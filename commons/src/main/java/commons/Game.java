package commons;

import lombok.*;

import javax.persistence.*;

@Entity
@Table
@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString @AllArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    private String username;
    private Integer score;

    public Game() {
    }

    public Game(String username, Integer score) {

        this.username = username;
        this.score = score;
    }
}
