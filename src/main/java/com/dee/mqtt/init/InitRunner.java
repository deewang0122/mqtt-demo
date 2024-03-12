package com.dee.mqtt.init;

import com.dee.mqtt.handler.IMessageClientHandler;
import com.dee.mqtt.handler.MqttMessageClientHandler;
import com.dee.mqtt.handler.param.MqttMessageClientParam;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitRunner implements ApplicationRunner {
    @Autowired
    IMessageClientHandler messageClientHandler;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        MqttMessageClientParam param = new MqttMessageClientParam();
        param.setCleanSession(true);
        param.setUrl("tcp://127.0.0.1:1883");
        param.setUsername("admin");
        param.setPassword("public");
        param.setMessageClientId("test_client_id");
        MqttMessageClientHandler clientHandler = new MqttMessageClientHandler();
        clientHandler.connect(param);
        clientHandler.sub("test", 2);
        messageClientHandler.addClient(param.getMessageClientId(), clientHandler);
    }
}
