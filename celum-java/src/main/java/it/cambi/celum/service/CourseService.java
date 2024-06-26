/** */
package it.cambi.celum.service;

import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.mongo.model.User;
import it.cambi.celum.mongo.repository.CourseRepository;
import it.cambi.celum.mongo.repository.UserRepository;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author luca
 */
@Service
@RequiredArgsConstructor
public class CourseService {
  private static final Logger log = LoggerFactory.getLogger(CourseService.class);

  private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final SequenceGeneratorService sequenceGenerator;

  public List<Course> findAll() {
    return courseRepository.findAll();
  }

  public Course findByObjectId(String _id) {

    return courseRepository
        .findOneById(new ObjectId(_id))
        .orElseThrow(() -> new RuntimeException("Course does not exists"));
  }

  public Course update(Course course) {
    log.info("Updating course " + course.getName());
    return courseRepository.save(course);
  }

  public Course save(Course course) {

    if (null == course.getId())
      course.setCourseId(sequenceGenerator.generateSequence(Course.SEQUENCE_NAME));

    log.info("Creating / updating course " + course.getName());

    Course savedCourse = courseRepository.save(course);

    addUsers(savedCourse.getId(), savedCourse.getUsers());

    return savedCourse;
  }

  public void addUsers(String courseId, Set<String> users) {

    users.forEach(
        u -> {
          User userToUpdate = userRepository.findOneById(new ObjectId(u)).get();

          Set<String> userCourses = userToUpdate.getCourses();
          userCourses.add(courseId);
          userToUpdate.setCourses(userCourses);

          userRepository.save(userToUpdate);
        });
  }

  public void deleteById(String id) {
    log.info("Deleting course id : " + id);

    Course course = findByObjectId(id);
    course.setDeleted(true);

    courseRepository.save(course);
  }
}
