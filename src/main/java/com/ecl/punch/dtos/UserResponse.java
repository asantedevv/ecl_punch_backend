package com.ecl.punch.dtos;

import com.ecl.punch.models.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserResponse extends ResponseMessage {

    private User data;

}