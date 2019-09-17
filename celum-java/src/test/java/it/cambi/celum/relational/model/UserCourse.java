/**
 * 
 */
package it.cambi.celum.relational.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Id;

/**
 * @author luca
 *
 */
@Entity
@Table(name = "USER_COURSE", schema = "CELUM")
public class UserCourse {

	@Id
	private User user;
	private Course course;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "courseId", referencedColumnName = "courseId", nullable = false, insertable = false, updatable = false) })
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false, insertable = false, updatable = false) })
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

}
