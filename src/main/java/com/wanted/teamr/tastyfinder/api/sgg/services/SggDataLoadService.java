package com.wanted.teamr.tastyfinder.api.sgg.services;

import com.wanted.teamr.tastyfinder.api.sgg.domain.SggData;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SggDataLoadService {

    private final SggDataService sggDataService;

    /**
     * csv 파일로부터 SggData 리스트를 작성하고 Redis의 SggData를 교체
     *
     * @throws IOException CSV 파일 발견하지 못하였거나 CSV 파일을 읽는데 문제가 있을 때 발생
     */
    public void replaceSggDataOnRedis(String resourcePath, boolean csvSkipFirstLine) throws IOException {
        File file = getCsvFile(resourcePath);
        List<SggData> sggDataList = getSggData(file, csvSkipFirstLine);
        sggDataService.replaceAll(sggDataList);
    }

    private File getCsvFile(String resourcePath) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource(resourcePath);
        return classPathResource.getFile();
    }

    private List<SggData> getSggData(File file, boolean csvSkipFirstLine) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<SggData> sggDataList = new ArrayList<>();
        boolean skipped = false;
        while ((line = reader.readLine()) != null) {
            if (!skipped && csvSkipFirstLine) {
                skipped = true;
                continue;
            }
            String[] parts = line.split(",");
            if (parts.length == 4) {
                String dosi = parts[0];
                String sgg = parts[1];
                String lon = parts[2];
                String lat = parts[3];

                sggDataList.add(SggData.from(dosi, sgg, lon, lat));
            }
        }
        reader.close();
        return sggDataList;
    }

}
