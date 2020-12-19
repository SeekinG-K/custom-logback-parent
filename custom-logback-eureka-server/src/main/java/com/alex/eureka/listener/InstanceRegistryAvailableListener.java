package com.alex.eureka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.EurekaRegistryAvailableEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;


@Configuration
@Slf4j
public class InstanceRegistryAvailableListener implements ApplicationListener<EurekaRegistryAvailableEvent> {
    @Override
    public void onApplicationEvent(EurekaRegistryAvailableEvent eurekaRegistryAvailableEvent) {
        log.debug("Register started success "+ eurekaRegistryAvailableEvent.toString());
    }
}
