package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;


import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nikita on 15.06.2016.
 */
public class UserMealDaoImpl implements UserMealDao {

    private Map<Integer,UserMeal> map = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    {
        addUser(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        addUser(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        addUser(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        addUser(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        addUser(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        addUser(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    }


    @Override
    public void addUser(UserMeal userMeal) {
        if (userMeal.getId() == 0){
            userMeal.setId(counter.incrementAndGet());

        }
        map.put(userMeal.getId(),userMeal);

    }

    @Override
    public void delete(int id) {
        map.remove(id);

    }

    @Override
    public UserMeal get(int id) {
        return map.get(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        return map.values();
    }
}
