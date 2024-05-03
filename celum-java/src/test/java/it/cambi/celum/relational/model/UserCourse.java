/**
 *
 */
package it.cambi.celum.relational.model;


import javax.persistence.*;

/**
 * @author luca
 *
 */
@Entity
@Table(name = "USER_COURSE", schema = "CELUM")
public class UserCourse {

    private UserCourseId userCourseId;

    private User user;
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "courseId", referencedColumnName = "courseId", nullable = false, insertable = false, updatable = false)})
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false, insertable = false, updatable = false)})
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "courseId", column = @Column(name = "courseId", nullable = false)),
            @AttributeOverride(
                    name = "userId",
                    column = @Column(name = "userId", nullable = false))
    })
    public UserCourseId getUserCourseId() {
        return userCourseId;
    }

    public void setUserCourseId(UserCourseId userCourseId) {
        this.userCourseId = userCourseId;
    }
}
