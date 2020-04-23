package com.example.carservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
class CarController {

    private CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @PostMapping("/cars")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Car> addCar(@RequestBody Car car) {
        return carRepository.save(car);
    }

    @GetMapping("/cars")
    public Flux<Car> getCars() {
        return carRepository.findAll();
    }

    @DeleteMapping("/cars/{id}")
    public Mono<ResponseEntity<Void>> deleteCar(@PathVariable("id") UUID id) {
        return carRepository.findById(id)
                .flatMap(car -> carRepository.delete(car)
                        .then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)))
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
