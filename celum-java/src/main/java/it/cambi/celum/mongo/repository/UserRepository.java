/**
 * 
 */
package it.cambi.celum.mongo.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.celum.mongo.model.User;

/**
 * @author luca
 *
 */
public interface UserRepository extends MongoRepository<User, Long>
{

    Optional<User> findOneById(ObjectId id);

}
