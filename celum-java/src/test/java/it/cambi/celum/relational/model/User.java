/**
 * 
 */
package it.cambi.celum.relational.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.GenerationType.SEQUENCE;

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
	private Set<Course> courses;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "courses")
	public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

}
