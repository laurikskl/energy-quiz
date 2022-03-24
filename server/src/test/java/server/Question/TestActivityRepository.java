package server.Question;

import commons.Activity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import server.Activity.ActivityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;


/**
 * activityRepository used for testing
 * many methods aren't used and therefore don't do anything
 */

public class TestActivityRepository implements ActivityRepository {

    /**
     * Activities are initialized and put into a list
     */

    private final Activity a1 = new Activity("showering", 2000, "wikipedia.com");
    private final Activity a2 = new Activity("watching tv", 1000, "wikipedia.com");
    private final Activity a3 = new Activity("coding", 1500, "wikipedia.com");
    private final Activity a4 = new Activity("using e-bike", 2000, "wikipedia.com");
    private final List<Activity> activities = Arrays.asList(a1, a2, a3, a4);


    /**
     * @return list of all activities
     */

    @Override
    public List<Activity> findAll() {
        return activities;
    }


    /**
     * This method isn't used
     */

    @Override
    public List<Activity> findAll(Sort sort) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public Page<Activity> findAll(Pageable pageable) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public List<Activity> findAllById(Iterable<Long> longs) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public long count() {
        return 4;
    }


    /**
     * This method isn't used
     */

    @Override
    public void deleteById(Long aLong) {

    }


    /**
     * This method isn't used
     */

    @Override
    public void delete(Activity entity) {

    }


    /**
     * This method isn't used
     */

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }


    /**
     * This method isn't used
     */

    @Override
    public void deleteAll(Iterable<? extends Activity> entities) {

    }


    /**
     * This method isn't used
     */

    @Override
    public void deleteAll() {

    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> S save(S entity) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> List<S> saveAll(Iterable<S> entities) {
        return null;
    }


    /**
     * Returns optionals of activities depending on the Long given
     */

    @Override
    public Optional<Activity> findById(Long aLong) {
        if (aLong == 0) {
            return Optional.ofNullable(a1);
        } else if (aLong == 1) {
            return Optional.ofNullable(a2);
        } else if (aLong == 2) {
            return Optional.ofNullable(a3);
        } else if (aLong == 3) {
            return Optional.ofNullable(a4);
        }
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }


    /**
     * This method isn't used
     */

    @Override
    public void flush() {

    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> S saveAndFlush(S entity) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public void deleteAllInBatch(Iterable<Activity> entities) {

    }


    /**
     * This method isn't used
     */

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }


    /**
     * This method isn't used
     */

    @Override
    public void deleteAllInBatch() {

    }


    /**
     * This method isn't used
     */

    @Override
    public Activity getOne(Long aLong) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public Activity getById(Long aLong) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> long count(Example<S> example) {
        return 0;
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity> boolean exists(Example<S> example) {
        return false;
    }


    /**
     * This method isn't used
     */

    @Override
    public <S extends Activity, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

}
