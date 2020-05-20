package de.com.alns.codingtest.hubject.chargingstationdata.services;

import org.springframework.stereotype.Service;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.Geometry;

import java.util.Optional;

@Service
public interface ILocationService {

    public boolean exists(String userId, Long id);

    public Long saveLocation(String userId, Feature feature);

    public void updateLocation(String userId, Long id, Feature feature);

    public void deleteLocation(Long id);

    public Optional<Feature> findLocationById(String userId, Long id);

    public FeatureCollection findAllLocations(String userId);

    public FeatureCollection findAllLocationsWithin(String userId, Geometry geoJson);

}
