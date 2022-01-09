package com.example.simpleconsumermovie;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="user-service")
public interface UserFeignClient {

    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public User findById(@PathVariable("id") Long id);

}
