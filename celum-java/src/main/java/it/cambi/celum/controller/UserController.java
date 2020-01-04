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

import it.cambi.celum.mongo.model.User;
import it.cambi.celum.service.UserService;

/**
 * @author luca
 *
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/user")
public class UserController
{

    private @Autowired UserService userService;

    @GetMapping("/test")
    public String home()
    {
        return "Hello Users!";
    }

    @GetMapping
    public List<User> findAll()
    {

        return userService.findAll();
    }

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable("id") String id)
    {
        return userService.findByObjectId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User save(@RequestBody User user)
    {

        return userService.save(user);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") String id)
    {
        userService.deleteById(id);
    }

    @PutMapping("/addCourses/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void addCourses(@PathVariable String userId, @RequestParam("courses") Set<String> courses)
    {
        userService.addCourses(userId, courses);
    }
}
