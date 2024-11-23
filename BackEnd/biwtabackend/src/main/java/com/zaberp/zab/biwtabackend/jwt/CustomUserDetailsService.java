package com.zaberp.zab.biwtabackend.jwt;

import com.zaberp.zab.biwtabackend.model.Xusers;
import com.zaberp.zab.biwtabackend.repository.XusersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private final XusersRepository repository;

    public CustomUserDetailsService(XusersRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Xusers xusers = repository.findByZemail(username);

        if (xusers.getZemail().equals(username)) {
            return new User(xusers.getZemail(), new BCryptPasswordEncoder().encode(xusers.getXpassword()), Collections.emptyList());
        }
        throw new UsernameNotFoundException("User not found");
    }
}

