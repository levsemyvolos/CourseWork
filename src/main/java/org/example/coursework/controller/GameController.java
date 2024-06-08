package org.example.coursework.controller;
import org.example.coursework.model.Game;
import org.example.coursework.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/game")
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @GetMapping("/")
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @PostMapping("/")
    public Game createGame(@RequestBody Game game) {
        return gameRepository.save(game);
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable Long id) {
        return gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable Long id, @RequestBody Game gameDetails) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));

        game.setTitle(gameDetails.getTitle());
        game.setDescription(gameDetails.getDescription());
        game.setReleaseDate(gameDetails.getReleaseDate());
        game.setDeveloper(gameDetails.getDeveloper());
        game.setPublisher(gameDetails.getPublisher());
        game.setAverageRating(gameDetails.getAverageRating());

        return gameRepository.save(game);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new RuntimeException("Game not found"));
        gameRepository.delete(game);
    }
}//
