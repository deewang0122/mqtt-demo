package com.dee.mqtt.handler.param;

import lombok.Data;

@Data
public abstract class BaseMessageClientParam {
    private String messageClientId;

    private String url;

    private String username;

    private String password;
}
