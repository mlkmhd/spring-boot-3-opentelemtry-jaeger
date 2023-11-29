package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.Random;

@RestController
@RequestMapping("/hello")
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    Random random = new Random();

    private RestTemplate restTemplate;
    @Value("${spring.application.name}")
    private String applicationName;
    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @GetMapping("/path1")
    public ResponseEntity<String> path1() {
        logger.info("Incoming request at {} for request /path1 ", applicationName);
        doNothingButSleepForSomeTime();
        String response = restTemplate.getForObject("http://localhost:8090/hello/path2", String.class);
        doNothingButSleepForSomeTime();
        return ResponseEntity.ok("response from /path1 + " + response);
    }

    @GetMapping("/path2")
    public ResponseEntity<String>  path2() {
        logger.info("Incoming request at {} at /path2", applicationName);
        doNothingButSleepForSomeTime();
        return ResponseEntity.ok("response from /path2 ");
    }


    public void doNothingButSleepForSomeTime() {
        try {
            int sleepTime = random.nextInt(1, 2);
            logger.info("sleeping for " + sleepTime + " seconds");
            Thread.sleep(sleepTime * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}