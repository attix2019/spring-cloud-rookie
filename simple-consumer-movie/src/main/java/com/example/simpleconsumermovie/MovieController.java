package com.example.simpleconsumermovie;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import feign.Feign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;


@RestController
public class MovieController {

    private static final Logger LOGGER =  LoggerFactory.getLogger(MovieController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserFeignClient userFeignClient;

//    @Autowired
//    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/user/{id}")
    @HystrixCommand(fallbackMethod = "findByIdFallBack" )
    public User findById(@PathVariable long id){
//        return restTemplate.getForObject("http://user-service/" + id, User.class);
        return userFeignClient.findById(id);
    }

    public User findByIdFallBack(long id){
        User user = new User();
        user.setAge(1);
        user.setBalance(new BigDecimal(0));
        user.setUsername("fallback username");
        user.setName("fallback name");
        return user;
    }

    @GetMapping("/log-instance")
    public void logUserInstance(){
        ServiceInstance serviceInstance = this.loadBalancerClient.choose("user-service");
        MovieController.LOGGER.info("{}:{}:{}", serviceInstance.getServiceId(), serviceInstance.getHost(),
                serviceInstance.getPort());
    }

//    @GetMapping("/user-instance")
//    public List<ServiceInstance> showInfo(){
//        return discoveryClient.getInstances("user-service");
//    }
}
