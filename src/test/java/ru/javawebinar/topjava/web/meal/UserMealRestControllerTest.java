package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * Created by nikita on 22.07.2016.
 */
public class UserMealRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = UserMealRestController.REST_URL + '/';
    @Autowired
    private UserMealService service;
    @Test
    public void testGet() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MealTestData.MATCHER.contentMatcher(MEAL1));

    }

    @Test
    public void testDelete() throws Exception {

        mockMvc.perform(delete(REST_URL + MEAL1_ID).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(MEAL6,MEAL5,MEAL4,MEAL3,MEAL2),service.getAll(START_SEQ));

    }

    @Test
    public void testGetAll() throws Exception {

        mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER2.contentListMatcher(UserMealsUtil.getWithExceeded(USER_MEALS, USER.getCaloriesPerDay())));

    }

    @Test
    public void testUpdate() throws Exception {

        UserMeal userMeal = getUpdated();
        mockMvc.perform(put(REST_URL + MEAL1_ID).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(userMeal)))
                .andExpect(status().isOk());
        MealTestData.MATCHER.assertEquals(userMeal,service.get(MEAL1_ID,START_SEQ));
    }

    @Test
    public void testCreate() throws Exception {
        UserMeal userMeal = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(userMeal)));

        UserMeal returned = MealTestData.MATCHER.fromJsonAction(action);
        userMeal.setId(returned.getId());

        MealTestData.MATCHER.assertEquals(userMeal, returned);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(userMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));

    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "filter?startDateTime=2015-05-30T07:00&endDateTime=2015-05-31T11:00:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MATCHER2.contentListMatcher(UserMealsUtil.createWithExceed(MEAL4, true),
                                                        UserMealsUtil.createWithExceed(MEAL1, false)));

    }

}