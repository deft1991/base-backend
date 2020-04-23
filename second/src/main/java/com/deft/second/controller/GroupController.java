package com.deft.second.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * Created by sgolitsyn on 4/17/20
 */
@RestController
@RequestMapping("/second")
class GroupController {

    @GetMapping
    String groups() {
        return "qweqweqweqweqweqe";
    }
}
