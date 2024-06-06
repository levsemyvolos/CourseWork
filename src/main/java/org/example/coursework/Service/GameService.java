package org.example.coursework.Service;

import org.example.coursework.exception.GameNotFoundException;
import org.example.coursework.model.Game;
import org.example.coursework.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    public Game createGame(String title, String description, LocalDate releaseDate, String developer, String publisher, Double averageRating) {
        Game game = new Game();
        game.setTitle(title);
        game.setDescription(description);
        game.setReleaseDate(releaseDate);
        game.setDeveloper(developer);
        game.setPublisher(publisher);
        game.setAverageRating(averageRating);
        return gameRepository.save(game);
    }

    public Game getGameById(Long gameId) throws GameNotFoundException {
        Optional<Game> gameOptional = gameRepository.findById(gameId);
        return gameOptional.orElseThrow(() -> new GameNotFoundException("Игра с указанным game_id не найдена"));
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

}

