#  Гуржій Максим АІ-221 
# Курсова робота: Створення веб-сервісу для оцінювання ігор

## Вступ:
Метою цієї роботи є створення веб-сервісу, який буду забезпечувати функціонал для користувачів, такий як: реєстрація, авторизація, просмотр список ігор, оцінювання цих ігор
залишати коментарі, відгуки та продивлятись рейтинг ігор. 

Застосунок побудовано за такими розділами як:
- Розділ 1 - Проектування і розробка веб-шару (REST-запити)
- Розділ 2 - Проектування і розробка сервісного шару 
- Розділ 3 - Проектування і розробка шару сховища
- Розділ 4 - Проектування і розробка журналювання
- Розділ 5 - Проектування і розробка безпеки

Зараз ми детально розглянемо процесс проектування та разробки кожного з розділів.
## Реалізація Розділ 1 - Проектування і розробка веб-шару (REST-запити)
REST (Representational State Transfer) є архітектурним стилем для розробки веб-сервісів, який базується на принципах простоти, легкості розуміння та масштабованості. REST-архітектура використовує HTTP протокол для передачі даних між клієнтом та сервером. Підходить для розробки як веб-додатків, так і мобільних додатків.

Основні принципи REST:

Клієнт-серверна архітектура: Система розбивається на дві окремі частини - клієнт і сервер. Це дозволяє їм розвиватися незалежно один від одного.

Без стану (Stateless): Кожен запит від клієнта до сервера має містити всю необхідну інформацію для сервера для розуміння запиту. Сервер не зберігає жодної інформації про стан клієнта між запитами.

Кешування: Клієнти можуть зберігати копії відповідей сервера, що дозволяє їм використовувати локальні копії замість повторних запитів до сервера.

Інтерфейс однорівневий (Uniform Interface): Інтерфейс REST повинен бути однорівневим, що означає, що ресурси доступні через стандартні інтерфейси, такі як HTTP методи (GET, POST, PUT, DELETE).

Система шарів (Layered System): Система може складатися з багатьох рівнів серверів, де клієнт не має свідомості про те, з яким сервером він спілкується.
Контроллер коментарів
```
package org.example.coursework.controller;
import org.example.coursework.model.Comment;
import org.example.coursework.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<?> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable(name = "id") Long id, @RequestBody Comment updatedComment) {
        Comment comment = commentService.updateComment(id, updatedComment);
        if (comment != null) {
            return ResponseEntity.ok(comment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        boolean deleted = commentService.deleteComment(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
```
Котроллер ігор
```
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
}
```
Контроллер рейтингу
```
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
}
```
## Розділ 2 - Проектування і розробка сервісного шару 
Сервісний шар (Service Layer) є ключовим елементом багатошарової архітектури додатка. Він відіграє важливу роль у відокремленні бізнес-логіки від інших компонентів додатка, таких як контролери та шари доступу до даних. Основні принципи та завдання сервісного шару включають наступне:

Реалізація бізнес-логіки: Сервісний шар містить всі необхідні методи та операції, які визначають бізнес-логіку додатка. Цей шар відповідає за обробку бізнес-правил, алгоритмів та операцій, які визначають основний функціонал додатка.

Взаємодія з іншими шарами: Сервісний шар служить посередником між іншими шарами додатка, такими як шари контролерів та доступу до даних (DAO/Repository). Він отримує дані від контролерів, обробляє їх за допомогою бізнес-логіки та взаємодіє з шаром доступу до даних для збереження або отримання необхідної інформації.

Відокремлення бізнес-логіки від інших компонентів: Одним з головних принципів сервісного шару є відокремлення бізнес-логіки від інших компонентів додатка, таких як користувацький інтерфейс та шар доступу до даних. Це дозволяє краще організувати код та забезпечити його перевикористання та підтримку.

Тестування та підтримка: Ізоляція бізнес-логіки в сервісному шарі робить тестування додатка більш простим та ефективним. Це дозволяє розробникам швидше виявляти та виправляти помилки. Крім того, забезпечується більша гнучкість та легкість підтримки додатка.

Враховуючи ці принципи, сервісний шар стає ключовим компонентом багатошарової архітектури додатка, забезпечуючи його ефективність, надійність та легкість управління.

Реалізація
Сервіс для коментарів
```
package org.example.coursework.Service;
import org.example.coursework.model.Comment;
import org.example.coursework.model.Game;
import org.example.coursework.model.User;
import org.example.coursework.repository.CommentRepository;
import org.example.coursework.repository.GameRepository;
import org.example.coursework.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    public List<Comment> getAllComments() {
        logger.info("Fetching all comments");
        return commentRepository.findAll();
    }

    public Comment createComment(Comment comment) {
        logger.info("Creating comment");

        User user = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(comment.getGame().getId())
                .orElseThrow(() -> new RuntimeException("Game not found"));

        comment.setUser(user);
        comment.setGame(game);
        comment.setCreatedAt(LocalDateTime.now());

        logger.info("Comment created: {}", comment);

        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        logger.info("Fetching comment with id {}", id);
        return commentRepository.findById(id).orElse(null);
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        logger.info("Updating comment with id {}", id);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(updatedComment.getContent());
        // Обновляем createdAt только если это необходимо
        if (updatedComment.getCreatedAt() != null) {
            comment.setCreatedAt(updatedComment.getCreatedAt());
        }
        // Обновляем user и game только если это необходимо
        if (updatedComment.getUser() != null) {
            comment.setUser(updatedComment.getUser());
        }
        if (updatedComment.getGame() != null) {
            comment.setGame(updatedComment.getGame());
        }

        logger.info("Updated comment: {}", comment);

        return commentRepository.save(comment);
    }

    public boolean deleteComment(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }
}
```
Сервіс для ігор
```
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
```
Сервіс для рейтингу
```
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
```
Сервіс для Юзеру
```
package org.example.coursework.Service;

import org.example.coursework.exception.UserNotFoundException;
import org.example.coursework.model.Role;
import org.example.coursework.model.User;
import org.example.coursework.repository.RoleRepository;
import org.example.coursework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public User createUser(String username, String email, String password) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());

        Role userRole = roleRepository.findByName("ROLE_USER");
        if (userRole == null) {
            userRole = new Role("ROLE_USER");
            roleRepository.save(userRole);
        }
        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        return userOptional.orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        Set<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toSet());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}//
```
## Розділ 3 - Проектування і розробка шару сховища
Шар сховища або доступу до даних (Data Access Layer) - це компонент програмного забезпечення, який відповідає за взаємодію з базою даних або будь-яким іншим механізмом зберігання даних. Його головна мета - забезпечити доступ до даних з інших частин програми та іншого програмного забезпечення.

Одним з головних завдань шару сховища є взаємодія з базою даних. Це включає в себе виконання операцій, таких як збереження (INSERT), оновлення (UPDATE), видалення (DELETE) та отримання (SELECT) даних з бази даних.
Зазвичай шар сховища використовує мову запитів, таку як SQL (Structured Query Language), для взаємодії з базою даних. SQL дозволяє виконувати різноманітні операції з даними, такі як фільтрація, сортування, з'єднання тощо.

Реалізація
Клас Comment
```
package org.example.coursework.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

// Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

}//
```
Клас Game
```
package org.example.coursework.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private LocalDate releaseDate;
    private String developer;
    private String publisher;
    private Double averageRating;

    public Game() {
    }

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "game", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments =  new ArrayList<>();


    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}//
```
Клас Rating 
```
package org.example.coursework.model;

import jakarta.persistence.*;
@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int score;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;


    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}//
```
Клас User 
```
package org.example.coursework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private LocalDateTime createdAt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
    // Constructors, getters and setters
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}//
```

