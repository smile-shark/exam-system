package com.smileshark.common;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class RequestParams {
    private String studentName;
    private String studentAccount;
    private String studentPassword;
    private String administratorId;
    private String administratorAccount;
    private String administratorPassword;
}
