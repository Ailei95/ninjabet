package io.ninjabet.auth.controller;

import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.entity.dto.RegistrationNinjaBetUserDto;
import io.ninjabet.auth.entity.dto.NinjaBetUserDto;
import io.ninjabet.auth.service.NinjaBetUserDetailsService;
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
public class NinjaBetUserController {

    private final NinjaBetUserDetailsService ninjaBetUserDetailsService;

    private final ModelMapper modelMapper;

    // For registration only

    @PostMapping("/users")
    NinjaBetUserDto addUser(@RequestBody RegistrationNinjaBetUserDto registrationNinjaBetUserDto) {
        return fromEntityToDto(this.ninjaBetUserDetailsService.add(fromDtoToEntity(registrationNinjaBetUserDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User already exists")));
    }

    private NinjaBetUserDto fromEntityToDto(NinjaBetUser ninjaBetUser) {
        return modelMapper.map(ninjaBetUser, NinjaBetUserDto.class);
    }

    private NinjaBetUser fromDtoToEntity(NinjaBetUserDto ninjaBetUserDto) {
        return modelMapper.map(ninjaBetUserDto, NinjaBetUser.class);
    }

    private NinjaBetUser fromDtoToEntity(RegistrationNinjaBetUserDto registrationNinjaBetUserDto) {
        return modelMapper.map(registrationNinjaBetUserDto, NinjaBetUser.class);
    }
}
