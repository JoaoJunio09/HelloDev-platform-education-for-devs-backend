package br.com.joaojuniodev.blog.services;

import br.com.joaojuniodev.blog.exceptions.NotFoundException;
import br.com.joaojuniodev.blog.model.User;
import br.com.joaojuniodev.blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.findByUsername(username);
        if (user != null) return user;
        else throw new UsernameNotFoundException("Username: " + username + " not found");
    }

    public User findById(Long id) throws UserPrincipalNotFoundException {
        var user = repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Not found this ID : " + id));
        if (user == null) throw new UserPrincipalNotFoundException("User not found");
        return user;
    }
}
