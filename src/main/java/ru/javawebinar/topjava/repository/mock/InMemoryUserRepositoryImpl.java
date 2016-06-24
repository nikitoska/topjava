package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer,User> repository = new ConcurrentHashMap<>();
    //private static final Comparator<User> USER_COMPARATOR = Comparator.comparing(User::getName);

    public static final int ADMIN_ID = 2;
    public static final int USER_ID = 1;


    private AtomicInteger counter = new AtomicInteger(0);

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
       if (repository.containsKey(id)){
           repository.remove(id);
           return true;

       }
        return false;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()){
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(),user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        if (repository.containsKey(id)) {
            return repository.get(id);
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
      List<User> values = new ArrayList<>(repository.values());
        Collections.sort(values, (o1, o2) -> o1.getName().compareTo(o2.getName()));
        return values;
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        return repository.values().stream()
                .filter(u -> email.equals(u.getEmail()))
                .findFirst()
                .orElse(null);
    }
}