package org.kol.microservices.apigateway.globalfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;


//make sure all request goes  through api gateway

@Component
public class LoggingFilter implements GlobalFilter {

    private Logger  logger= LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, GatewayFilterChain gatewayFilterChain){
        logger.info("Path of the requested received -> {}", serverWebExchange.getRequest().getPath());

        return gatewayFilterChain.filter(serverWebExchange);
    }
}
