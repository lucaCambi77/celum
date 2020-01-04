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
import it.cambi.celum.mongo.repository.UserRepository;

/**
 * @author luca
 *
 */
@Service
public class UserService
{

    private @Autowired UserRepository userRepository;
    private @Autowired CourseService courseService;
    private @Autowired SequenceGeneratorService sequenceGenerator;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public List<User> findAll()
    {

        return userRepository.findAll();
    }

    public User findByObjectId(String _id)
    {

        return userRepository.findOneById(new ObjectId(_id)).orElseThrow(() -> new RuntimeException("User does not exists"));
    }

    public User save(User user)
    {
        if (null == user.getId())
            user.setUserId((sequenceGenerator.generateSequence(User.SEQUENCE_NAME)));

        log.info("Creating / updating user " + user.getEmail() + "with id " + user.getUserId());

        return userRepository.save(user);
    }

    public User addCourses(String userId, Set<String> courses)
    {

        User user = findByObjectId(userId);

        Set<String> userCourses = user.getCourses();
        userCourses.addAll(courses);
        user.setCourses(userCourses);

        courses.stream().forEach(c -> {
            Course courseToUpdate = courseService.findByObjectId(c);

            Set<String> courseUsers = courseToUpdate.getUsers();
            courseUsers.add(userId);
            courseToUpdate.setUsers(courseUsers);

            courseService.save(courseToUpdate);
        });

        return userRepository.save(user);
    }

    public void deleteById(String id)
    {
        log.info("Deleteing course id : " + id);

        User user = findByObjectId(id);
        user.setDeleted(true);

        userRepository.save(user);
    }

}
