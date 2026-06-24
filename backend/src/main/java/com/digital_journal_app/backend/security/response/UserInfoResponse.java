package com.digital_journal_app.backend.security.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
//    private String jwtToken;
    private String username;


}
