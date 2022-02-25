package server.Game;

import commons.Game;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class GameConfig {

    @Bean
    CommandLineRunner commandLineRunner(GameRepository repository) {
        return args -> {
            Game game1 = new Game(
                    "Kuba",
                    10
            );

            Game game2 = new Game(
                    "Lauri",
                    11
            );

            repository.saveAll(List.of(game1, game2));
        };
    }
}
