package io.ninjabet.football.controller;

import io.ninjabet.football.entity.Country;
import io.ninjabet.football.entity.dto.CountryDto;
import io.ninjabet.football.service.CountryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping("/api")
public class CountryController {

    private final CountryService countryService;

    private final ModelMapper modelMapper;

    @GetMapping(value = {"/countries/", "admin/countries/"})
    Iterable<CountryDto> findAll() {
        return StreamSupport.stream(this.countryService.findAll().spliterator(), false)
                .map(this::fromEntityToDto).collect(Collectors.toList());
    }

    @GetMapping(value = {"/countries/{id}", "admin/countries/{id}"})
    CountryDto findById(@PathVariable Long id) {
        return fromEntityToDto(this.countryService.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found")));
    }

    @PostMapping("/admin/countries/")
    Country add(@RequestBody CountryDto countryDto) {
        return this.countryService.add(fromDtoToEntity(countryDto));
    }

    @PutMapping("/admin/countries/{id}")
    Country update(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        return this.countryService.update(id, fromDtoToEntity(countryDto))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
    }

    @DeleteMapping("/admin/countries/{id}")
    void delete(@PathVariable Long id) {
        if (!this.countryService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found");
        }
    }

    private CountryDto fromEntityToDto(Country country) {
        return modelMapper.map(country, CountryDto.class);
    }

    private Country fromDtoToEntity(CountryDto countryDto) {
        return modelMapper.map(countryDto, Country.class);
    }
}
