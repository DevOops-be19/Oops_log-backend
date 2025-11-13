package com.devoops.oopslog.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("health")
    public String health() {
        return "I'm Oops!";
    }
}
