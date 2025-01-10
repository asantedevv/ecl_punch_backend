package com.ecl.punch.auth;

import com.ecl.punch.config.JwtService;
import com.ecl.punch.models.User;
import com.ecl.punch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
















    public AuthenticationResponse register(User user) {

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        try {

//            var user = User.builder()
////                .firstName(user.getFirstName())
////                .lastName(user.getLastName())
//                    .email(user.getEmail())
//                    .password(passwordEncoder .encode(user.getPassword()))
//                    .build();




            user.setDeleteStatus("NO");
            user.setPunchInStatus(false);
            user.setPunch("Punched Out");
            userRepository.save(user);

            var jwtToken = jwtService.generateToken(user);

            authenticationResponse.setResponseCode("000");
            authenticationResponse.setResponseMessage("Sign up successful");
            authenticationResponse.setToken(jwtToken);
            authenticationResponse.setData(user);

        } catch (Exception e) {

            authenticationResponse.setResponseCode("111");
            authenticationResponse.setResponseMessage("Sign up failed: " + e.getMessage());

        }

//        var jwtToken = jwtService.generateToken(user);
//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();

        return authenticationResponse;
    }



    public AuthenticationResponse authenticate(User request) {

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            request.getEmail(),
//                            request.getPassword()
//                    )
//            );

//            var user = userRepository.findByEmail(request.getEmail())
//                    .orElseThrow();

            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

            System.out.println("This is the user delete status");

            System.out.println(user.getDeleteStatus());

            if (Objects.equals(user.getDeleteStatus(), "NO"))
            {

                var jwtToken = jwtService.generateToken(user);

                authenticationResponse.setResponseCode("000");
                authenticationResponse.setResponseMessage("Login Successful!");
                authenticationResponse.setToken(jwtToken);
                authenticationResponse.setData(user);
            }

            else {
                throw new RuntimeException("USER DOES NOT EXIST");
            }

        }

        catch (Exception e) {

            authenticationResponse.setResponseCode("111");
            authenticationResponse.setResponseMessage("Login Failed!: " + e.getMessage());

        }

//        return AuthenticationResponse.builder()
//                .token(jwtToken)
//                .build();

        return authenticationResponse;
    }




    public AuthenticationResponse microsoftAuthentication (com.ecl.punch.models.User user) {

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        try{

            boolean userExists = userRepository.existsByEmail(user.getEmail());

            if (userExists){
                authenticationResponse =  authenticate(user);
            }
            else {
                authenticationResponse = register(user);
            }

            return authenticationResponse;

        } catch (Exception e) {
            authenticationResponse.setResponseCode("111");
            authenticationResponse.setResponseMessage("Failed to microsoft authenticate: " +  e.getMessage());
        }

        return authenticationResponse;

    }

}
