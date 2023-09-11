package ma.abdenaser.testdd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        // Logique pour créer un utilisateur
        return userRepository.save(user);
    }

    public User getUserById(Long id) {
        // Logique pour obtenir un utilisateur par ID
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(User user) {
        // Logique pour mettre à jour un utilisateur
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        // Logique pour supprimer un utilisateur
        userRepository.deleteById(id);
    }
}
