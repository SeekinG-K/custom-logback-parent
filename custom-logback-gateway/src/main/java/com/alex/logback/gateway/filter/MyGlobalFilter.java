package com.alex.logback.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Slf4j
@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("================come in myGlobalFilter" + new Date() + "==================");
        String name = exchange.getRequest().getQueryParams().getFirst("name");
/*        if (name == null || name.length() == 0) {
            log.info("=======================非法用户,name为null==============================");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }*/
        log.info("=======welcome! name =  " + name + "=======================");
        return chain.filter(exchange);
    }
    @Override
    public int getOrder() {
        return -1;
    }
}
