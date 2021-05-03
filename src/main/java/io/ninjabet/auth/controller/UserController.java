package io.ninjabet.auth.controller;

import io.ninjabet.auth.entity.User;
import io.ninjabet.auth.entity.dto.RegistrationUserDto;
import io.ninjabet.auth.entity.dto.UserDto;
import io.ninjabet.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    /**
     * For user registration
     *
     * @param registrationUserDto
     * @return
     */

    @PostMapping("/users")
    UserDto addUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return fromEntityToDto(this.userService.addUser(fromDtoToEntity(registrationUserDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User already exists")));
    }

    private UserDto fromEntityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User fromDtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private User fromDtoToEntity(RegistrationUserDto registrationUserDto) {
        return modelMapper.map(registrationUserDto, User.class);
    }
}
