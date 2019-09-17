/**
 * 
 */
package it.cambi.celum.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.mongo.model.User;
import it.cambi.celum.mongo.repository.CourseRepository;
import it.cambi.celum.mongo.repository.UserRepository;

/**
 * @author luca
 *
 */
@Service
public class UserService {

	private @Autowired UserRepository userRepository;
	private @Autowired MongoTemplate mongoTemplate;
	private @Autowired CourseRepository courseRepository;
	private @Autowired SequenceGeneratorService sequenceGenerator;

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	public List<User> findAll() {

		return userRepository.findAll();
	}

	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	public User findByObjectId(ObjectId _id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(_id.getTimestamp(), _id.getMachineIdentifier(),
				_id.getProcessIdentifier(), _id.getCounter())));

		return mongoTemplate.findOne(query, User.class);
	}

	public User save(User user) {

		/**
		 * This version of mondo db doesn't return _id on save, so we get the user from
		 * db after save
		 */

		if (null == user.get_id()) {

			user.setUserId((sequenceGenerator.generateSequence(User.SEQUENCE_NAME)));
			userRepository.save(user);
			user.set_id(findById(user.getUserId()).get_id());
		}

		log.info("Creating / updating user " + user.getEmail() + "with id " + user.getUserId());

		return addCourses(user);
	}

	public User addCourses(User user) {

		/**
		 * Find and update courses
		 */
		Set<String> userCourses = user.getCourses() == null ? new HashSet<String>() : user.getCourses();

		List<Course> courses = courseRepository.findAll();

		/**
		 * If a user has a course, but it has been removed from the input, we remove it from the user.
		 * Otherwise we add it anyway
		 */
		courses.stream().forEach(c -> {
			if (null != c.getUsers() && c.getUsers().stream().anyMatch(u -> u.equals(user.get_id()))
					&& !userCourses.contains(c.get_id())) {

				Set<String> courseUsers = c.getUsers();
				if (null != courseUsers) {

					courseUsers.remove(user.get_id());
					c.setUsers(courseUsers);
					courseRepository.save(c);
				}
			} else if (userCourses.contains(c.get_id())) {
				Set<String> courseUsers = c.getUsers() == null ? new HashSet<String>() : c.getUsers();
				courseUsers.add(user.get_id());
				c.setUsers(courseUsers);
				courseRepository.save(c);
			}
		});

		return userRepository.save(user);
	}

	public void deleteById(Long id) {
		log.info("Deleteing course id : " + id);

		User user = findById(id);

		/**
		 * Find and update courses
		 */

		if (null != user.getCourses()) {

			Set<ObjectId> courseObjectId = new HashSet<ObjectId>();
			user.getCourses().forEach(c -> courseObjectId.add(new ObjectId(c)));

			Query query = new Query();
			query.addCriteria(Criteria.where("_id").in(courseObjectId));

			List<Course> courses = mongoTemplate.find(query, Course.class);

			if (null != courses)
				courses.forEach(c -> {

					if (null != c.getUsers()) {

						log.info("Deleteing user : " + user.getName() + " from course " + c.getName());
						c.getUsers().removeIf((u -> u != null && u.toString().equals(user.get_id().toString())));

						courseRepository.save(c);
					}
				});

		}

		userRepository.deleteById(id);
	}

}
