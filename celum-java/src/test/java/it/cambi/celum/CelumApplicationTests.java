package it.cambi.celum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.mongo.model.User;
import it.cambi.celum.service.CourseService;
import it.cambi.celum.service.UserService;
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

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {Application.class,
        ApplicationConfiguration.class}, webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
public class CelumApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(CelumApplicationTests.class);

    private @Autowired
    UserService userService;

    private @Autowired
    CourseService courseService;

    private static String mathObjectId;

    private static String mathematics = "Mathematics";
    private static String mathematicsUpdate = "MathematicsUpdate";

    private static User persistedUser;
    private static Course persistedCourse;

    @Test
    @Order(1)
    public void persistCourse() throws JsonProcessingException {

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
    public void persistUser() throws JsonProcessingException {

        String newUser = "newUser@gmail.com";

        User userBuild = new User.Builder().withEmail(newUser).withName("New").withLastName("User").withCourses(new HashSet<String>() {
            {
                add(mathObjectId);
            }
        }).build();

        persistedUser = userService.save(userBuild);

        log.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(persistedUser));

        User user = userService.findByObjectId(persistedUser.getId());

        assertEquals(newUser, user.getEmail());
        assertEquals(1, user.getCourses().size());
    }

    @Test
    @Order(3)
    public void updatedCourse() {

        Course course = courseService.findByObjectId(mathObjectId);
        course.setName(mathematicsUpdate);

        courseService.save(course);

        List<Course> courses = courseService.findAll();

        assertEquals(1, courses.size());
        assertEquals(1, courses.get(0).getCourseId());
        assertEquals(1, courses.get(0).getUsers().size());
        assertEquals(mathematicsUpdate, courses.get(0).getName());
    }

    @Test
    @Order(4)
    public void deleteUser() {

        userService.deleteById(persistedUser.getId());

        User user = userService.findByObjectId(persistedUser.getId());

        assertEquals(user.isDeleted(), true);
    }

    @Test
    @Order(5)
    public void deleteCourse() {

        courseService.deleteById(persistedCourse.getId());

        List<Course> courses = courseService.findAll();

        assertEquals(courses.stream().filter(u -> u.getId().equals(persistedCourse.getId())).findFirst().get().isDeleted(), true);
    }
}
