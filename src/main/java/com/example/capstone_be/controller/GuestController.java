package com.example.capstone_be.controller;


import com.example.capstone_be.dto.category.CategoryDto;
import com.example.capstone_be.dto.guest.GuestDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.service.GuestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.example.capstone_be.util.ValidUtils.getMessageBindingResult;

@RestController
@RequestMapping("/guest")
public class GuestController {
    private final GuestService guestService;

    public GuestController(GuestService guestService) {
        this.guestService = guestService;
    }
    @PostMapping("/create/")
    public ResponseEntity<List<GuestDto>> createGuestList(@RequestBody List<GuestDto> guestDtos) {
        guestService.createGuests(guestDtos);
        return new ResponseEntity<>(guestDtos, HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<GuestDto> createGuest(@RequestBody final GuestDto guestDto) {
        guestService.createGuest(guestDto);
        return new ResponseEntity<>(guestDto, HttpStatus.OK);
    }

    @PatchMapping("/update-guest/{id}")
    public ResponseEntity<?> updateGuest(@RequestBody @Valid GuestDto guestDto, @PathVariable UUID id, final BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            String msg = getMessageBindingResult(bindingResult);
            return new ResponseEntity<>(msg, HttpStatus.BAD_REQUEST);
        }
        GuestDto updatedGuestDto = guestService.updateGuest(guestDto, id);
        return new ResponseEntity(updatedGuestDto, HttpStatus.OK);
    }
    @DeleteMapping("/guest-delete/{id}")
    public ResponseEntity<ImageDto> deleteGuest(@PathVariable UUID id) {
        guestService.deleteGuestById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
