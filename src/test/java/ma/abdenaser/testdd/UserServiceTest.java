
import ma.abdenaser.testdd.TestDdApplication;
import ma.abdenaser.testdd.User;
import ma.abdenaser.testdd.UserRepository;
import ma.abdenaser.testdd.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
/*
* dans cette exemple on va utiliser @MockBean pour simuler le comportement du UserRepository
* et @Autowired pour injecter le UserService dans les tests.
* Ces annotations permettent de tester le service UserService sans dépendre d'une base de données réelle.*/
@RunWith(SpringRunner.class)
/*
* Cette annotation indique à JUnit d'utiliser le SpringRunner comme moteur d'exécution de tests.
* Le SpringRunner permet d'intégrer Spring Framework dans les tests unitaires JUnit,
* ce qui vous permet d'utiliser des fonctionnalités Spring, telles que l'injection de dépendances.*/
@SpringBootTest(classes = TestDdApplication.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    /*
    * Cette annotation est utilisée pour injecter une dépendance dans la classe.
    * Dans ce cas, elle est utilisée pour injecter un bean UserService dans la classe de test.*/
    @MockBean
    private UserRepository userRepository;
    /*
    * Cette annotation est fournie par Spring Boot et est utilisée pour créer un mock (une version simulée) d'un bean Spring.
    *  Dans ce cas,elle est utilisée pour créer un mock du UserRepository afin de simuler
    * les interactions avec la base de données.*/
    @Test
    /*
    * Cette annotation indique qu'une méthode est un cas de test JUnit.
    * Les méthodes annotées avec @Test seront exécutées lors de l'exécution des tests unitaires.*/
    public void testCreateUser() {
        User newUser = new User();
        newUser.setUsername("john.doe");
        newUser.setEmail("john.doe@example.com");

        when(userRepository.save(any(User.class))).thenReturn(newUser);

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser);
        assertTrue(createdUser.getEmail()!=null);
        assertEquals("john.doe", createdUser.getUsername());
    }
    @Test
    public void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setId(2L);
        updatedUser.setUsername("updated.username");
        updatedUser.setEmail("test@gmail.com");

        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(updatedUser);

        assertNotNull(result);
        assertEquals("updated.username", result.getUsername());
        System.out.println("result = " + result);
    }
    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("alice.smith");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        /**Cette ligne configure un comportement simulé à l'aide de Mockito
         *  Elle indique que lorsque userRepository.findById(userId) est appelé avec un userId spécifique
         *  (dans ce cas, 1L), il devrait renvoyer un Optional contenant l'objet utilisateur.
         *  Cela simule une requête réussie à la base de données où un utilisateur avec l'ID donné est trouvé.*/
        User retrievedUser = userService.getUserById(userId);
        /*
        * Cette ligne appelle la méthode getUserById de votre userService,
        * qui, à son tour, invoque userRepository.findById(userId)
        * pour récupérer un utilisateur par son ID.*/
        assertNotNull(retrievedUser);
        /*
        * Cette assertion s'assure que retrievedUser n'est pas nul. Si userRepository.findById(userId)
        * avait renvoyé null, cette assertion aurait échoué.*/
        assertEquals("alice.smith", retrievedUser.getUsername());
        /*
        * Cette assertion vérifie que retrievedUser a le nom d'utilisateur attendu, qui est "alice.smith"
        * en fonction de la configuration de votre test.*/
    }


}
