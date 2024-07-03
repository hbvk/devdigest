package com.hbvk.devdigest.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void hello() throws Exception {
        mvc
                .perform(get("/hello"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"));
        ;
    }

    @Test
    void throwError() throws Exception {
        mvc
                .perform(get("/throw"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("error"))
        ;
    }

    @Test
    void throwRuntimeError() throws Exception {
        mvc
                .perform(get("/throw2"))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("error"))
        ;
    }

    @Test
    void deprecated() throws Exception {
        mvc
                .perform(get("/deprecated"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello world (deprecated)!"))
        ;
    }

    @Test
    void findByName() throws Exception {
        mvc
                .perform(get("/find/me"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, me!"))
        ;
    }
}