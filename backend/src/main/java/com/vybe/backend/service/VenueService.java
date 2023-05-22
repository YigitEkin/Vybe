package com.vybe.backend.service;

import com.vybe.backend.exception.InputException;
import com.vybe.backend.model.dto.*;
import com.vybe.backend.exception.VenueNotFoundException;
import com.vybe.backend.model.entity.*;
import com.vybe.backend.repository.*;
import com.vybe.backend.util.SoundtrackUtil;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class VenueService {

    @Resource VenueRepository venueRepository;
    @Resource SongNodeRepository songNodeRepository;
    @Resource PlaylistRepository playlistRepository;
    @Resource CustomerRepository customerRepository;
    @Resource CommentRepository commentRepository;
    @Resource VisitRepository visitRepository;

    // add venue
    public VenueDTO addVenue(VenueCreationDTO venueCreationDTO) {
        Venue venue = venueCreationDTO.toVenue();
        // if location is not in format float, float throw exception using regex
        if(!venue.getLocation().matches("^-?\\d+(\\.\\d+)?, -?\\d+(\\.\\d+)?$")) {
            throw new InputException("Location must be in format float, float but found: " + venue.getLocation());
        }
        venue.setComments(Collections.emptySet());
        venue.setPhotos(Collections.emptySet());
        return new VenueDTO(venueRepository.save(venue));
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
        Venue venue = venueRepository.findById(id).get();
        VenueDTO venueDTO = new VenueDTO(venueRepository.findById(id).get());
        venueDTO.setName(venueCreationDTO.getName());
        venueDTO.setDescription(venueCreationDTO.getDescription());
        venueDTO.setLocation(venueCreationDTO.getLocation());

        Venue updatedVenue = venueDTO.toVenue();
        updatedVenue.setComments(venue.getComments());
        updatedVenue.setCurrentSong(venue.getCurrentSong());
        return new VenueDTO(venueRepository.save(updatedVenue));
    }

    // delete venue
    public void deleteVenue(int id) {
        if(!venueRepository.existsById(id)) {
            throw new VenueNotFoundException("Venue with id: " + id + " not found");
        }
        venueRepository.deleteById(id);
    }

    // play song from request list
    public SongDTO getNextSong(Integer venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        Venue venue = venueRepository.findById(venueId).get();
        Song song = venue.getPlaylist().playNextSong(songNodeRepository);

        // find the playlist and update the currentMode
        Playlist playlist = playlistRepository.findById(venue.getPlaylist().getId()).get();
        playlist.setCurrentMode(venue.getPlaylist().getCurrentMode());
        SongNode songNode = songNodeRepository.findBySong_IdAndPlaylistId(song.getId(), playlist.getId());
        int songNodeId = -1;
        if(songNode != null){
            songNodeId = songNode.getId();
            songNodeRepository.deleteById(songNodeId);
        }

        playlistRepository.save(playlist);

        venueRepository.save(venue);
        return new SongDTO(song);
    }

    public Song startSong(Integer venueId) {
        if (!venueRepository.existsById(venueId))
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");

        SongDTO nextSong = getNextSong(venueId);
        Venue venue = venueRepository.findById(venueId).get();
        String playlistId;

        if (venue.getPlaylist().getCurrentMode().equals("request"))
            playlistId = venue.getPlaylist().getRequestPlaylistId();
        else
            playlistId = venue.getPlaylist().getDefaultPlaylistId();

        String soundzoneId = venue.getSoundzoneId();
        String token = venue.getToken();
        String name = nextSong.getName();
        int index = SoundtrackUtil.findIndexOfSongInPlaylist(playlistId, name, token).get("index");
        System.out.println("playing song: " + name + " in index: " + index);
        SoundtrackUtil.playSong(playlistId, index, Collections.singletonList(soundzoneId), token);
        venue.setCurrentSong(nextSong.toSong());
        venueRepository.save(venue);
        return nextSong.toSong();
    }

    public SongDTO getCurrentSong(Integer venueId) {
        if (!venueRepository.existsById(venueId))
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        Venue venue = venueRepository.findById(venueId).get();
        if (venue.getCurrentSong() == null)
            return new SongDTO();
        return new SongDTO(venue.getCurrentSong());
    }

    public void startSongScheduled(Integer venueId) {
        if (!venueRepository.existsById(venueId))
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        SongDTO nextSong = getNextSong(venueId);
        Venue venue = venueRepository.findById(venueId).get();
        String playlistId;
        if (venue.getPlaylist().getCurrentMode().equals("request"))
            playlistId = venue.getPlaylist().getRequestPlaylistId();
        else
            playlistId = venue.getPlaylist().getDefaultPlaylistId();
        String soundzoneId = venue.getSoundzoneId();
        String token = venue.getToken();
        String name = nextSong.getName();
        HashMap<String, Integer> tmp = SoundtrackUtil.findIndexOfSongInPlaylist(playlistId, name, token);
        int index = tmp.get("index");
        int duration = tmp.get("duration");
        System.out.println("playing song: " + name + " in index: " + index);
        SoundtrackUtil.playSong(playlistId, index, Collections.singletonList(soundzoneId), token);
        venue.setCurrentSong(nextSong.toSong());
        venueRepository.save(venue);
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void deleteNull() {
        songNodeRepository.deleteAllByPlaylistIdIsNull();
    }

    // ************** Check in/out methods ************** //
    public List<CustomerDTO> getCheckedInCustomers(Integer venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        Venue venue = venueRepository.findById(venueId).get();
        return venue.getCheckedInCustomers().stream().map(CustomerDTO::new).collect(Collectors.toList());
    }

    public Boolean uploadVenuePhoto(ImageDTO dto, Integer venueId) {
        String data = dto.getImage();
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        Venue venue = venueRepository.findById(venueId).get();
        Set<Image> photos = venue.getPhotos();
        Image image = new Image();
        image.setData(data);
        // Remove next line to switch to multiple photos
        photos = new HashSet<>();
        photos.add(image);
        venue.setPhotos(photos);
        venueRepository.save(venue);
        return true;
    }

    public List<ImageDTO> getVenuePhotos(Integer venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        Venue venue = venueRepository.findById(venueId).get();
        List<Image> images = new ArrayList<>(venue.getPhotos());
        return images.stream().map(ImageDTO::new).collect(Collectors.toList());
    }

    public boolean deleteVenuePhoto(Integer venueId, Long imageId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        Venue venue = venueRepository.findById(venueId).get();
        Set<Image> photos = venue.getPhotos();
        photos.remove(photos.stream().filter(image -> Objects.equals(image.getId(), imageId)).findFirst().get());
        venue.setPhotos(photos);
        venueRepository.save(venue);
        return true;
    }

    
    public void createDummyVisits(int count, boolean thisMonth) {
        String[] usernames = {"905076011168", "2", "905309510454", "905332346981", "905387866001"};
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            Visit visit = new Visit();
            visit.setCustomerUsername(usernames[random.nextInt(usernames.length)]);
            visit.setVenueId(2);
            visit.setVenueName("Fameo Cafe");
            if (thisMonth)
                visit.setVisitDate(getRandomDateInThisMonth());
            else
                visit.setVisitDate(getRandomDateInThisYear());
            visitRepository.save(visit);
        }
    }

    private Date getRandomDateInThisMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        long startOfMonth = calendar.getTimeInMillis();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        long endOfMonth = calendar.getTimeInMillis();
        long randomTimeInMillis = ThreadLocalRandom.current().nextLong(startOfMonth, endOfMonth);
        return new Date(randomTimeInMillis);
    }

    private Date getRandomDateInThisYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        long startOfYear = calendar.getTimeInMillis();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
        long endOfYear = calendar.getTimeInMillis();
        long randomTimeInMillis = ThreadLocalRandom.current().nextLong(startOfYear, endOfYear);
        return new Date(randomTimeInMillis);
    }

}
