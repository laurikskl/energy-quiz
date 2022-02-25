package server.Game;

import lombok.*;

import javax.persistence.*;



@Entity
@Table
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class Game {

    @Id
    @SequenceGenerator(
            name = "game_sequence",
            sequenceName = "game_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "game_sequence"
    )
    private Integer id;
    private String username;
    private Integer score;

    public Game(String username, Integer score) {

        this.username = username;
        this.score = score;
    }
}
