package commons;

import javax.persistence.*;



@Entity
@Table
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

    public Game() {};

    public Game(Integer id, String username, Integer score) {
        this.id = id;
        this.username = username;
        this.score = score;
    }

    public Game(String username, Integer score) {
        this.username = username;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Integer getScore() {
        return score;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
