/**
 * 
 */
package it.cambi.celum.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.cambi.celum.mongo.model.User;

/**
 * @author luca
 *
 */
public interface UserRepository extends MongoRepository<User, Long> {

}
