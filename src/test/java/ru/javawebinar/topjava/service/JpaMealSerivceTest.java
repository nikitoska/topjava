package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by nikita on 08.07.2016.
 */
@ActiveProfiles(Profiles.JPA)
public class JpaMealSerivceTest extends AbstractUserMealServiceTest {
}
