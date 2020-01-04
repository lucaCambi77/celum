package it.cambi.celum;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.mongo.model.User;
import it.cambi.celum.service.CourseService;
import it.cambi.celum.service.UserService;

@SpringBootTest(classes = { Application.class,
        ApplicationConfiguration.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CelumApplicationTests
{

    private static final Logger log = LoggerFactory.getLogger(CelumApplicationTests.class);

    private @Autowired UserService userService;

    private @Autowired CourseService courseService;

    private static String mathObjectId;;

    private static String mathematics = "Mathematics";
    private static String mathematicsUpdate = "MathematicsUpdate";

    private static User persistedUser;
    private static Course persistedCourse;

    @Test
    @Order(1)
    public void persistCourse() throws JsonProcessingException
    {

        Course course = new Course.Builder().withName(mathematics).withStarDate(new Date()).build();

        persistedCourse = courseService.save(course);

        log.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(course));

        List<Course> courses = courseService.findAll();

        assertEquals(1, courses.size());
        assertEquals(mathematics, courses.get(0).getName());

        mathObjectId = courses.get(0).getId();
    }

    @Test
    @Order(2)
    public void persistUser() throws JsonProcessingException
    {

        User user = new User.Builder().withEmail("lucacambi77@gmail.com").withName("Luca").build();

        persistedUser = userService.save(user);

        log.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(persistedUser));

        List<User> users = userService.findAll();

        assertEquals(2, users.size());
        assertEquals("lucacambi77@gmail.com", persistedUser.getEmail());

    }

    @SuppressWarnings("serial")
    @Test
    @Order(3)
    public void addCourseToUser() throws JsonProcessingException
    {

        userService.addCourses(persistedUser.getId(), new HashSet<String>()
        {
            {
                add(mathObjectId);
            }
        });

        List<User> users = userService.findAll();

        assertEquals(2, users.size());

        assertEquals(true, users.stream().filter(u -> u.getUserId() == persistedUser.getUserId()).findFirst()
                .orElse(null).getCourses().stream().anyMatch(c -> c.toString().equals(mathObjectId.toString())));

    }

    @Test
    @Order(4)
    public void updatedCourse() throws JsonProcessingException
    {

        Course course = courseService.findByObjectId(mathObjectId);
        course.setName(mathematicsUpdate);

        courseService.save(course);

        List<Course> courses = courseService.findAll();

        assertEquals(1, courses.size());
        assertEquals(1, courses.get(0).getCourseId());
        assertEquals(1, courses.get(0).getUsers().size());
        assertEquals(mathematicsUpdate, courses.get(0).getName());

    }

    @SuppressWarnings("serial")
    @Test
    @Order(5)
    public void addUsersToCourse() throws JsonProcessingException
    {

        User user = new User.Builder().withEmail("lucacambi78@gmail.com").withName("Luca").build();

        User persistedUser = userService.save(user);

        courseService.addUsers(mathObjectId, new HashSet<String>()
        {
            {
                add(persistedUser.getId());
            }
        });

        List<Course> courses = courseService.findAll();

        assertEquals(1, courses.size());
        assertEquals(1, courses.get(0).getCourseId());
        assertEquals(2, courses.get(0).getUsers().size());
        assertEquals("MathematicsUpdate", courses.get(0).getName());

    }

    @Test
    @Order(6)
    public void deleteUser() throws JsonProcessingException
    {

        userService.deleteById(persistedUser.getId());

        List<User> users = userService.findAll();

        assertEquals(users.stream().filter(u -> u.getId().equals(persistedUser.getId())).findFirst().get().isDeleted(), true);
    }

    @Test
    @Order(7)
    public void deleteCourse() throws JsonProcessingException
    {

        courseService.deleteById(persistedCourse.getId());

        List<Course> courses = courseService.findAll();

        assertEquals(courses.stream().filter(u -> u.getId().equals(persistedCourse.getId())).findFirst().get().isDeleted(), true);

    }
}
