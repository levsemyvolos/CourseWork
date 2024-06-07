package org.example.coursework.Service;
import org.example.coursework.exception.RatingNotFoundException;
import org.example.coursework.model.Rating;
import org.example.coursework.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }

    public Rating createRating(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating getRatingById(Long id) throws RatingNotFoundException {
        return ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Рейтинг с указанным id не найден"));
    }

    public Rating updateRating(Long id, Rating updatedRating) throws RatingNotFoundException {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Рейтинг с указанным id не найден"));
        rating.setScore(updatedRating.getScore());
        rating.setUser(updatedRating.getUser());
        rating.setGame(updatedRating.getGame());
        return ratingRepository.save(rating);
    }

    public boolean deleteRating(Long id) {
        if (ratingRepository.existsById(id)) {
            ratingRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}