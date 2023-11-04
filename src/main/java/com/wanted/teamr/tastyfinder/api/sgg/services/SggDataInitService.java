package com.wanted.teamr.tastyfinder.api.sgg.services;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class SggDataInitService {

    private final SggDataLoadService sggDataLoadService;

    @Value("${sggCsv.csvSkipFirstLine}")
    private boolean csvSkipFirstLine;

    @Value("${sggCsv.resourcePath}")
    private String sggLocation;

    /**
     * 서버가 시작될 때 CSV 파일을 읽고 Redis에 적재하는 서비스 호출.
     * 만약 그 과정에서 IOException이 발생하면 서버 시작을 실패하게 하도록 IOException 을 던진다.
     *
     * @throws IOException
     */
    @PostConstruct
    public void loadSggDataOnStartup() throws IOException {
        sggDataLoadService.replaceSggDataOnRedis(sggLocation, csvSkipFirstLine);
    }

}
