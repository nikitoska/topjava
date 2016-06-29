package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int MEAL_ID = START_SEQ + 2;
    public static final int MEAL_ADMIN_ID = START_SEQ + 7;

    public static final UserMeal MEAL1 = new UserMeal(MEAL_ID,of(2016, Month.JUNE, 28, 10, 0), "завтрак", 500);
    public static final UserMeal MEAL2 = new UserMeal(MEAL_ID + 1,of(2016, Month.JUNE, 28, 12, 0), "обед", 500);
    public static final UserMeal MEAL3 = new UserMeal(MEAL_ID + 2,of(2016, Month.JUNE, 28, 17, 0), "ужин", 500);
    public static final UserMeal MEAL4 = new UserMeal(MEAL_ID + 3,of(2016, Month.JUNE, 29, 10, 0), "завтрак", 500);
    public static final UserMeal MEAL5 = new UserMeal(MEAL_ID + 4,of(2016, Month.JUNE, 29, 12, 0), "обед", 500);
    public static final UserMeal ADMIN_MEAL1 = new UserMeal(MEAL_ADMIN_ID,of(2016, Month.JUNE, 28, 10, 0), "завтрак", 500);
    public static final UserMeal ADMIN_MEAL2 = new UserMeal(MEAL_ADMIN_ID + 1,of(2016, Month.JUNE, 28, 12, 0), "обед", 500);

    public static final List<UserMeal> USER_MEALS = Arrays.asList(MEAL1,MEAL2,MEAL3,MEAL4,MEAL5);


}
