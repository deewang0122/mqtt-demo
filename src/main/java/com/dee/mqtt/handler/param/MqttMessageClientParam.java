package com.dee.mqtt.handler.param;

import com.dee.mqtt.handler.MqttClientCallback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqttMessageClientParam extends BaseMessageClientParam {
    private MqttClientCallback mqttClientCallback;

    private Boolean cleanSession;
}
