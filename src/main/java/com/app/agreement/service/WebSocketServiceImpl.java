package com.app.agreement.service;

import com.app.agreement.configurations.CustomWebSocketHandler;
import com.app.agreement.entity.AgreementEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketServiceImpl {

    @Autowired
    private CustomWebSocketHandler webSocketHandler;

    public void sendAllAgreementFoundByClientId(List<AgreementEntity> data) {
        webSocketHandler.broadcastMessage(data);
    }

}
