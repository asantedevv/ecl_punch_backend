package com.ecl.punch.services;

import com.ecl.punch.dtos.PunchCardListResponse;
import com.ecl.punch.dtos.PunchCardResponse;
import com.ecl.punch.models.PunchCard;
import com.ecl.punch.models.User;
import com.ecl.punch.repositories.PunchCardRepository;
import com.ecl.punch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PunchCardService {

    private final PunchCardRepository punchCardRepository;
    private final UserRepository userRepository;

    public PunchCardResponse punchIn(PunchCard punchCard) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userEmail = authentication.getName();
//        User user = userRepository.findByEmail(userEmail).orElseThrow();

        PunchCardResponse punchCardResponse = new PunchCardResponse();

        try{

            punchCard.setStatus("ONGOING");
            punchCard.setPunchInDateTime(new Date());
//            punchCard.setUserId(user.getId());
            punchCard.setDeleteStatus("NO");
            punchCard.setPunchOutNotes("");
            punchCard.setPunchInNotes("");
            punchCard.setNotes("");
            punchCard.setPunchDate(new Date());
            punchCard.setTotalHours(0);
            punchCard.setPunchOutDateTime(new Date());

            PunchCard punchCard1 = punchCardRepository.save(punchCard);
            punchCardResponse.setResponseCode("000");
            punchCardResponse.setResponseMessage("Punch in successfully");
            punchCardResponse.setData(punchCard1);

            User user = userRepository.findById(punchCard.getUserId()).orElseThrow(() -> new RuntimeException("User Not Found"));
            user.setPunch("Punched In");
            user.setPunchInStatus(true);

            System.out.println("this is the user punched In" + user.getPunch());
            userRepository.save(user);
        } catch (Exception e) {
            punchCardResponse.setResponseCode("111");
            punchCardResponse.setResponseMessage("Punch in failed: " + e.getMessage());
        }
        return punchCardResponse;
    }


    public PunchCardResponse punchOut(PunchCard punchCard) {

        PunchCardResponse punchCardResponse = new PunchCardResponse();

        try{
            PunchCard punchCard1 = punchCardRepository.findById(punchCard.getId()).orElse(null);
            punchCard1.setStatus("COMPLETED");
            punchCard1.setPunchOutDateTime(new Date());
            punchCard1.setPunchOutNotes(punchCard.getPunchOutNotes());

//            Duration duration = Duration.between(punchCard1.getPunchInDateTime(), punchCard1.getPunchOutDateTime());


            Instant punchInInstant = punchCard1.getPunchInDateTime().toInstant();
            Instant punchOutInstant = punchCard1.getPunchOutDateTime().toInstant();

            Duration duration = Duration.between(punchInInstant, punchOutInstant);

            int hoursWorked = (int) duration.toHours();


            punchCard1.setTotalHours(hoursWorked);
            punchCardRepository.save(punchCard1);


            System.out.println("########################################");
            System.out.println(punchCard.getUserId());

            User user = userRepository.findById(punchCard.getUserId()).orElseThrow(()->new RuntimeException("User Not Found"));
            System.out.println(user.getFullName());
            System.out.println(user.getPunch());
            user.setPunch("Punched Out");
            user.setPunchInStatus(false);
            userRepository.save(user);
            System.out.println(user.getPunch());


            punchCardResponse.setResponseCode("000");
            punchCardResponse.setResponseMessage("Punch out successfully");
            punchCardResponse.setData(punchCard1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            punchCardResponse.setResponseCode("111");
            punchCardResponse.setResponseMessage("Punch out unsuccessful: " + e.getMessage());
        }
        return punchCardResponse;
    }




    public PunchCardResponse findPunchCardById(Integer id) {
        PunchCardResponse punchCardResponse = new PunchCardResponse();

        try{
            PunchCard punchCard = punchCardRepository.findById(id).orElse(null);
            punchCardResponse.setResponseCode("000");
            punchCardResponse.setResponseMessage("Punch Card found successfully");
            punchCardResponse.setData(punchCard);
        } catch (Exception e) {
            punchCardResponse.setResponseCode("111");
            punchCardResponse.setResponseMessage("Punch Card not found");
        }
        return punchCardResponse;
    }

    public PunchCardListResponse findPunchCardsByUserId(Integer userId) {
        PunchCardListResponse punchCardListResponse = new PunchCardListResponse();

        try{
            List<PunchCard> punchCard = punchCardRepository.findAllByUserId(userId);
            punchCardListResponse.setResponseCode("000");
            punchCardListResponse.setResponseMessage("Punch Card found successfully");
            punchCardListResponse.setData(punchCard);
        } catch (Exception e) {
            punchCardListResponse.setResponseCode("111");
            punchCardListResponse.setResponseMessage("Punch Card not found");
        }
        return punchCardListResponse;
    }

    public PunchCardListResponse findAllPunchCards() {

        PunchCardListResponse punchCardListResponse = new PunchCardListResponse();

        try {
            List<PunchCard> punchCards = punchCardRepository.findAll();

            punchCardListResponse.setResponseCode("000");
            punchCardListResponse.setResponseMessage("Found all Punch Cards");
            punchCardListResponse.setData(punchCards);
        } catch (Exception e) {
            punchCardListResponse.setResponseCode("111");
            punchCardListResponse.setResponseMessage("Punch Cards not found");
        }
        return punchCardListResponse;
    }

    public PunchCardResponse updatePunchCardById(Integer id, PunchCard updatedPunchCard) {

        PunchCardResponse punchCardResponse = new PunchCardResponse();

        PunchCard punchCard = punchCardRepository.findById(id).orElse(null);

        try{
            if (punchCard != null) {
                punchCard.setDeleteStatus(updatedPunchCard.getDeleteStatus());
                punchCard.setPicture(updatedPunchCard.getPicture());
//                punchCard.setTimestamp(updatedPunchCard.getTimestamp());
                punchCard.setLocationName(updatedPunchCard.getLocationName());
                punchCard.setLongitude(updatedPunchCard.getLongitude());
                punchCard.setLatitude(updatedPunchCard.getLatitude());

                PunchCard punchCard1 = punchCardRepository.save(punchCard);

                punchCardResponse.setResponseCode("000");
                punchCardResponse.setResponseMessage("Punch Card updated successfully");
                punchCardResponse.setData(punchCard1);
            }
        } catch (Exception e) {
            punchCardResponse.setResponseCode("111");
            punchCardResponse.setResponseMessage("Punch Card failed to update");
        }
        return punchCardResponse;
    }

    public PunchCardResponse deletePunchCardById(Integer id) {

        PunchCardResponse punchCardResponse = new PunchCardResponse();

        try {
            PunchCard punchCard = punchCardRepository.findById(id).orElse(null);

            if (punchCard != null) {
                punchCard.setDeleteStatus("DELETED");

                PunchCard punchCard1 = punchCardRepository.save(punchCard);
                punchCardResponse.setResponseCode("000");
                punchCardResponse.setResponseMessage("Punch Card deleted successfully");
                punchCardResponse.setData(punchCard1);
            }
        } catch (Exception e) {
            punchCardResponse.setResponseCode("111");
            punchCardResponse.setResponseMessage("Punch Card failed to delete");
        }
        return punchCardResponse;
    }

    public PunchCardResponse restorePunchCardById(Integer id) {

        PunchCardResponse punchCardResponse = new PunchCardResponse();

        try {
            PunchCard punchCard = punchCardRepository.findById(id).orElse(null);

            if (punchCard != null) {
                punchCard.setDeleteStatus("ACTIVE");

                PunchCard punchCard1 = punchCardRepository.save(punchCard);
                punchCardResponse.setResponseCode("000");
                punchCardResponse.setResponseMessage("Punch Card is Active");
                punchCardResponse.setData(punchCard1);
            }
        } catch (Exception e) {
            punchCardResponse.setResponseCode("111");
            punchCardResponse.setResponseMessage("Punch Card is Inactive");
        }
        return punchCardResponse;
    }

}
