package com.example.apigateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/fallback")
class Fallback {

    @GetMapping("/cars-fallback")
    public Flux<Car> noCars() {
        return Flux.empty();
    }

    @GetMapping("/second")
    public Mono<String> secondServiceFallback(){
        return Mono.just("Second Server overloaded! Please try after some time.");
    }

    @GetMapping("/group")
    public Mono<String> groupServiceFallback(){
        return Mono.just("Group Server overloaded! Please try after some time.");
    }
}
