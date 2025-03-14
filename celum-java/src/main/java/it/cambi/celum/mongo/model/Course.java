/** */
package it.cambi.celum.mongo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

/**
 * @author luca
 *     <p>ObjectId is a logical key and it is serialized as {@link ObjectId#toString()} and it works
 *     also as a reference (foreign key in relation databases). An auto incrementing sequence is
 *     created as a primary key in case also of a migration to a relational data base
 */
@Document
@Builder
@Data
public class Course {

  private @MongoId ObjectId id;

  @Transient public static final String SEQUENCE_NAME = "course_sequence";

  private long courseId;
  private String name;
  private Date startDate;
  @Builder.Default private Set<String> users = new HashSet<>();
  private boolean isDeleted;

  public String getId() {
    return null == id ? null : id.toString();
  }
}
