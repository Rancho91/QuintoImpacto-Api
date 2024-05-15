package com.semillero.ecosistema.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.AddressComponentType;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class GeocodingUtil {
    private final GeoApiContext context;

    public GeocodingUtil(GeoApiContext context) {
        this.context = context;
    }


    public Map<String, String> getLocationFromCoordinates(Double lat, Double lng) throws IOException, InterruptedException, ApiException {
        GeocodingResult[] results = reverseGeocoding(lat, lng);
        Map<String, String> location = new HashMap<>();

        if (results.length > 0) {
            AddressComponent[] addressComponent = results[0].addressComponents;

            for (AddressComponent component : addressComponent) {
                for (AddressComponentType type : component.types) {
                    switch (type) {
                        case COUNTRY:
                            location.put("country", component.longName);
                            break;
                        case ADMINISTRATIVE_AREA_LEVEL_1:
                            location.put("province", component.longName);
                            break;
                        case LOCALITY:
                            location.put("city", component.longName);
                            break;
                    }
                }
            }
        }

        return location;
    }

    private GeocodingResult[] reverseGeocoding(Double lat, Double lng) throws IOException, InterruptedException, ApiException {
        LatLng latLng = new LatLng(lat, lng);
        return GeocodingApi.reverseGeocode(context, latLng).await();
    }

}
