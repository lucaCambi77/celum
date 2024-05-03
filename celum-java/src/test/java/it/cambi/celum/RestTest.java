/** */
package it.cambi.celum;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author luca
 */
@SpringBootTest(classes = {Application.class, ApplicationConfiguration.class})
@AutoConfigureMockMvc
public class RestTest {

  @Autowired private MockMvc mockMvc;

  @Test
  public void testGreeting() {

  }
}
