package com.hbvk.devdigest.service;

import org.springframework.stereotype.Service;

@Service
public class MyService {
    public String find(String name) {
        return "Hello, " + name + "!";
    }
}
