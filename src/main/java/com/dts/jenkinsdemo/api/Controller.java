package com.dts.jenkinsdemo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jenkins")
public class Controller {
    @GetMapping("/get")
    public ResponseEntity<String> getTest() {
        return ResponseEntity.ok("200 response");
    }
    @GetMapping("/get-id")
    public ResponseEntity<String> getTestId(@RequestParam(required = false) String id) {
        return ResponseEntity.ok("200 response: " + id);
    }
}
