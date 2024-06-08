package org.example.coursework.repository;
import org.example.coursework.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RatingRepository extends JpaRepository<Rating, Long> {
}//