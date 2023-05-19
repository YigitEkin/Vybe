package com.vybe.backend.service;

import com.vybe.backend.exception.VenueNotFoundException;
import com.vybe.backend.model.dto.SongDTO;
import com.vybe.backend.model.entity.Song;
import com.vybe.backend.model.entity.SongRequest;
import com.vybe.backend.model.entity.Visit;
import com.vybe.backend.repository.SongRepository;
import com.vybe.backend.repository.SongRequestRepository;
import com.vybe.backend.repository.VenueRepository;
import com.vybe.backend.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class AnalyticsService {
    @Resource
    private VisitRepository visitRepository;

    @Resource
    private SongRequestRepository songRequestRepository;

    @Resource
    private VenueRepository venueRepository;

    @Resource
    private SongRepository songRepository;

    // count of checkin in a year, returns an array of 12 integers (Jan - Dec)
    private Integer[] getCheckinCountInAYear(Integer venueId) {
        List<Visit> visits = visitRepository.findAllByVenueId(venueId);

        Integer[] checkinCount = new Integer[12];
        Arrays.fill(checkinCount, 0);

        for(Visit visit : visits)
            checkinCount[visit.getVisitDate().getMonth()]++;

        return checkinCount;
    }

    // count of checkin in a month, returns an array of 31 integers (1 - 31)
    private Integer[] getCheckinCountInAMonth(Integer venueId) {
        List<Visit> visits = visitRepository.findAllByVenueId(venueId);

        Integer[] checkinCount = new Integer[31];
        Arrays.fill(checkinCount, 0);

        for(Visit visit : visits)
            checkinCount[visit.getVisitDate().getDate()-1]++;

        return checkinCount;
    }

    // count of checkin in a day, returns an array of 24 integers (0 - 23)
    private Integer[] getCheckinCountInADay(Integer venueId) {
        List<Visit> visits = visitRepository.findAllByVenueId(venueId);

        Integer[] checkinCount = new Integer[24];
        Arrays.fill(checkinCount, 0);

        for(Visit visit : visits)
            checkinCount[visit.getVisitDate().getHours()]++;

        return checkinCount;
    }

    // count of checkin in 4 hours, returns an array of 6 integers (0 - 5)
    private Integer[] getCheckinCountIn4Hours(Integer venueId) {
        List<Visit> visits = visitRepository.findAllByVenueId(venueId);

        Integer[] checkinCount = new Integer[6];
        Arrays.fill(checkinCount, 0);

        for(Visit visit : visits)
            checkinCount[visit.getVisitDate().getHours()/4]++;

        return checkinCount;
    }

    public Integer[] getCheckIns(Integer venueId, String inAYear, String inAMonth, String inADay, String in4Hours) {
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        if (inAYear != null) {
            return getCheckinCountInAYear(venueId);
        } else if (inAMonth != null) {
            return getCheckinCountInAMonth(venueId);
        } else if (inADay != null) {
            return getCheckinCountInADay(venueId);
        } else if (in4Hours != null) {
            return getCheckinCountIn4Hours(venueId);
        } else {
            return getCheckinCountInAMonth(venueId);
        }

    }

    // top 10 requested songs per venue
    public List<SongDTO> getTop10RequestedSongs(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<Integer> songIds = songRequestRepository.findTop10RequestsByVenueId(venueId);
        List<Song> songs = songRepository.findAllById(songIds);

        // enforce the order of the songs to be the same in songIds
        songs.sort((s1, s2) -> {
            int index1 = songIds.indexOf(s1.getId());
            int index2 = songIds.indexOf(s2.getId());
            return index1 - index2;
        });

        return songs.stream().map(SongDTO::new).toList();
    }

    // recently requested 20 songs per venue
    public List<SongDTO> getRecentlyRequestedSongs(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findTop20RecentRequestsByVenueId(venueId);
        List<Song> songs = songRepository.findAllById(songRequests.stream().map(SongRequest::getSongId).toList());

        // enforce the order of the songs to be the same in songIds
        songs.sort((s1, s2) -> {
            int index1 = songRequests.stream().map(SongRequest::getSongId).toList().indexOf(s1.getId());
            int index2 = songRequests.stream().map(SongRequest::getSongId).toList().indexOf(s2.getId());
            return index1 - index2;
        });

        return songs.stream().map(SongDTO::new).toList();
    }



    // songs requested per artist per venue returns a list of artist names and the number of songs requested by that artist
    // might take a lot of time
    public List<String> getSongsRequestedPerArtist(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        if(songRequests.isEmpty())
            return null;

        // a song can have more than one artist seperated by a space
        List<String> artists = new ArrayList<>();
        for(SongRequest songRequest : songRequests) {
            String[] songArtists = songRequest.getSong().getArtist().split(", ");
            artists.addAll(Arrays.asList(songArtists));
        }

        List<String> uniqueArtists = artists.stream().distinct().toList();

        return uniqueArtists.parallelStream().map(artist -> artist + ": " + artists.parallelStream().filter(a -> a.equals(artist)).count()).toList();
    }

    // number of songs requested in a month per venue
    private Integer[] getSongRequestCountInAMonth(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        Integer[] songRequestsPerDay = new Integer[31];
        Arrays.fill(songRequestsPerDay, 0);

        for(SongRequest songRequest : songRequests)
            songRequestsPerDay[songRequest.getRequestDate().getDate()-1]++;

        return songRequestsPerDay;
    }


    // number of songs requested in a day per venue
    private Integer[] getSongRequestCountInADay(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        Integer[] songRequestsPerDay = new Integer[24];
        Arrays.fill(songRequestsPerDay, 0);

        for(SongRequest songRequest : songRequests)
            songRequestsPerDay[songRequest.getRequestDate().getHours()]++;

        return songRequestsPerDay;
    }

    // number of songs requested in 4 hours per venue
    private Integer[] getSongRequestCountIn4Hours(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        Integer[] songRequestsPerDay = new Integer[6];
        Arrays.fill(songRequestsPerDay, 0);

        for(SongRequest songRequest : songRequests)
            songRequestsPerDay[songRequest.getRequestDate().getHours()/4]++;

        return songRequestsPerDay;
    }

    // number of songs requested in a year
    private Integer[] getSongRequestCountInAYear(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        Integer[] songRequestsPerDay = new Integer[12];
        Arrays.fill(songRequestsPerDay, 0);

        for(SongRequest songRequest : songRequests)
            songRequestsPerDay[songRequest.getRequestDate().getMonth()]++;

        return songRequestsPerDay;
    }

    public Integer[] getSongRequestCounts(Integer venueId, String inAYear, String inAMonth, String inADay, String in4Hours) {
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        if (inAYear != null) {
            return getSongRequestCountInAYear(venueId);
        } else if (inAMonth != null) {
            return getSongRequestCountInAMonth(venueId);
        } else if (inADay != null) {
            return getSongRequestCountInADay(venueId);
        } else if (in4Hours != null) {
            return getSongRequestCountIn4Hours(venueId);
        } else {
            return getSongRequestCountInAMonth(venueId);
        }
    }

    // total coins spent on song requests in a day per venue
    private Double[] getCoinsSpentOnSongRequestsInADay(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        Double[] coinsSpentPerDay = new Double[24];
        Arrays.fill(coinsSpentPerDay, 0.0);

        for(SongRequest songRequest : songRequests)
            coinsSpentPerDay[songRequest.getRequestDate().getHours()] += songRequest.getCoinCost();

        return coinsSpentPerDay;
    }

    // total coins spent on song requests in a month per venue
    private Double[] getCoinsSpentOnSongRequestsInAMonth(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        Double[] coinsSpentPerDay = new Double[31];
        Arrays.fill(coinsSpentPerDay, 0.0);

        for(SongRequest songRequest : songRequests)
            coinsSpentPerDay[songRequest.getRequestDate().getDate()-1] += songRequest.getCoinCost();

        return coinsSpentPerDay;
    }

    // total coins spent on song requests in 4 hours per venue
    private Double[] getCoinsSpentOnSongRequestsIn4Hours(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        Double[] coinsSpentPerDay = new Double[6];
        Arrays.fill(coinsSpentPerDay, 0.0);

        for(SongRequest songRequest : songRequests)
            coinsSpentPerDay[songRequest.getRequestDate().getHours()/4] += songRequest.getCoinCost();

        return coinsSpentPerDay;
    }

    // total coins spent on song requests in a year per venue
    private Double[] getCoinsSpentOnSongRequestsInAYear(Integer venueId) {
        if(venueId == null)
            return null;
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        List<SongRequest> songRequests = songRequestRepository.findAllByRequestedInVenueId(venueId);

        Double[] coinsSpentPerDay = new Double[12];
        Arrays.fill(coinsSpentPerDay, 0.0);

        for(SongRequest songRequest : songRequests)
            coinsSpentPerDay[songRequest.getRequestDate().getMonth()] += songRequest.getCoinCost();

        return coinsSpentPerDay;
    }

    public Double[] getCoinsSpentOnSongRequests(Integer venueId, String inAYear, String inAMonth, String inADay, String in4Hours) {
        if( venueRepository.findById(venueId).isEmpty() )
            throw new VenueNotFoundException("Venue with id " + venueId + " not found");

        if (inAYear != null) {
            return getCoinsSpentOnSongRequestsInAYear(venueId);
        } else if (inAMonth != null) {
            return getCoinsSpentOnSongRequestsInAMonth(venueId);
        } else if (inADay != null) {
            return getCoinsSpentOnSongRequestsInADay(venueId);
        } else if (in4Hours != null) {
            return getCoinsSpentOnSongRequestsIn4Hours(venueId);
        } else {
            return getCoinsSpentOnSongRequestsInAMonth(venueId);
        }
    }
}
