package com.ecl.punch.dtos;

import com.ecl.punch.models.PunchCard;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class PunchCardListResponse extends ResponseMessage{

    private List<PunchCard> data;
}
