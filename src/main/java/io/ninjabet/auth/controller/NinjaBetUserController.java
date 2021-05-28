package io.ninjabet.auth.controller;

import io.ninjabet.auth.entity.NinjaBetConfirmRegistrationToken;
import io.ninjabet.auth.entity.NinjaBetUser;
import io.ninjabet.auth.entity.dto.NinjaBetConfirmRegistrationTokenDto;
import io.ninjabet.auth.entity.dto.RegistrationNinjaBetUserDto;
import io.ninjabet.auth.entity.dto.NinjaBetUserDto;
import io.ninjabet.auth.service.NinjaBetConfirmRegistrationTokenService;
import io.ninjabet.auth.service.NinjaBetUserDetailsService;
import io.ninjabet.auth.service.NinjaBetUserService;
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

    private final NinjaBetUserService ninjaBetUserService;

    private final NinjaBetUserDetailsService ninjaBetUserDetailsService;

    private final NinjaBetConfirmRegistrationTokenService ninjaBetConfirmRegistrationTokenService;

    private final ModelMapper modelMapper;

    @GetMapping("/user")
    NinjaBetUserDto get() {
        return this.ninjaBetUserDetailsService.getCurrentUserDto().orElse(null);
    }

    // TODO Delete

    @PostMapping("/user")
    NinjaBetUserDto setAdmin(@RequestParam boolean admin) {
        Optional<NinjaBetUser> ninjaBetUser = this.ninjaBetUserDetailsService.getCurrentUser();

        return fromEntityToDto(this.ninjaBetUserService.setAdmin(ninjaBetUser.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase())), admin));
    }

    // For registration only

    @GetMapping("/registration")
    NinjaBetUserDto confirm(@RequestParam String token) {
        return fromEntityToDto(this.ninjaBetUserService.confirmNinjaBetUser(token)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No valid token found")));
    }

    @PostMapping("/registration")
    NinjaBetConfirmRegistrationTokenDto add(@RequestBody RegistrationNinjaBetUserDto registrationNinjaBetUserDto) {
        return fromEntityToDto(this.ninjaBetUserService.add(fromDtoToEntity(registrationNinjaBetUserDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "User already exists")));
    }

    @GetMapping("/resend")
    void resend(@RequestParam String email) {
        Optional<NinjaBetUser> ninjaBetUser = ninjaBetUserService.findById(email);

        if (ninjaBetUser.isPresent()) {
            Optional<NinjaBetConfirmRegistrationToken> ninjaBetConfirmRegistrationToken
                    = this.ninjaBetConfirmRegistrationTokenService.getUnconfirmedValidRegistrationToken(ninjaBetUser.get());

            if (ninjaBetConfirmRegistrationToken.isPresent())
                this.ninjaBetConfirmRegistrationTokenService
                        .sendConfirmationEmail(ninjaBetUser.get().getEmail(), ninjaBetConfirmRegistrationToken.get().getToken());
            else
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Registration not found, maybe the registration request is expired or is already confirmed");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    private NinjaBetUserDto fromEntityToDto(NinjaBetUser ninjaBetUser) {
        return modelMapper.map(ninjaBetUser, NinjaBetUserDto.class);
    }

    private NinjaBetUser fromDtoToEntity(RegistrationNinjaBetUserDto registrationNinjaBetUserDto) {
        return modelMapper.map(registrationNinjaBetUserDto, NinjaBetUser.class);
    }

    private NinjaBetConfirmRegistrationTokenDto fromEntityToDto(NinjaBetConfirmRegistrationToken ninjaBetConfirmRegistrationToken) {
        return modelMapper.map(ninjaBetConfirmRegistrationToken, NinjaBetConfirmRegistrationTokenDto.class);
    }
}
