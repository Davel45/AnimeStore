package com.animestore.user;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    void init() {
        seedUser("John", "Lennon", "john@mail.com", "password", List.of(Role.USER));
        seedUser("Gojo", "Satoru", "admin@animestore.com", "admin", List.of(Role.ADMIN));
        seedUser("Naruto", "Uzumaki", "user", "user", List.of(Role.USER));
        seedUser("Gojo", "Satoru", "admin", "admin", List.of(Role.ADMIN));
        seedUser("Sung", "Jinwoo", "superadmin", "superadmin", List.of(Role.SUPERADMIN));
    }

    private void seedUser(String firstName, String lastName, String email, String rawPassword, List<Role> roles) {
        User user = repository.findByEmail(email).orElseGet(User::new);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setRoles(roles);
        repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return repository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + userEmail));
    }
}
