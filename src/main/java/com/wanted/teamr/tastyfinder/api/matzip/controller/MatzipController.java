package com.wanted.teamr.tastyfinder.api.matzip.controller;

import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipListRetrieveRequest;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipResponse;
import com.wanted.teamr.tastyfinder.api.matzip.dto.MatzipSummaryResponse;
import com.wanted.teamr.tastyfinder.api.matzip.service.MatzipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MatzipController {
    private final MatzipService matzipService;
  
    @GetMapping("/api/matzips")
    public ResponseEntity<List<MatzipSummaryResponse>> retrieveMatzipList(@ModelAttribute @Valid MatzipListRetrieveRequest request) {
        List<MatzipSummaryResponse> response = matzipService.retrieveMatzipList(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/matzips/{matzipId}")
    public ResponseEntity<MatzipResponse> getMatzip(@PathVariable("matzipId") Long matzipId) {
        MatzipResponse matzipResponse = matzipService.getMatzip(matzipId);
        return ResponseEntity.ok(matzipResponse);
    }

}

