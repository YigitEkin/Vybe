package com.vybe.backend.service;


import com.vybe.backend.DTO.VenueCreationDTO;
import com.vybe.backend.DTO.VenueDTO;
import com.vybe.backend.exception.VenueNotFoundException;
import com.vybe.backend.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenueService {

    VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    // add venue
    public VenueDTO addVenue(VenueCreationDTO venueCreationDTO) {
        return new VenueDTO(venueRepository.save(venueCreationDTO.toVenue()));
    }

    // get venue
    public VenueDTO getVenue(int id) {
        if(!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        return new VenueDTO(venueRepository.findById(id).get());
    }

    // get all venues
    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll().stream().map(VenueDTO::new).collect(Collectors.toList());
    }

    public VenueDTO getVenue(String name) {
        if(!venueRepository.existsByName(name)) {
            throw new VenueNotFoundException("Venue with name: " + name + " not found");
        }
        return new VenueDTO(venueRepository.findByName(name));
    }

    // update venue
    public VenueDTO updateVenue(int id, VenueCreationDTO venueCreationDTO) {
        if(!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        VenueDTO venueDTO = new VenueDTO(venueRepository.findById(id).get());
        venueDTO.setName(venueCreationDTO.getName());
        venueDTO.setDescription(venueCreationDTO.getDescription());
        venueDTO.setLocation(venueCreationDTO.getLocation());
        return new VenueDTO(venueRepository.save(venueDTO.toVenue()));
    }

    // delete venue
    public void deleteVenue(int id) {
        if(!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        venueRepository.deleteById(id);
    }

}
