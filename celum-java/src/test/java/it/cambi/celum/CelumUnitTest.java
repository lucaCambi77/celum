package it.cambi.celum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import it.cambi.celum.mongo.model.User;
import it.cambi.celum.mongo.repository.UserRepository;
import it.cambi.celum.service.UserService;
import java.util.Arrays;
import java.util.List;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CelumUnitTest {

  @InjectMocks private UserService userService;

  @Mock private UserRepository userRepository;

  @ParameterizedTest
  @CsvSource({"58d1c36efb0cac4e15afd278,lucacambi@yahoo.it"})
  public void should_find_users(String id, String email) {
    when(userRepository.findAll())
        .thenReturn(Arrays.asList(User.builder().id(new ObjectId(id)).email(email).build()));

    List<User> users = userService.findAll();

    verify(userRepository, times(1)).findAll();
    assertEquals(1, users.size());
    assertEquals(id, users.get(0).getId());
  }
}
