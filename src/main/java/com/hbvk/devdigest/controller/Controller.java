package com.hbvk.devdigest.controller;

import com.hbvk.devdigest.exception.SampleException;
import com.hbvk.devdigest.exception.SampleRuntimeException;
import com.hbvk.devdigest.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    final private MyService service;

    @Autowired
    public Controller(MyService service) {
        this.service = service;
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok().body("Hello World");
    }

    @GetMapping("/throw")
    public ResponseEntity<String> throwError() throws SampleException {
        throw new SampleException("error");
    }

    @GetMapping("/throw2")
    public ResponseEntity<String> throwRuntimeError() {
        throw new SampleRuntimeException("error");
    }

    @GetMapping("/deprecated")
    @Deprecated(forRemoval = true)
    public ResponseEntity<String> deprecated() {
        return ResponseEntity.ok().body("Hello world (deprecated)!");
    }

    @GetMapping("/find/{name}")
    public String findByName(@PathVariable String name) {
        return service.find(name);
    }
}
