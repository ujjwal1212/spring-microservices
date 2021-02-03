package com.learning.moviecatalogservice.resources;

import java.util.List;
import java.util.stream.Collectors;

import com.learning.moviecatalogservice.models.CatalogItem;
import com.learning.moviecatalogservice.models.UserRating;
import com.learning.moviecatalogservice.services.MovieInfo;
import com.learning.moviecatalogservice.services.UserRatingInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private MovieInfo movieInfo;

    @Autowired
    private UserRatingInfo userRatingInfo;

    @RequestMapping(value = "/{userId}", headers = "Accept=application/json")
    public List<CatalogItem> getCatalog(@PathVariable("userId")  String userId){
        UserRating ratings = userRatingInfo.getUserRatings(userId);
        return ratings
            .getRatings()
            .stream()
            .map(rating -> movieInfo.getCatalogItem(rating))
            .collect(Collectors.toList());
    }
}

/* Movie movie = webClientBuilder.build()
    .get()
    .uri("http://localhost:8082/movies/" + rating.getMovieId())
    .retrieve()
    .bodyToMono(Movie.class)
    .block(); */