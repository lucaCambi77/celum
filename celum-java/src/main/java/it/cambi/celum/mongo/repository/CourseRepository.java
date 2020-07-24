/**
 * 
 */
package it.cambi.celum.mongo.repository;

import it.cambi.celum.mongo.model.Course;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @author luca
 *
 */
public interface CourseRepository extends MongoRepository<Course, Long>
{

    Optional<Course> findOneById(ObjectId id);

}
