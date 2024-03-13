package com.dee.mqtt.controller;

import com.dee.mqtt.handler.IMessageClientHandler;
import com.dee.mqtt.handler.MqttMessageClientHandler;
import com.dee.mqtt.handler.param.MqttMessageClientParam;
import com.dee.mqtt.init.InitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * Mqtt测试Controller
 */
@RestController
@RequestMapping("mqtt")
public class HelloController {

    @Autowired
    IMessageClientHandler iMessageClientHandler;

    @GetMapping("hello")
    public String hello() {
        return "Hello Mqtt";
    }

    @GetMapping("list")
    public List<String> list() {
        List<String> stringList = new ArrayList<>();
        IMessageClientHandler.messageClientFactory.forEach((k, v) -> {
            stringList.add(k);
        });
        return stringList;
    }

    @GetMapping("stop")
    public String stop() {
        List<String> keys = new ArrayList<>();
        IMessageClientHandler.messageClientFactory.forEach((key, handler) -> {
            keys.add(key);
            handler.close();
        });

        keys.forEach(IMessageClientHandler.messageClientFactory::remove);
        return "客户端存活数：" + IMessageClientHandler.messageClientFactory.size();
    }

    @GetMapping("start")
    public String start() {
        MqttMessageClientParam param = new MqttMessageClientParam();
        param.setCleanSession(true);
        param.setUrl("tcp://127.0.0.1:1883");
        param.setUsername("admin");
        param.setPassword("public");
        param.setMessageClientId("test_client_id");
        MqttMessageClientHandler clientHandler = new MqttMessageClientHandler();
        clientHandler.connect(param);
        clientHandler.sub("test", 2);
        iMessageClientHandler.addClient(param.getMessageClientId(), clientHandler);

        return "启动客户端：" + param.getMessageClientId();
    }
}
