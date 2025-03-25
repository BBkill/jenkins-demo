package com.dts.jenkinsdemo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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

    @PostMapping("/post-id")
    public ResponseEntity<String> postTestId(@RequestBody String id) {
        return ResponseEntity.ok("200 response: " + id);
    }


    @GetMapping("/random")
    public ResponseEntity<String> getRamDom() {
        return ResponseEntity.ok("random: " + UUID.randomUUID());
    }
}
