/** */
package it.cambi.celum.controller;

import it.cambi.celum.mongo.model.User;
import it.cambi.celum.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author luca
 */
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/user")
public class UserController {

  private @Autowired UserService userService;

  @GetMapping("/test")
  public String home() {
    return "Hello Users!";
  }

  @GetMapping
  public List<User> findAll() {
    return userService.findAll();
  }

  @GetMapping(value = "/{id}")
  public User findById(@PathVariable("id") String id) {
    return userService.findByObjectId(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public User save(@RequestBody User user) {
    return userService.save(user);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("id") String id) {
    userService.deleteById(id);
  }
}
