package com.learning.moviecatalogservice.services;

import com.learning.moviecatalogservice.models.CatalogItem;
import com.learning.moviecatalogservice.models.Movie;
import com.learning.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {
    @Autowired
    private RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod = "getFallbackCatalog",
        commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="60")
        }
    )
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = this.restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallbackCatalog(Rating rating) {
        return new CatalogItem("Movie name not found.", "", rating.getRating());
    }
    
}