package com.biblioteca.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.biblioteca.entities.UserEntity;
import com.biblioteca.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository usuarioRepository;

    public UserEntity saveUser(UserEntity usuarioEntity) {
        String passEncoded = usuarioEntity.getUserPassword();
        usuarioEntity.setUserPassword(passEncoded);
        return usuarioRepository.save(usuarioEntity);
    }

    public List<UserEntity> listAllUsers() {
        return usuarioRepository.findAll();
    }

    public UserEntity findUserById(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void deleteUser(Long idUsuario) {
        usuarioRepository.deleteById(idUsuario);
    }

    public UserEntity updateUser(Long idUsuario, UserEntity newUser) {
        UserEntity existingUser = findUserById(idUsuario);
        existingUser.setUserName(newUser.getUsername());
        existingUser.setUserCpf(newUser.getUserCpf());

        if (newUser.getUserPassword() != null && !newUser.getUserPassword().isEmpty()) {
            String passEncoded = newUser.getUserPassword();
            existingUser.setUserPassword(passEncoded);
        }

        existingUser.setUserEmail(newUser.getUserEmail());
        existingUser.setUserType(newUser.getUserType());
        return usuarioRepository.save(existingUser);
    }
}
