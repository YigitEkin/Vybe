package com.vybe.backend.service;

import com.vybe.backend.model.entity.Visit;
import com.vybe.backend.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class VisitService {
    @Resource
    private VisitRepository visitRepository;

    // get all visits by venueId
    public List<Visit> getAllVisitsByVenueId(Integer venueId) {
        return visitRepository.findAllByVenueId(venueId);
    }

    // get all visits by customerUsername
    public List<Visit> getAllVisitsByCustomerUsername(String customerUsername) {
        if(customerUsername == null) {
            return null;
        }
        return visitRepository.findAllByCustomerUsername(customerUsername);
    }

    // get all visits by customerUsername and venueId
    public List<Visit> getAllVisitsByCustomerUsernameAndVenueId(String customerUsername, Integer venueId) {
        if(customerUsername == null || venueId == null) {
            return null;
        }
        return visitRepository.findAllByCustomerUsernameAndVenueId(customerUsername, venueId);
    }

    // add a visit
    public Visit addVisit(Visit visit) {
        if(visit == null) {
            return null;
        }
        visit.setVisitDate(new java.util.Date());
        return visitRepository.save(visit);
    }

    // get visits by venueId and today's date
    public List<Visit> getVisitsByVenueIdAndToday(Integer venueId) {
        if(venueId == null) {
            return null;
        }
        List<Visit> visits = visitRepository.findAllByVenueId(venueId);
        if(visits == null) {
            return null;
        }

        java.util.Date today = new java.util.Date();
        java.util.Date visitDate;
        for(int i = 0; i < visits.size(); i++) {
            visitDate = visits.get(i).getVisitDate();
            if(visitDate == null) {
                return null;
            }
            if(visitDate.getYear() != today.getYear() || visitDate.getMonth() != today.getMonth() || visitDate.getDate() != today.getDate()) {
                visits.remove(i);
                i--;
            }
        }
        return visits;
    }
}
