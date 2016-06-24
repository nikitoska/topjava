package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.to.UserMealWithExceed;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserMealService service;

    public List<UserMealWithExceed> getAll(){
        LOG.info("getAll meals ");
      return   UserMealsUtil.getWithExceeded(service.getAll(LoggedUser.id()), LoggedUser.getCaloriesPerDay());
    }
    public UserMeal get(int id){
        LOG.info("get meal " + id);
        int userId = LoggedUser.id();
        return service.get(id,userId);
    }

    public void delete(int id){
        int userId = LoggedUser.id();
        LOG.info("delete meal " + id);
        service.delete(id,userId);
    }
    public UserMeal create(UserMeal userMeal){
        int userId = LoggedUser.id();
        LOG.info("create meal " + userMeal);
        return service.save(userMeal,userId);
    }

    public UserMeal update(UserMeal userMeal){
        int userId = LoggedUser.id();
        LOG.info("update meal " + userMeal);
        return service.update(userMeal,userId);
    }

    public List<UserMealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
        int userId = LoggedUser.id();
        LOG.info("Usermeal between " + userId);
        return UserMealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, userId),
                startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX, LoggedUser.getCaloriesPerDay() );
    }




}
