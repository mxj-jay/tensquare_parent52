package com.tensquare.sms.listener;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @RabbitHandler
    public void executeSms(Map<String, String> map) {
        String moblie = map.get("mobile");
        String randomNumeric = map.get("randomNumeric");
        System.out.println("手机号:" + moblie);
        System.out.println("验证码:" + randomNumeric);
    }

}
