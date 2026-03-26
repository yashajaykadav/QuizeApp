package org.yash.quize_app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yash.quize_app.dto.RegisterRequest;
import org.yash.quize_app.entity.Role;
import org.yash.quize_app.entity.User;
import org.yash.quize_app.repository.UserRepository;
import org.yash.quize_app.dto.LoginRequest;
import org.yash.quize_app.dto.AuthResponse;
import org.yash.quize_app.security.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder  passwordEncoder;
    private  JwtUtil jwtutil;

    public String register(RegisterRequest registerRequest){

        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
            throw new RuntimeException("Email Already Exists");
        }
        Role role;

        try{
            role = Role.valueOf(registerRequest.getRole().toUpperCase());
        }catch (Exception e){
            throw new RuntimeException("Invalid Role, Admin or Student");
        }

        User user = User.builder()
                .name(registerRequest.getName())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .build();
        userRepository.save(user);
        return "User registered Successful";

    }

    public AuthResponse login(LoginRequest loginRequest){

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new RuntimeException("User not Found"));

        if(!passwordEncoder.matches(loginRequest.getPassword(),user.getPassword())){
            throw  new RuntimeException("Invalid PassWord");
        }

        String token  = jwtutil.generateToken(user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
