package com.ecl.punch.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "card")
@RequiredArgsConstructor
@Data

public class PunchCard {

    @Id
    @GeneratedValue
    private Integer id;
    private String status;
    private String deleteStatus;
    private String picture;
//    private String timestamp;
    private Integer userId;
    private Date punchInDateTime;
    private Date punchOutDateTime;


    private String locationName;
    private String longitude;
    private String latitude;
    private String notes;
    private String punchInNotes;
    private String punchOutNotes;

}
