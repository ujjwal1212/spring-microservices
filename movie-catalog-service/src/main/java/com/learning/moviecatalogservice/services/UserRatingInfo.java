package com.learning.moviecatalogservice.services;

import java.util.Arrays;

import com.learning.moviecatalogservice.models.Rating;
import com.learning.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRatingInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallbackUserRatings")
    public UserRating getUserRatings(String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratings/users/" + userId, UserRating.class);
    }

    public UserRating getFallbackUserRatings(String userId) {
        System.out.println("getFallbackUserRatings");
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(
            new Rating("0", 0)
        ));
        return userRating;
    }
}