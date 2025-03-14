/** */
package it.cambi.celum.service;

import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.mongo.model.User;
import it.cambi.celum.mongo.repository.CourseRepository;
import it.cambi.celum.mongo.repository.UserRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
public class UserService {

  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final SequenceGeneratorService sequenceGenerator;

  private static final Logger log = LoggerFactory.getLogger(UserService.class);

  public List<User> findAll() {
    return userRepository.findAll().stream()
        .filter(u -> !u.isDeleted())
        .collect(Collectors.toList());
  }

  public User findByObjectId(String _id) {
    return userRepository
        .findOneById(new ObjectId(_id))
        .orElseThrow(() -> new RuntimeException("User does not exists"));
  }

  public User save(User user) {
    if (null == user.getId())
      user.setUserId((sequenceGenerator.generateSequence(User.SEQUENCE_NAME)));

    log.info("Creating / updating user " + user.getEmail() + "with id " + user.getUserId());

    User savedUser = userRepository.save(user);

    addToCourses(savedUser.getId(), savedUser.getCourses());

    return savedUser;
  }

  public void addToCourses(String userId, Set<String> courses) {
    courses.forEach(
        c -> {
          Course courseToUpdate = courseRepository.findOneById(new ObjectId(c)).get();

          Set<String> courseUsers = courseToUpdate.getUsers();
          courseUsers.add(userId);
          courseToUpdate.setUsers(courseUsers);

          courseRepository.save(courseToUpdate);
        });
  }

  public void deleteById(String id) {
    log.info("Deleteing course id : " + id);

    User user = findByObjectId(id);
    user.setDeleted(true);

    userRepository.save(user);
  }
}
