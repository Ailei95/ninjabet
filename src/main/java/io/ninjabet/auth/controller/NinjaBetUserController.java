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

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class NinjaBetUserController {

    private final NinjaBetUserDetailsService ninjaBetUserDetailsService;

    private final ModelMapper modelMapper;

    @GetMapping("/user")
    NinjaBetUserDto get() {
        return this.ninjaBetUserDetailsService.getCurrentUser().orElse(null);
    }

    // For registration only

    @PostMapping("/registration")
    NinjaBetUserDto add(@RequestBody RegistrationNinjaBetUserDto registrationNinjaBetUserDto) {
        return fromEntityToDto(this.ninjaBetUserDetailsService.add(fromDtoToEntity(registrationNinjaBetUserDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User already exists")));
    }

    @PostMapping("/registration/confirm")
    NinjaBetUserDto confirm(@RequestParam String token) {
        return fromEntityToDto(this.ninjaBetUserDetailsService.confirmNinjaBetUser(token)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No valid token found")));
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
