package de.com.alns.codingtest.hubject.chargingstationdata.services.impl;

import de.com.alns.codingtest.hubject.chargingstationdata.domain.models.LocationEntity;
import de.com.alns.codingtest.hubject.chargingstationdata.repositories.LocationRepository;
import de.com.alns.codingtest.hubject.chargingstationdata.services.ILocationService;
import de.com.alns.codingtest.hubject.chargingstationdata.utils.GeometryUtils;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wololo.geojson.Feature;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.Geometry;

import java.lang.reflect.Field;
import java.util.*;

@Service
@CommonsLog
public class LocationServiceImpl implements ILocationService {

    private LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public boolean exists(String userId, Long id) {
        return locationRepository.existsLocationEntityByUserAndId(userId, id);
    }

    public Long saveLocation(String userId, Feature feature) {
        LocationEntity locationEntity = convertFeatureToEntity(feature);
        locationEntity.setUser(userId);
        locationRepository.save(locationEntity);
        return locationEntity.getId();
    }

    public void updateLocation(String userId, Long id, Feature feature) {
        LocationEntity locationEntity = convertFeatureToEntity(feature);
        locationEntity.setId(id);
        locationEntity.setUser(userId);
        locationRepository.save(locationEntity);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }

    public Optional<Feature> findLocationById(String userId, Long id) {
        Optional<LocationEntity> locationEntity = locationRepository.findByUserAndId(userId, id);
        return locationEntity.map(this::convertEntityToFeature);
    }

    public FeatureCollection findAllLocations(String userId) {
        List<LocationEntity> locationEntityList = locationRepository.findAllByUser(userId);
        Feature[] features = mapEntityListToFeatures(locationEntityList);
        return new FeatureCollection(features);
    }

    public FeatureCollection findAllLocationsWithin(String userId, Geometry geoJson) {
        com.vividsolutions.jts.geom.Geometry geometry = GeometryUtils.convertGeoJsonToJtsGeometry(geoJson);
        List<LocationEntity> locationEntityList = locationRepository.findWithin(userId, geometry);
        Feature[] features = mapEntityListToFeatures(locationEntityList);
        return new FeatureCollection(features);
    }

    private Feature[] mapEntityListToFeatures(List<LocationEntity> locationEntityList) {
        return locationEntityList.stream()
                .map(this::convertEntityToFeature)
                .toArray(Feature[]::new);
    }

    private LocationEntity convertFeatureToEntity(Feature feature) {
        LocationEntity entity = new LocationEntity();
        Map<String, Object> propertiesList = feature.getProperties();
        Arrays.stream(LocationEntity.class.getDeclaredFields())
                .filter(i -> !i.isSynthetic())
                .forEach(i -> {
                    try {
                        Field f = LocationEntity.class.getDeclaredField(i.getName());
                        f.setAccessible(true);
                        f.set(entity, propertiesList.getOrDefault(i.getName(), null));
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        log.warn(e.getMessage());
                    }
                });
        entity.setGeometry(GeometryUtils.convertGeoJsonToJtsGeometry(feature.getGeometry()));
        return entity;
    }

    private Feature convertEntityToFeature(LocationEntity entity) {
        Long id = entity.getId();
        org.wololo.geojson.Geometry geometry = GeometryUtils.convertJtsGeometryToGeoJson(entity.getGeometry());

        Map<String, Object> properties = new HashMap<>();
        Arrays.stream(LocationEntity.class.getDeclaredFields())
                .filter(i -> !i.isSynthetic())
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.getType() != Geometry.class && !field.getName().equals("id") && !field.getName().equals("user")) {
                            properties.put(field.getName(), field.get(entity));
                        }
                    } catch (IllegalAccessException e) {
                        log.warn(e.getMessage());
                    }
                });

        return new Feature(id, geometry, properties);
    }
}
