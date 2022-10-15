package com.site.rentyuzhne.service;

import com.site.rentyuzhne.model.User;
import com.site.rentyuzhne.repository.UserRepository;
import com.site.rentyuzhne.security.UserDatls;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        if (user.isEmpty())

            throw new UsernameNotFoundException("User not found");

        return new UserDatls(user.get());
    }
}
