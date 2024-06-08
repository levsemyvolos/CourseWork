package org.example.coursework.controller;

import org.example.coursework.model.Rating;
import org.example.coursework.Service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping("/")
    public List<Rating> getAllRatings() {
        return ratingService.getAllRatings();
    }

    @PostMapping("/")
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {
        Rating createdRating = ratingService.createRating(rating);
        return ResponseEntity.ok(createdRating);
    }

    @GetMapping("/{id}")
public ResponseEntity<Rating> getRatingById(@PathVariable Long id) {
    Rating rating = ratingService.getRatingById(id);
    if (rating != null) {
        return ResponseEntity.ok(rating);
    } else {
        return ResponseEntity.notFound().build();
    }
}

@PutMapping("/{id}")
public ResponseEntity<Rating> updateRating(@PathVariable Long id, @RequestBody Rating updatedRating) {
    Rating rating = ratingService.updateRating(id, updatedRating);
    if (rating != null) {
        return ResponseEntity.ok(rating);
    } else {
        return ResponseEntity.notFound().build();
    }
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteRating(@PathVariable Long id) {
    boolean deleted = ratingService.deleteRating(id);
    if (deleted) {
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}
}//
