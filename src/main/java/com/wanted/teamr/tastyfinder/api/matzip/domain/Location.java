package com.wanted.teamr.tastyfinder.api.matzip.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Column(name = "REFINE_WGS84_LAT", length = 20, nullable = false)
    String lat;

    @Column(name = "REFINE_WGS84_LOGT", length = 20, nullable = false)
    String lon;

    @Builder
    private Location(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public static Location of(String lat, String lon) {
        return Location.builder()
                       .lat(lat)
                       .lon(lon)
                       .build();
    }

    public double distanceFrom(Location target) {
        double lat1 = Double.parseDouble(lat);
        double lon1 = Double.parseDouble(lon);
        double lat2 = Double.parseDouble(target.lat);
        double lon2 = Double.parseDouble(target.lon);

        double R = 6371; // km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}

