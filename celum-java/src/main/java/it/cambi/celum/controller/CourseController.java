/**
 * 
 */
package it.cambi.celum.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.service.CourseService;

/**
 * @author luca
 *
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/course")
public class CourseController
{

    private @Autowired CourseService userService;

    @GetMapping("/test")
    public String home()
    {
        return "Hello Course!";
    }

    @GetMapping
    public List<Course> findAll()
    {

        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Course findById(@PathVariable("id") String id)
    {
        return userService.findByObjectId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course save(@RequestBody Course course)
    {

        return userService.save(course);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id)
    {
        userService.deleteById(id);
    }

    @PutMapping("/addUsers/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public void addUsers(@PathVariable String courseId, @RequestParam("users") Set<String> users)
    {
        userService.addUsers(courseId, users);
    }
}
