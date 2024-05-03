/** */
package it.cambi.celum.controller;

import it.cambi.celum.mongo.model.Course;
import it.cambi.celum.service.CourseService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @author luca
 */
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

  private final CourseService userService;

  @GetMapping("/test")
  public String home() {
    return "Hello Course!";
  }

  @GetMapping
  public List<Course> findAll() {
    return userService.findAll();
  }

  @GetMapping(value = "/{id}")
  public Course findById(@PathVariable("id") String id) {
    return userService.findByObjectId(id);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Course save(@RequestBody Course course) {
    return userService.save(course);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void delete(@PathVariable("id") String id) {
    userService.deleteById(id);
  }
}
