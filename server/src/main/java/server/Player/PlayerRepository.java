package server.Player;

import commons.Player;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for players.
 */
public interface PlayerRepository extends JpaRepository<Player, Long> {
}