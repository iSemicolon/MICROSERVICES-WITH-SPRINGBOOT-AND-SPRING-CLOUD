package org.kol.microservices.currencyexchangeservice.circuitbreakercontroller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CircuitBreakerController {

    private Logger logger= LoggerFactory.getLogger(CircuitBreakerController.class);

    @GetMapping("/sample-api")
  //  @Retry(name = "sample-api-retry")
 //   @Retry(name = "sample-api", fallbackMethod = "hardCodedResponse")
 //   @CircuitBreaker(name = "sample-api", fallbackMethod = "hardCodedResponse")
  //  @RateLimiter(name = "default")
    //rateLimiter-> 10s ==> 10000 calls allowed to the sample api

    //bulkhead- how many concurrent call allowed
    @Bulkhead(name = "default")
    public String SampleApi(){

        logger.info("---> Sample API call received");
/*
        ResponseEntity<String> forEntity=new RestTemplate().getForEntity("http://localhost:8080/some-dummy-url", String.class);

        return forEntity.getBody(); */

        return "Sample API Back";
    }


    public String hardCodedResponse(Exception exception){
        return "fallback-response";
    }
}
