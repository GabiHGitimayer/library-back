package com.biblioteca.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "userName")
    @NotBlank
    private String userName;

    @Column(name = "userEmail")
    @NotBlank
    private String userEmail;

    @Column(name = "userCpf", unique = true)
    @NotBlank
    private String userCpf;

    @Column(name = "userPassword")
    @NotBlank
    @JsonIgnore
    private String userPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "userType", nullable = false)
    @NotNull
    private UserType userType;

    public UserEntity(String userName, String userEmail, String userCpf, UserType userType, String userPassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userCpf = userCpf;
        this.userType = userType;
        this.userPassword = userPassword;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userType == UserType.ADMIN || this.userType == UserType.EMPLOYEE)
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_EMPLOYEE"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        else
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userPassword;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return userCpf;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
