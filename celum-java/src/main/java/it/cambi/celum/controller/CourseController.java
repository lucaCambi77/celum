/**
 * 
 */
package it.cambi.celum.controller;

import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
