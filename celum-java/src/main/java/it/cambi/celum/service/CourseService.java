/**
 * 
 */
package it.cambi.celum.service;

import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.mongo.model.User;
import it.cambi.celum.mongo.repository.CourseRepository;

/**
 * @author luca
 *
 */
@Service
public class CourseService
{
    private static final Logger log = LoggerFactory.getLogger(CourseService.class);

    private @Autowired CourseRepository courseRepository;
    private @Autowired UserService userService;
    private @Autowired SequenceGeneratorService sequenceGenerator;

    public List<Course> findAll()
    {

        return courseRepository.findAll();
    }

    public Course findByObjectId(String _id)
    {

        return courseRepository.findOneById(new ObjectId(_id)).orElseThrow(() -> new RuntimeException("Course does not exists"));
    }

    public Course save(Course course)
    {

        if (null == course.getId())
            course.setCourseId(sequenceGenerator.generateSequence(Course.SEQUENCE_NAME));

        log.info("Creating / updating course " + course.getName());

        return courseRepository.save(course);
    }

    public Course addUsers(String courseId, Set<String> users)
    {

        Course course = findByObjectId(courseId);

        Set<String> courseUsers = course.getUsers();
        courseUsers.addAll(users);
        course.setUsers(courseUsers);

        users.stream().forEach(u -> {
            User courseToUpdate = userService.findByObjectId(u);

            Set<String> userCourses = courseToUpdate.getCourses();
            userCourses.add(courseId);
            courseToUpdate.setCourses(userCourses);

            userService.save(courseToUpdate);
        });

        return courseRepository.save(course);

    }

    public void deleteById(String id)
    {
        log.info("Deleteing course id : " + id);

        Course course = findByObjectId(id);
        course.setDeleted(true);

        courseRepository.save(course);
    }
}
