/**
 * 
 */
package it.cambi.celum.mongo.repository;

import it.cambi.celum.mongo.model.Course;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author luca
 *
 */
public interface CourseRepository extends MongoRepository<Course, Long>
{

    Optional<Course> findOneById(ObjectId id);

}
