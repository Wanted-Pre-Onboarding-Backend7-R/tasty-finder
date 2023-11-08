package com.wanted.teamr.tastyfinder.api.sgg.controller;

import com.wanted.teamr.tastyfinder.api.sgg.domain.SggData;
import com.wanted.teamr.tastyfinder.api.sgg.dto.GetSggDataResponse;
import com.wanted.teamr.tastyfinder.api.sgg.services.SggDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SggDataController {

    private final SggDataService sggDataService;

    @GetMapping("/api/sggdata")
    public ResponseEntity<List<GetSggDataResponse>> getSggDataList() {
        List<SggData> sggList = sggDataService.getSggDataList();
        List<GetSggDataResponse> response = sggList.stream().map(GetSggDataResponse::from).toList();
        return ResponseEntity.ok().body(response);
    }

}
