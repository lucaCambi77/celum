/**
 * 
 */
package it.cambi.celum.relational.model;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.Set;
import javax.persistence.*;
import org.springframework.data.annotation.Id;

/**
 * @author luca
 *
 */
@Entity
@Table(name = "USER", schema = "CELUM")
public class User {

	@Id
	private long userId;

	private String name;
	private String lastName;
	private String address;
	private String phone;

	private String email;
	private Set<UserCourse> courses;

	@Id
    @SequenceGenerator(name = "userSeqgenerator", sequenceName = "CELUM.USER_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "userSeqgenerator")
    @Column(name = "userId", nullable = false, precision = 10, scale = 0)
	public long getUserId() {
		return userId;
	}

	public void setUserId(long id) {
		this.userId = id;
	}

	@Column
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
	public Set<UserCourse> getCourses() {
		return courses;
	}

	public void setCourses(Set<UserCourse> courses) {
		this.courses = courses;
	}

}
