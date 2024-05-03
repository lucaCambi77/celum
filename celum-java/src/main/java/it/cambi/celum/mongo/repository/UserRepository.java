/**
 * 
 */
package it.cambi.celum.mongo.repository;

import it.cambi.celum.mongo.model.User;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author luca
 *
 */
public interface UserRepository extends MongoRepository<User, Long>
{

    Optional<User> findOneById(ObjectId id);

}
