/**
 * 
 */
package it.cambi.celum.mongo.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.annotation.JsonIgnoreType;

/**
 * @author luca
 * 
 *         ObjectId is a logical key and it is serialized as {@link ObjectId#toString()} and it works also as a reference (foreign key in relation
 *         databases). An auto incrementing sequence is created as a primary key in case also of a migration to a relational data base
 *
 */
@Document
public class Course
{

    private @MongoId ObjectId id;

    @Transient
    public static final String SEQUENCE_NAME = "course_sequence";

    private long courseId;
    private String name;
    private Date startDate;
    private Set<String> users;
    private boolean isDeleted;

    @JsonIgnoreType
    public static class Builder
    {
        private long id;
        private String name;
        private Date startDate;
        private Set<String> users;
        private ObjectId _id;

        public Builder withId(long id)
        {
            this.id = id;
            return this;
        }

        public Builder withName(String name)
        {
            this.name = name;
            return this;
        }

        public Builder withStarDate(Date startDate)
        {
            this.startDate = startDate;
            return this;
        }

        public Builder withUsers(Set<String> users)
        {
            this.users = users;
            return this;
        }

        public Builder withObjectId(ObjectId _id)
        {
            this._id = _id;
            return this;
        }

        public Course build()
        {

            Course course = new Course();
            course.courseId = this.id;
            course.name = this.name;
            course.startDate = this.startDate;
            course.users = this.users;
            course.id = this._id;

            return course;
        }
    }

    public long getCourseId()
    {
        return courseId;
    }

    public void setCourseId(long id)
    {
        this.courseId = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Set<String> getUsers()
    {
        return users == null ? new HashSet<String>() : users;
    }

    public void setUsers(Set<String> users)
    {
        this.users = users;
    }

    public String getId()
    {
        return null == id ? null : id.toString();
    }

    public boolean isDeleted()
    {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted)
    {
        this.isDeleted = isDeleted;
    }

}
