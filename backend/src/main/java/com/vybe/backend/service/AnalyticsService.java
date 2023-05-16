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
    public Integer[] getCheckinCountInAYear(Integer venueId) {
        List<Visit> visits = visitRepository.findAllByVenueId(venueId);

        Integer[] checkinCount = new Integer[12];
        Arrays.fill(checkinCount, 0);

        for(Visit visit : visits)
            checkinCount[visit.getVisitDate().getMonth()]++;

        return checkinCount;
    }

    // count of checkin in a month, returns an array of 31 integers (1 - 31)
    public Integer[] getCheckinCountInAMonth(Integer venueId) {
        List<Visit> visits = visitRepository.findAllByVenueId(venueId);

        Integer[] checkinCount = new Integer[31];
        Arrays.fill(checkinCount, 0);

        for(Visit visit : visits)
            checkinCount[visit.getVisitDate().getDate()-1]++;

        return checkinCount;
    }

    // count of checkin in a day, returns an array of 24 integers (0 - 23)
    public Integer[] getCheckinCountInADay(Integer venueId) {
        List<Visit> visits = visitRepository.findAllByVenueId(venueId);

        Integer[] checkinCount = new Integer[24];
        Arrays.fill(checkinCount, 0);

        for(Visit visit : visits)
            checkinCount[visit.getVisitDate().getHours()]++;

        return checkinCount;
    }

    // count of checkin in 4 hours, returns an array of 6 integers (0 - 5)
    public Integer[] getCheckinCountIn4Hours(Integer venueId) {
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



    // songs requested per artist per venue

    // number of songs requested per day per venue

    // number of songs requested per week per venue

    // number of songs requested per month per venue

    // total checkins per day per venue

    // total checkins per week per venue

    // total checkins per month per venue

    // total coins spent on song requests per day per venue

    // total coins spent on song requests per week per venue

    // total coins spent on song requests per month per venue
}
