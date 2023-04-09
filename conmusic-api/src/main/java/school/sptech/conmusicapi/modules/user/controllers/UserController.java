package school.sptech.conmusicapi.modules.user.controllers;

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
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/authentication")
    public ResponseEntity<UserTokenDto> authenticate(@RequestBody LoginDto dto) {
        UserTokenDto userTokenDto = userService.authenticate(dto);
        return ResponseEntity.status(200).body(userTokenDto);
    }
}
