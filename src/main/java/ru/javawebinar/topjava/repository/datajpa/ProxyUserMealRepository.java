package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by nikita on 08.07.2016.
 */
@Transactional(readOnly = true)
public interface ProxyUserMealRepository extends JpaRepository<UserMeal, Integer> {

    @Transactional
    @Modifying
    @Query("delete from UserMeal u where u.id=:id and u.user.id=:userId")
    int delete(@Param("id") int id,@Param("userId") int userId);

    @Transactional
    UserMeal save(UserMeal userMeal);

    @Query("select u from  UserMeal u where u.id=:id and u.user.id=:userId")
    UserMeal get(@Param("id")int id,@Param("userId") int userId);

    @Query("select u from UserMeal u where u.user.id=:userId")
    List<UserMeal> getAll(@Param("userId")int userId);

    @Query("select u from UserMeal u where u.user.id=:userId and u.dateTime between :startDate and :endDate order by u.dateTime DESC ")
    List<UserMeal> getBetween(@Param("startDate")LocalDateTime startDate,@Param("endDate") LocalDateTime endDate,@Param("userId") int userId);


}
