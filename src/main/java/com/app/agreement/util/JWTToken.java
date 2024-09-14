package com.app.agreement.util;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class JWTToken {
    private String token;
}
