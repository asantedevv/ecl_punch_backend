package com.ecl.punch.dtos;

import com.ecl.punch.models.PunchCard;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PunchCardResponse extends ResponseMessage{

    private PunchCard data;

}