package school.sptech.conmusicapi.modules.user.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.sptech.conmusicapi.modules.user.dtos.LoginDto;
import school.sptech.conmusicapi.modules.user.dtos.UserTokenDto;
import school.sptech.conmusicapi.modules.user.services.UserService;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Responsible for managing all requests and operations related to all users")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "Authenticate user", description = "Authenticates a user and returns a token")
    @PostMapping("/authentication")
    public ResponseEntity<UserTokenDto> authenticate(@RequestBody LoginDto dto) {
        UserTokenDto userTokenDto = userService.authenticate(dto);
        return ResponseEntity.status(200).body(userTokenDto);
    }
}
