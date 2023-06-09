package com.example.schoolFrontDesk.dto.AddRequest;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LoginRequest {

    private String name;
    private String password;


}
