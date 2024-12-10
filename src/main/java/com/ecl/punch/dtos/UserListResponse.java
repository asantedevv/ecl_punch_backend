package com.ecl.punch.dtos;

import com.ecl.punch.models.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class UserListResponse extends  ResponseMessage{

    private List<User> data;
}
