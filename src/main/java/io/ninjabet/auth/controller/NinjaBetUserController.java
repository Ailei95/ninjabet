package io.ninjabet.auth.controller;

import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.entity.dto.RegistrationNinjaBetUserDto;
import io.ninjabet.auth.entity.dto.NinjaBetUserDto;
import io.ninjabet.auth.service.NinjaBetUserDetailsService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class NinjaBetUserController {

    private final NinjaBetUserDetailsService ninjaBetUserDetailsService;

    private final ModelMapper modelMapper;

    @GetMapping("/user")
    NinjaBetUserDto get() {
        Optional<NinjaBetUserDto> local = this.ninjaBetUserDetailsService.getCurrentUser();

        return local.isPresent() ? local.get() : null;
    }

    // For registration only

    @PostMapping("/users")
    NinjaBetUserDto add(@RequestBody RegistrationNinjaBetUserDto registrationNinjaBetUserDto) {
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
