package com.app.agreement.util;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class OwnersShop {

    private String shopName;
    private String workPerform;
}
