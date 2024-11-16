package com.biblioteca.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity saveUser(UserEntity userEntity) {
        String passEncoded = userEntity.getUserPassword();
        userEntity.setUserPassword(passEncoded);
        return userRepository.save(userEntity);
    }

    public List<UserEntity> listAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserEntity updateUser(Long userId, UserEntity newUser) {
        UserEntity existingUser = findUserById(userId);
        existingUser.setUserName(newUser.getUsername());
        existingUser.setUserCpf(newUser.getUserCpf());

        if (newUser.getUserPassword() != null && !newUser.getUserPassword().isEmpty()) {
            String passEncoded = newUser.getUserPassword();
            existingUser.setUserPassword(passEncoded);
        }

        existingUser.setUserEmail(newUser.getUserEmail());
        existingUser.setUserType(newUser.getUserType());
        return userRepository.save(existingUser);
    }
}
