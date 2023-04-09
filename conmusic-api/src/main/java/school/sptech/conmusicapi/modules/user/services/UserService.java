package school.sptech.conmusicapi.modules.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.conmusicapi.config.security.jwt.JwtTokenManager;
import school.sptech.conmusicapi.modules.user.dtos.LoginDto;
import school.sptech.conmusicapi.modules.user.dtos.UserTokenDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.mapper.UserMapper;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;

@Service
public class UserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private AuthenticationManager authenticationManager;

    public UserTokenDto authenticate(LoginDto dto) {
        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                dto.getEmail(),
                dto.getPassword()
        );
        final Authentication authentication = authenticationManager.authenticate(credentials);

        User authenticatedUser = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(
                        () -> new ResponseStatusException(404, "Email and/or Password are incorrect", null)
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = jwtTokenManager.generateToken(authentication);
        return UserMapper.toDto(authenticatedUser, token);
    }
}
