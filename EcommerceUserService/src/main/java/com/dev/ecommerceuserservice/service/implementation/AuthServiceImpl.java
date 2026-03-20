package com.dev.ecommerceuserservice.service.implementation;

import com.dev.ecommerceuserservice.dto.LoginRequestDTO;
import com.dev.ecommerceuserservice.dto.SignupRequestDto;
import com.dev.ecommerceuserservice.dto.UserResponseDto;
import com.dev.ecommerceuserservice.dto.ValidateTokenResponseDto;
import com.dev.ecommerceuserservice.enums.TokenStatus;
import com.dev.ecommerceuserservice.exception.UserServiceException;
import com.dev.ecommerceuserservice.models.Role;
import com.dev.ecommerceuserservice.models.Token;
import com.dev.ecommerceuserservice.models.User;
import com.dev.ecommerceuserservice.repository.RoleRepository;
import com.dev.ecommerceuserservice.repository.TokenRepository;
import com.dev.ecommerceuserservice.repository.UserRepository;
import com.dev.ecommerceuserservice.service.AuthService;
import com.dev.ecommerceuserservice.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.dev.ecommerceuserservice.mapper.UserMapper.toUserResponseDto;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TokenRepository tokenRepository;
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TokenRepository tokenRepository, JwtUtil jwtUtil, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenRepository = tokenRepository;
        this.jwtUtil = jwtUtil;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public HashMap<String, Object> login(LoginRequestDTO loginRequestDTO) {
        User loginUser = userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(
                () -> new UserServiceException("Incorrect EmailId Or Password", HttpStatus.BAD_REQUEST)
        );

        if (!bCryptPasswordEncoder.matches(loginRequestDTO.getPassword(), loginUser.getPassword())) {
            throw new UserServiceException("Invalid Credentials", HttpStatus.BAD_REQUEST);

        }
        tokenRepository.findByUserAndTokenStatus(loginUser, TokenStatus.ACTIVE)
                .ifPresent(session -> {
                    session.setTokenStatus(TokenStatus.EXPIRED);
                    tokenRepository.save(session);
                });

        String jwtToken = jwtUtil.getToken(loginUser);

        Token token = new Token();
        token.setToken(jwtToken);
        token.setUser(loginUser);
        token.setTokenStatus(TokenStatus.ACTIVE);
        tokenRepository.save(token);

        UserResponseDto responseDto = toUserResponseDto(loginUser);
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("header", jwtToken);
        responseMap.put("response", responseDto);
        return responseMap;
    }

    @Override
    public void logout(String jwtToken) {
        jwtToken = jwtToken.substring(7);
        Token token1 = tokenRepository.findByToken(jwtToken).orElseThrow(
                () -> new UserServiceException("Invalid Token", HttpStatus.BAD_REQUEST)
        );
        token1.setTokenStatus(TokenStatus.EXPIRED);
        tokenRepository.save(token1);
    }

    @Override
    public UserResponseDto signUp(SignupRequestDto signupRequestDto) {
        Optional<User> signUpUser = userRepository.findByEmail(signupRequestDto.getEmail());
        if (signUpUser.isPresent()) {
            throw new UserServiceException("Email Already Exist", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signupRequestDto.getName());
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequestDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findByName("USER").orElseThrow(
                () -> new UserServiceException("Role Doesn't Exist", HttpStatus.BAD_REQUEST)
        );
        roles.add(role);
        user.setRoles(roles);
        return toUserResponseDto(userRepository.save(user));
    }

    @Override
    public ValidateTokenResponseDto validate(String jwtToken) {

        jwtToken = jwtToken.substring(7);
        Claims claims = jwtUtil.parseToken(jwtToken);
        ValidateTokenResponseDto validateTokenResponseDto = new ValidateTokenResponseDto();
        Token token = tokenRepository.findByToken(jwtToken).orElseThrow(
                () -> new UserServiceException("Invalid Token", HttpStatus.BAD_REQUEST)
        );
        validateTokenResponseDto.setTokenStatus(String.valueOf(token.getTokenStatus()));
        validateTokenResponseDto.setId(token.getUser().getId());
        validateTokenResponseDto.setEmail((String) claims.get("email"));
        validateTokenResponseDto.setRoles((List<String>) claims.get("roles"));

        return validateTokenResponseDto;

    }
}
