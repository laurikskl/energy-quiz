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
package server.api;

import commons.Player;
import commons.Quote;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import server.database.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class TestPlayerRepository implements PlayerRepository {

    public final List<Player> players = new ArrayList<>();
    public final List<String> calledMethods = new ArrayList<>();

    private void call(String name) {
        calledMethods.add(name);
    }

    @Override
    public List<Player> findAll() {
        calledMethods.add("findAll");
        return players;
    }

    @Override
    public List<Player> findAll(Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Player> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public Player getOne(Long id) {
        return null;
    }

    @Override
    public List<Player> findAllByUsername(Iterable<String> usernames) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Player> List<S> saveAll(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Player> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public void flush() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends Player> S saveAndFlush(S entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Player> List<S> saveAllAndFlush(Iterable<S> entities) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<Player> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<String> strings) {
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteAllByUsernameInBatch(Iterable<String> usernames) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllInBatch() {
        // TODO Auto-generated method stub

    }

    @Override
    public Player getOne(String id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Player getById(String s) {
        return null;
    }

    @Override
    public Player getByUsername(String username) {
        call("getByUsername");
        return find(username).get();
    }

    private Optional<Player> find(String username) {
        return players.stream().filter(q -> q.userName.equals(username)).findFirst();
    }

    @Override
    public <S extends Player> List<S> findAll(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Player> List<S> findAll(Example<S> example, Sort sort) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Player> findAll(Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Player> S save(S entity) {
        call("save");
        entity.userName = String.valueOf((long) players.size());
        players.add(entity);
        return entity;
    }

    @Override
    public Optional<Quote> findByUsername(String username) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        call("existsById");
        return find(username).isPresent();
    }

    @Override
    public long count() {
        return players.size();
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void deleteByUsername(String username) {
        // TODO Auto-generated method stub

    }

    @Override
    public void delete(Player entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAllById(Iterable<? extends String> usernames) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll(Iterable<? extends Player> entities) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub

    }

    @Override
    public <S extends Player> Optional<S> findOne(Example<S> example) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Player> Page<S> findAll(Example<S> example, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <S extends Player> long count(Example<S> example) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public <S extends Player> boolean exists(Example<S> example) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public <S extends Player, R> R findBy(Example<S> example, Function<FetchableFluentQuery<S>, R> queryFunction) {
        // TODO Auto-generated method stub
        return null;
    }
}