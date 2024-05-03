/**
 * 
 */
package it.cambi.celum.relational.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import org.springframework.data.annotation.Id;

/**
 * @author luca
 *
 */
@Entity
@Table(name = "COURSE", schema = "CELUM")
public class Course {

	@Id
	private long courseId;
	private String name;
	private Date startDate;
	private Set<UserCourse> users;

	@Id
	@SequenceGenerator(name = "courseSeqgenerator", sequenceName = "CELUM.COURSE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = SEQUENCE, generator = "courseSeqgenerator")
    @Column(name = "course", nullable = false, precision = 10, scale = 0)
	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long id) {
		this.courseId = id;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<UserCourse> getUsers() {
		return users;
	}

	public void setUsers(Set<UserCourse> users) {
		this.users = users;
	}

}
