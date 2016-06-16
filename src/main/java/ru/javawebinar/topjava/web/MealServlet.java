package ru.javawebinar.topjava.web;

import javax.servlet.http.HttpServlet;
import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.UserMealDao;
import ru.javawebinar.topjava.dao.UserMealDaoImpl;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;


/**
 * Created by nikita on 14.06.2016.
 */
public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    private UserMealDao userMealDao = new UserMealDaoImpl();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");

        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        userMealDao.addUser(userMeal);
        response.sendRedirect("meals");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredMealsWithExceeded = UserMealsUtil.getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(23, 0), 2000);
        LOG.debug("redirect to mealList");
        request.setAttribute("mealList",filteredMealsWithExceeded);




        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
       // response.sendRedirect("mealList.jsp"); */
        String action = request.getParameter("action");

        if (action == null){
            request.setAttribute("mealList",UserMealsUtil.getFilteredWithExceeded(userMealDao.getAll(),LocalTime.of(7, 0), LocalTime.of(23, 0), 2000) );
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        }
        else if (action.equals("delete")){
            int id = Integer.parseInt(request.getParameter("id"));
            userMealDao.delete(id);
        } else if (action.equals("create") || action.equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            UserMeal meal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now().withNano(0).withSecond(0), "", 1000) :
                    userMealDao.get(id);
            request.setAttribute("meal",meal);
            request.getRequestDispatcher("mealEdit.jsp").forward(request, response);

        }
    }
}
