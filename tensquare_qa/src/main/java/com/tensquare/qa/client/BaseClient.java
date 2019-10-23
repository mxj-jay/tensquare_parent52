package com.tensquare.qa.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("tensquare-base")
public interface BaseClient {

    @RequestMapping(value = "/label/{labelID}", method = RequestMethod.GET)
    public Result findByID(@PathVariable("labelID") String labelID);

}
