package dev.maniebra.awesomedraw.service;

import dev.maniebra.awesomedraw.dto.AuthRequest;
import dev.maniebra.awesomedraw.repository.UserRepository;
import dev.maniebra.awesomedraw.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import dev.maniebra.awesomedraw.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public String register(AuthRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(u -> { throw new RuntimeException("Username already taken"); });

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);

        return jwtUtils.generateToken(user.getUsername());
    }

}
