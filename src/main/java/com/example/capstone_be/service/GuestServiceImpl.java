package com.example.capstone_be.service;

import com.example.capstone_be.dto.guest.GuestDto;
import com.example.capstone_be.dto.image.ImageDto;
import com.example.capstone_be.exception.NotFoundException;
import com.example.capstone_be.model.Guest;
import com.example.capstone_be.model.ImageDetail;
import com.example.capstone_be.repository.GuestRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GuestServiceImpl implements GuestService {

    private final ModelMapper mapper;
    private final GuestRepository guestRepository;

    public GuestServiceImpl(ModelMapper mapper, GuestRepository guestRepository) {
        this.mapper = mapper;
        this.guestRepository = guestRepository;
    }

    @Override
    @Transactional
    public List<GuestDto> createGuests(List<GuestDto> guestDtos) {
        List<Guest> guests = new ArrayList<>();
        for (GuestDto guestDto: guestDtos) {
//            guests.add(mapper.map(guestDto,Guest.class));
            guestRepository.saveGuest(guestDto.getGuestType(),guestDto.getQuantity(),
                    guestDto.getTimeId(),guestDto.getUserId());
        }
//        for (Guest guest: guests) {
//            guestRepository.saveGuest(guest.getGuestType(),guest.getQuantity(),guest.getTimeId(),guest.getUserId());
//        }
//        guestRepository.saveAll(guests);
        return guestDtos;
    }

    @Override
    @Transactional
    public GuestDto createGuest(GuestDto guestDto) {
        guestRepository.save(mapper.map(guestDto,Guest.class));
        return guestDto;
    }

    @Override
    public void deleteGuestById(UUID id) {
        Guest guest = guestRepository.findById(id).orElseThrow(() -> new NotFoundException("Guest not found"));
        guestRepository.deleteById(id);
    }

    @Override
    public GuestDto updateGuest(GuestDto guestDto, UUID id) {
        final Guest updatedGuest = guestRepository.findById(id)
                .map(guest -> {
                    guest.setGuestType(guestDto.getGuestType());
                    guest.setQuantity(guestDto.getQuantity());
                    return guestRepository.save(guest);
                })
                .orElseGet(() -> {
                    guestDto.setGuestId(id);
                    return guestRepository.save(mapper.map(guestDto, Guest.class));
                });

        return mapper.map(updatedGuest, GuestDto.class);
    }

    @Override
    public List<GuestDto> getAllGuest() {
        List<Guest> guests = guestRepository.findAll();
        List<GuestDto> guestDtos = new ArrayList<>();
        for (Guest guest: guests) {
            guestDtos.add(mapper.map(guest,GuestDto.class));
        }
        return guestDtos;
    }
}
