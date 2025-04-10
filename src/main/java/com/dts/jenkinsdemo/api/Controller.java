package com.dts.jenkinsdemo.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/jenkins")
public class Controller {
    private final Logger logger = LoggerFactory.getLogger(Controller.class);
    @GetMapping("/get")
    public ResponseEntity<String> getTest() {
        logger.info("get getTest");
        return ResponseEntity.ok("200 response");
    }

    @GetMapping("/get-id")
    public ResponseEntity<String> getTestId(@RequestParam(required = false) String id) {
        logger.info("get getTestId: " + id);
        return ResponseEntity.ok("200 response: " + id);
    }

    @PostMapping("/post-id")
    public ResponseEntity<String> postTestId(@RequestBody String id) {
        logger.info("post postTestId: " + id);
        return ResponseEntity.ok("200 response: " + id);
    }


    @GetMapping("/random")
    public ResponseEntity<String> getRamDom() {
        String random = UUID.randomUUID().toString();
        logger.info("get random: " + random);
        return ResponseEntity.ok("random: " + random);
    }
}
