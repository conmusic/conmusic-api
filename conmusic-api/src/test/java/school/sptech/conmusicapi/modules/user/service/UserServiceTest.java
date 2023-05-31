package school.sptech.conmusicapi.modules.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import school.sptech.conmusicapi.config.security.jwt.JwtTokenManager;
import school.sptech.conmusicapi.modules.user.dtos.LoginDto;
import school.sptech.conmusicapi.modules.user.dtos.UserTokenDto;
import school.sptech.conmusicapi.modules.user.entities.User;
import school.sptech.conmusicapi.modules.user.repositories.IUserRepository;
import school.sptech.conmusicapi.modules.user.services.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private JwtTokenManager jwtTokenManager;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void authenticateShouldThrowExceptionForInvalidCredentials() {
        // given
        String email = "test@example.com";
        String password = "password";
        LoginDto dto = new LoginDto();
        dto.setEmail(email);
        dto.setPassword(password);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND, "Email and/or Password are incorrect"));

        // when/then
        assertThrows(ResponseStatusException.class, () -> userService.authenticate(dto));

        // verify
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByEmail(email);
        verify(jwtTokenManager, never()).generateToken(any(Authentication.class));
    }

}
