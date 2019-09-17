/**
 * 
 */
package it.cambi.celum.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.celum.mongo.model.Course;

/**
 * @author luca
 *
 */
public interface CourseRepository extends MongoRepository<Course, Long> {

}
