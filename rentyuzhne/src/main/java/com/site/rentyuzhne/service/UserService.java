package com.site.rentyuzhne.service;

import com.site.rentyuzhne.model.User;
import com.site.rentyuzhne.model.enums.Role;
import com.site.rentyuzhne.repository.UserRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@Data
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private MailSender mailSender;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Transactional
    public boolean createUser(User user) {
        String userEmail = user.getEmail();
        if (userRepository.findByEmail(userEmail) != null) return false;
        user.getRoles().add(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivationCode(UUID.randomUUID().toString());
        userRepository.save(user);

            String message = String.format(
                    "Hello, %s! \n" +
                            "Перейдите по ссылке для окончания регистрации: http://localhost:8888/activate/%s",
                    user.getName(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        

        return true;
    }

    @Transactional
    public boolean createAdmin() {
        User user = new User();
        user.getRoles().add(Role.ROLE_ADMIN);
        user.setActive(true);
        user.setName("ADMIN");
        user.setNumberPhone("101");
        user.setPassword("1111");
        user.setEmail("test@test");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return true;
    }

    @Transactional(readOnly = true)
    public User findById(Long id){

        return userRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public List<User> list() {
        return userRepository.findAll();
    }

    @Transactional
    public void banUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.isActive()) {
                user.setActive(false);

            } else {
                user.setActive(true);

            }
        }
        userRepository.save(user);
    }
    @Transactional
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    @Transactional
    public boolean activateUser(String code) {
        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);
        user.setActive(true);
        userRepository.save(user);

        return true;
    }
}