package com.learning.ratingdataservice.resources;

import java.util.Arrays;
import java.util.List;

import com.learning.ratingdataservice.models.Rating;
import com.learning.ratingdataservice.models.UserRating;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ratings")
public class RatingDataResource {
    
    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId){
        return new Rating(movieId, 7);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRatingInfo(@PathVariable("userId") String userId){
        List<Rating> ratings = Arrays.asList(
            new Rating("100", 8),
            new Rating("200", 7)
        );

        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(ratings);
        return userRating;
    }
}