package com.dee.mqtt.handler;

import com.dee.mqtt.handler.param.BaseMessageClientParam;
import com.dee.mqtt.handler.param.MqttMessageClientParam;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

public interface IMessageClientHandler<T extends BaseMessageClientParam> {
    ConcurrentHashMap<String, IMessageClientHandler> messageClientFactory = new ConcurrentHashMap<>();

    default void addClient(String messageClientId, IMessageClientHandler handler) {
        if (messageClientFactory.contains(messageClientId)) {
            return;
        }

        messageClientFactory.put(messageClientId, handler);
    }

    default void removeClient(String messageClientId) {
        if (!messageClientFactory.contains(messageClientId)) {
            return;
        }

        messageClientFactory.get(messageClientId).close();
        messageClientFactory.remove(messageClientId);
    }

    void connect(T param);

    void sub(String[] topics);

    void sub(String topic, Integer qos);

    void close();


}
