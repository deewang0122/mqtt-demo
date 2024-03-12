package com.dee.mqtt.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

@Slf4j
public class MqttClientCallback implements MqttCallback, IClientCallback {

    private String mqttClientId;

    public MqttClientCallback(String mqttClientId) {
        this.mqttClientId = mqttClientId;
    }

    @Override
    public void connectionLost(Throwable throwable) {
        log.error("Mqtt连接丢失", throwable);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        log.info("Mqtt接收消息，topic={}, 消息内容={}", topic, new ObjectMapper().writeValueAsString(mqttMessage.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        log.info("Mqtt发送消息完成");
    }
}
