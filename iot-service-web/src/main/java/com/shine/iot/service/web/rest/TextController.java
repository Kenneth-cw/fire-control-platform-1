package com.shine.iot.service.web.rest;

import com.shine.iot.service.web.feignClient.TestFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TextController {

    private TestFeignClient testFeignClient;

    @Autowired
    private void setTestFeignClient(TestFeignClient testFeignClient) {
        this.testFeignClient = testFeignClient;
    }

    @GetMapping("/query")
    public void test() {
        testFeignClient.testQuery("3036303046464646");
    }

}
