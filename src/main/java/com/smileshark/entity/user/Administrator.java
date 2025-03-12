package com.smileshark.entity.user;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Administrator {
    private String administratorId;
    private String administratorAccount;
    private String administratorPassword;
    private String administratorName;
}
