/**
 * 
 */
package it.cambi.celum.mongo.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.celum.mongo.model.Course;

/**
 * @author luca
 *
 */
public interface CourseRepository extends MongoRepository<Course, Long>
{

    Optional<Course> findOneById(ObjectId id);

}
