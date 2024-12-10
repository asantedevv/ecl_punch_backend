package com.ecl.punch.auth;

import com.ecl.punch.dtos.ResponseMessage;
import com.ecl.punch.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse extends ResponseMessage {

    private String token;
    private User data;

}