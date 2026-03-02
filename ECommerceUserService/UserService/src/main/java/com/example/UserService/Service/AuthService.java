package com.example.UserService.Service;

import com.example.UserService.Repository.RoleRepository;
import com.example.UserService.Repository.SessionRepository;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.dto.UserResponseDto;
import com.example.UserService.exception.UserServiceException;
import com.example.UserService.models.Role;
import com.example.UserService.models.Session;
import com.example.UserService.models.SessionStatus;
import com.example.UserService.models.User;
import com.example.UserService.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;

import static com.example.UserService.mapper.UserMapper.toUserDto;

@Service
public class AuthService {
    private final RoleRepository roleRepository;
    private UserRepository userRepository;
    private SessionRepository sessionRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, SessionRepository sessionRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil,
                       RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.roleRepository = roleRepository;
    }

    public UserResponseDto signUp(String name, String email, String password) {
        Role role = roleRepository.findByRoleName("USER").orElseThrow(
                () -> new UserServiceException("Role Doesnot Exist", HttpStatus.BAD_REQUEST)
        );

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        User savedUser = userRepository.save(user);

        return toUserDto(savedUser);
    }

    public HashMap<String, Object> login(String email, String password) throws Exception {

        User loginUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserServiceException("User Doesn't Exist With The Given Mail Id", HttpStatus.NOT_FOUND));

        if (!bCryptPasswordEncoder.matches(password, loginUser.getPassword())) {
            throw new UserServiceException("Invalid Credentials", HttpStatus.UNAUTHORIZED);
        }

        sessionRepository.findByUserAndSessionStatus(loginUser, SessionStatus.ACTIVE)
                .ifPresent(session -> {
                    session.setSessionStatus(SessionStatus.ENDED);
                    sessionRepository.save(session);
                });

        String token = jwtUtil.getToken(loginUser);

        Session newSession = new Session();
        newSession.setUser(loginUser);
        newSession.setLoginAt(LocalDateTime.now());
        newSession.setToken(token);
        newSession.setSessionStatus(SessionStatus.ACTIVE);

        sessionRepository.save(newSession);

        UserResponseDto userResponseDto = toUserDto(loginUser);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, token);

        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("response", userResponseDto);
        responseMap.put("headers", headers);

        return responseMap;
    }

    public void logout(String token) {
        Session session = sessionRepository.findByToken(token).orElseThrow(
                ()->new UserServiceException("Invalid Token", HttpStatus.BAD_REQUEST)
        );
        if(session.getSessionStatus()==SessionStatus.ENDED)
        {
            return;
        }
        session.setSessionStatus(SessionStatus.ENDED);
        sessionRepository.save(session);
    }

    public SessionStatus validate(String token) {
        Session session = sessionRepository.findByToken(token).orElseThrow(
                ()->new UserServiceException("Invalid Token", HttpStatus.BAD_REQUEST)
        );

        return session.getSessionStatus();
    }
}