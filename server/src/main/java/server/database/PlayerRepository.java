/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package server.database;

import commons.Player;
import commons.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, String> {

    Player getOne(Long id);

    List<Player> findAllByUsername(Iterable<String> usernames);

    void deleteAllByUsernameInBatch(Iterable<String> usernames);

    Player getByUsername(String username);

    Optional<Quote> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);
}