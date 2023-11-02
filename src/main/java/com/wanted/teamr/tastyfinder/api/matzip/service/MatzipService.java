package com.wanted.teamr.tastyfinder.api.matzip.service;

import com.wanted.teamr.tastyfinder.api.matzip.domain.Location;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipListRetrieveRequest;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipSummaryReponse;
import com.wanted.teamr.tastyfinder.api.matzip.repository.MatzipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatzipService {
    private final MatzipRepository matzipRepository;

    public List<MatzipSummaryReponse> retrieveMatzipList(MatzipListRetrieveRequest request) {
        Location requestLocation = Location.of(request.getLat(), request.getLon());
        double range = Double.parseDouble(request.getRange());
        return request.getType()
                      .retrieve(matzipRepository.findAll(), requestLocation, range);
    }
}

