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
            System.err.println("User already exists.");
            System.out.println(user);
            return;
        }

        user = new User(username, password);
        int res = userRepository.registerUser(user);
        if(res == 1) System.out.println("User registered");
        else System.err.println("Something went wrong");
    }

    public User authenticateUser (User user) {
        User existedUser = userRepository.findUserByUsername(user.getUsername());
        if(existedUser != null && existedUser.getPass().equals(user.getPass())) {
            System.out.println(existedUser);
            return existedUser;
        }
        else System.err.println("Invalid credentials");
        return null;
    }
}
