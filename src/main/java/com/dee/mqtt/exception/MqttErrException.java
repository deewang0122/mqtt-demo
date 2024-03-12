package com.dee.mqtt.exception;

public class MqttErrException extends RuntimeException {

    public MqttErrException(String message) {
        super(message);
    }

    public MqttErrException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MqttErrException(Throwable throwable) {
        super(throwable);
    }
}
