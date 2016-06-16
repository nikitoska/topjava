package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.UserMeal;


import java.util.Collection;


/**
 * Created by nikita on 15.06.2016.
 */
public interface UserMealDao {
    public void addUser(UserMeal userMeal);
    public void delete(int id);
    public UserMeal get(int id);
    Collection<UserMeal> getAll();
}
