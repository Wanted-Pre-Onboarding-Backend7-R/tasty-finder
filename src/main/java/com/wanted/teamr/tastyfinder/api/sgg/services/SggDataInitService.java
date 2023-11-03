package com.wanted.teamr.tastyfinder.api.sgg.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SggDataInitService {

    private final SggDataLoadService sggDataLoadService;

    @PostConstruct
    public void loadSggDataOnStartup() throws IOException {
        sggDataLoadService.loadDataFromCSV();
    }

}
