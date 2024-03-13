package com.dee.mqtt.handler;

import com.dee.mqtt.exception.MqttErrException;
import com.dee.mqtt.handler.param.BaseMessageClientParam;
import com.dee.mqtt.handler.param.MqttMessageClientParam;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class MqttMessageClientHandler implements IMessageClientHandler<MqttMessageClientParam> {

    private MqttClient mqttClient;

    public MqttMessageClientHandler() {
    }

    public MqttMessageClientHandler(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    @Override
    public void connect(MqttMessageClientParam param) {
        try {
            mqttClient = new MqttClient(param.getUrl(), param.getMessageClientId(), new MemoryPersistence());
        } catch (MqttException e) {
            throw new MqttErrException("创建Mqtt客户端失败", e);
        }

        if (Objects.isNull(param.getMqttClientCallback())) {
            mqttClient.setCallback(new MqttClientCallback(param.getMessageClientId()));
        } else {
            mqttClient.setCallback(param.getMqttClientCallback());
        }

        MqttConnectOptions connectOptions = initConnectOptions(param);

        try {
            mqttClient.connect(connectOptions);
        } catch (MqttException e) {
            throw new MqttErrException("Mqtt连接失败", e);
        }
    }

    private MqttConnectOptions initConnectOptions(MqttMessageClientParam param) {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(param.getUsername());
        connectOptions.setPassword(param.getPassword().toCharArray());
        connectOptions.setConnectionTimeout(30);
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setCleanSession(param.getCleanSession());
        return connectOptions;
    }

    @Override
    public void sub(String[] topics) {
        try {
            mqttClient.subscribe(topics);
        } catch (MqttException e) {
            log.error("Mqtt订阅消息失败，topics={}", topics, e);
        }
    }

    @Override
    public void sub(String topic, Integer qos) {
        try {
            mqttClient.subscribe(topic, qos);
        } catch (MqttException e) {
            log.error("Mqtt订阅消息失败，topic={}", topic, e);
        }
    }

    @Override
    public void close() {
        try {
            mqttClient.disconnect();
            mqttClient.close();
        } catch (MqttException e) {
            log.error("关闭Mqtt客户端失败：{}", e.getMessage());
            throw new MqttErrException(e);
        }
    }
}
