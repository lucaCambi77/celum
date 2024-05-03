package it.cambi.celum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.mongo.model.User;
import it.cambi.celum.service.CourseService;
import it.cambi.celum.service.UserService;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = {Application.class, ApplicationConfiguration.class})
@ExtendWith(SpringExtension.class)
public class CelumIntegrationTest {

  private static final Logger log = LoggerFactory.getLogger(CelumIntegrationTest.class);

  private @Autowired UserService userService;

  private @Autowired CourseService courseService;

  private static String mathematics = "Mathematics";
  private static String mathematicsUpdate = "MathematicsUpdate";

  @Autowired private MongoTemplate mongoTemplate;

  @BeforeEach
  void cleanUp() {
    mongoTemplate.dropCollection(User.class);
    mongoTemplate.dropCollection(Course.class);
  }

  @Test
  public void persistCourse() throws JsonProcessingException {

    Course course = createCourse(mathematics);

    log.info(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(course));

    List<Course> courses = courseService.findAll();

    assertEquals(1, courses.size());
    assertEquals(mathematics, courses.get(0).getName());
  }

  @Test
  public void persistUser() {
    Course course = createCourse(mathematics);

    User newUser = createUser(course);

    User user = userService.findByObjectId(newUser.getId());

    assertEquals(newUser.getEmail(), user.getEmail());
    assertEquals(1, user.getCourses().size());
  }

  @Test
  public void updatedCourse() {

    Course course = createCourse(mathematics);
    course.setName(mathematicsUpdate);

    courseService.save(course);

    List<Course> courses = courseService.findAll();

    assertEquals(1, courses.size());
    assertEquals(mathematicsUpdate, courses.get(0).getName());
  }

  @Test
  public void deleteUser() {
    Course course = createCourse(mathematics);
    User newUser = createUser(course);

    userService.deleteById(newUser.getId());

    User user = userService.findByObjectId(newUser.getId());

    assertEquals(user.isDeleted(), true);
  }

  @Test
  public void deleteCourse() {
    Course course = createCourse(mathematics);

    courseService.deleteById(course.getId());

    List<Course> courses = courseService.findAll();

    assertTrue(
        courses.stream()
            .filter(u -> u.getId().equals(course.getId()))
            .findFirst()
            .get()
            .isDeleted());
  }

  private User createUser(Course course) {
    String newUser = "newUser@gmail.com";

    User userBuild =
        User.builder()
            .email(newUser)
            .name("New")
            .lastName("User")
            .courses(
                new HashSet<>() {
                  {
                    add(course.getId());
                  }
                })
            .build();

    return userService.save(userBuild);
  }

  private Course createCourse(String courseName) {
    Course course = Course.builder().name(courseName).startDate(new Date()).build();

    return courseService.save(course);
  }
}
