package org.kol.microservices.apigateway.configuration;


import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder){

        //test_url : http://localhost:8765/get

        //if request come from specific url the route specific url


        return  routeLocatorBuilder.routes().route(p-> p.path("/get")
                .filters(f->f.addRequestHeader("MyHeader", "MyURI")
                        .addRequestHeader("Param", "MyValue"))
                .uri("http://httpbin.org:80"))

                .route(p->p.path("/currency-exchange/**")   //anything come from currency-exchange
                        .uri("lb://currency-exchange"))   //lb: load balancing and redirect to currency-exchange

                .route(p->p.path("/currency-conversion/**")   //anything come from currency-conversion resttemplte
                        .uri("lb://currency-conversion"))

                .route(p->p.path("/currency-conversion-feign/**")   //anything come from currency-conversion feign
                        .uri("lb://currency-conversion"))

                //reWrite url
                .route(p->p.path("/currency-conversion-new/**")
                        .filters(f->f.rewritePath(
                                "/currency-conversion-new/(?<segment>.*)",
                                "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion"))
                .build();
    }
}
