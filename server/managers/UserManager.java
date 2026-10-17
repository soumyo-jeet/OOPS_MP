package managers;

import models.User;
import repositories.UserRepository;

public class UserManager {
    private UserRepository userRepository;
    public UserManager (UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public void addNewUser (String username, String password) {
        User user = userRepository.findUserByUsername(username);
        if(user != null) {
            System.out.println(user);
            throw new RuntimeException("User with this username already exists.");
        }

        user = new User(username, password);
        int res = userRepository.registerUser(user);
        if(res == 1) System.out.println("User registered");
        else throw new RuntimeException("Diary registration failed");
    }

    public User authenticateUser (User user) {
        User existedUser = userRepository.findUserByUsername(user.getUsername());
        if(existedUser != null && existedUser.getPass().equals(user.getPass())) {
            System.out.println(existedUser);
            return existedUser;
        }
        throw new RuntimeException("Invalid credentials.");
    }
}
