/**
 * 
 */
package it.cambi.celum.mongo.model;

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
@Document(collection = "user")
public class User
{

    private @MongoId ObjectId id;

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    private long userId;

    private String name;
    private String lastName;
    private String address;
    private String phone;

    private String email;
    private Set<String> courses;
    private boolean isDeleted;

    @JsonIgnoreType
    public static class Builder
    {
        private long id;
        private String name;
        private String lastName;
        private String address;
        private String phone;
        private String email;
        private Set<String> courses;

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

        public Builder withLastName(String lastName)
        {
            this.lastName = lastName;
            return this;
        }

        public Builder withAddress(String address)
        {
            this.address = address;
            return this;
        }

        public Builder withPhone(String phone)
        {
            this.phone = phone;
            return this;
        }

        public Builder withEmail(String email)
        {
            this.email = email;
            return this;
        }

        public Builder withCourses(Set<String> courses)
        {
            this.courses = courses;
            return this;
        }

        public User build()
        {

            User user = new User();
            user.address = this.address;
            user.email = this.email;
            user.userId = this.id;
            user.name = this.name;
            user.phone = this.phone;
            user.courses = this.courses;
            user.lastName = this.lastName;

            return user;
        }
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long id)
    {
        this.userId = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Set<String> getCourses()
    {
        return courses == null ? new HashSet<String>() : courses;

    }

    public void setCourses(Set<String> courses)
    {
        this.courses = courses;
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
