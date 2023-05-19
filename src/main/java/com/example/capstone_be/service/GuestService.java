package com.example.capstone_be.service;

import com.example.capstone_be.dto.guest.GuestDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.dto.image.ImageViewDto;

import java.util.List;
import java.util.UUID;

public interface GuestService {
    List<GuestDto> createGuests(List<GuestDto> guestDtos);

    GuestDto createGuest(GuestDto guestDto);

    void deleteGuestById(UUID id);

    GuestDto updateGuest(GuestDto guestDto, UUID id);

    List<GuestDto> getAllGuest();
}
