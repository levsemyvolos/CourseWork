package org.example.coursework.repository;
import org.example.coursework.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GameRepository extends JpaRepository<Game, Long> {
}