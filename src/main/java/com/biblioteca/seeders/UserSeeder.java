package com.biblioteca.seeders;

import com.biblioteca.entities.UserEntity;
import com.biblioteca.entities.UserType;
import com.biblioteca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class UserSeeder {

    @Autowired
    private UserRepository userRepository;
    private final BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();

    public void seedUsers() {
        System.out.println("Populando tabela de usuários...");

        saveUserIfNotExists("Admin", "98765432100", "Admin", "ADMIN", "admin@library.com");
        saveUserIfNotExists("João Silva", "12345678901", "senha123", "EMPLOYEE", "joao_silva@library.com");
        saveUserIfNotExists("Maria Oliveira", "12341234123", "senha456", "USER", "maria_oliveria@library.com");
        saveUserIfNotExists("Carlos Souza", "11223344556", "senha789", "EMPLOYEE", "carlos_souza@library.com");
        saveUserIfNotExists("Ana Costa", "55667788999", "senha101", "USER", "ana_costa@library.com");
    }

    private void saveUserIfNotExists(String name, String cpf, String password, String type, String email) {
        if (!userRepository.existsByEmail(email)) {
            UserEntity user = new UserEntity();
            user.setUserName(name);
            user.setUserCpf(cpf);
            user.setUserPassword(passEncoder.encode(password));
            user.setUserType(UserType.valueOf(type));
            user.setUserEmail(email);
            userRepository.save(user);
        }
    }
}
