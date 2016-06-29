package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * Created by nikita on 29.06.2016.
 */

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {
    @Autowired
    protected UserMealService service;
    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        UserMeal userMeal = service.get(MEAL_ID, USER_ID);
        MATCHER.assertEquals(MEAL1, userMeal);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL2, MEAL3, MEAL4, MEAL5), service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5),
                service.getBetweenDates(LocalDate.of(2016, Month.JUNE, 28), LocalDate.of(2016, Month.JUNE, 29), USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<UserMeal> actual = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals((USER_MEALS), actual);
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal userMeal = new UserMeal(MEAL_ID, MEAL1.getDateTime(), "обновление", 400);
        service.update(userMeal, USER_ID);
        MATCHER.assertEquals(userMeal, service.get(MEAL_ID, USER_ID));

    }

    @Test
    public void testSave() throws Exception {
        UserMeal userMeal = new UserMeal(null, of(2016, Month.JUNE, 10, 18, 0), "Созданный ужин", 300);
        service.save(userMeal, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(userMeal, MEAL1, MEAL2, MEAL3, MEAL4, MEAL5), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteNotFound() throws Exception {
        service.delete(MEAL_ID, 1);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(MEAL_ID,1);

    }
    @Test(expected = NotFoundException.class)
    public void testUpdateNotFound() throws Exception {
        UserMeal userMeal = new UserMeal(MEAL_ID, MEAL1.getDateTime(), "обновление", 400);
        service.update(userMeal,MEAL_ADMIN_ID);

    }
}