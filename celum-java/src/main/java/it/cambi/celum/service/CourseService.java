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
public class CourseService {
	private static final Logger log = LoggerFactory.getLogger(CourseService.class);

	private @Autowired CourseRepository courseRepository;
	private @Autowired UserRepository userRepository;
	private @Autowired MongoTemplate mongoTemplate;
	private @Autowired SequenceGeneratorService sequenceGenerator;

	public List<Course> findAll() {

		return courseRepository.findAll();
	}

	public Course findById(Long id) {
		return courseRepository.findById(id).get();
	}

	public Course findByObjectId(String _id) {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(new ObjectId(_id)));

		return mongoTemplate.findOne(query, Course.class);
	}

	public Course save(Course course) {

		/**
		 * This version of mondo db doesn't return _id on save, so we get the course
		 * from db after save
		 */
		if (null == course.get_id()) {

			course.setCourseId(sequenceGenerator.generateSequence(Course.SEQUENCE_NAME));
			courseRepository.save(course);
			course.set_id(findById(course.getCourseId()).get_id());
		}

		log.info("Creating / updating course " + course.getName());

		return addUsers(course);
	}

	public Course addUsers(Course course) {

		/**
		 * Find and update users
		 */
		Set<String> courseUsers = course.getUsers() == null ? new HashSet<String>() : course.getUsers();

		List<User> users = userRepository.findAll();

		/**
		 * If a course has a user, but it has been removed from the input, we remove it from the course.
		 * Otherwise we add it anyway
		 */
		users.stream().forEach(u -> {

			if (null != u.getCourses() && u.getCourses().stream().anyMatch(c -> c.equals(course.get_id()))
					&& !courseUsers.contains(u.get_id())) {

				Set<String> userCourses = u.getCourses();
				if (null != userCourses) {
					userCourses.remove(course.get_id());
					u.setCourses(userCourses);
					userRepository.save(u);
				}
			} else if (courseUsers.contains(u.get_id())) {
				Set<String> userCourses = u.getCourses() == null ? new HashSet<String>() : u.getCourses();
				userCourses.add(course.get_id());
				u.setCourses(userCourses);
				userRepository.save(u);

			}
		});

		return courseRepository.save(course);

	}

	public void deleteById(Long id) {
		log.info("Deleteing course id : " + id);

		Course course = findById(id);

		/**
		 * Find and update users
		 */

		if (null != course.getUsers()) {

			Set<ObjectId> usersObjectId = new HashSet<ObjectId>();
			course.getUsers().forEach(c -> usersObjectId.add(new ObjectId(c)));

			Query query = new Query();
			query.addCriteria(Criteria.where("_id").in(usersObjectId));

			List<User> users = mongoTemplate.find(query, User.class);

			if (null != users)
				users.forEach(u -> {

					if (null != u.getCourses()) {

						log.info("Deleteing course : " + course.getName() + " from user " + u.getEmail());

						u.getCourses().removeIf((c -> c != null && c.toString().equals(course.get_id().toString())));

						userRepository.save(u);
					}
				});
		}

		courseRepository.deleteById(id);
	}
}
