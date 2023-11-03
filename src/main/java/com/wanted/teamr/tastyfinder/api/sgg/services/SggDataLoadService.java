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

    public void loadDataFromCSV() throws IOException {

        String classPath = "data/sgg_lat_lon.csv";
        ClassPathResource classPathResource = new ClassPathResource(classPath);
        File file = classPathResource.getFile();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        List<SggData> sggDataList = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
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
        sggDataService.saveAll(sggDataList);
    }

}