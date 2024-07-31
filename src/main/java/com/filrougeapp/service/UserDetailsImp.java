package com.filrougeapp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.filrougeapp.repository.UserRepository;

@Service
public class UserDetailsImp implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsImp(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Le username n'a pas été trouvé"));
    }
}
