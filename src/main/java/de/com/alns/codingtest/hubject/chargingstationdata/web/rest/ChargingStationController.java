package de.com.alns.codingtest.hubject.chargingstationdata.web.rest;

import de.com.alns.codingtest.hubject.chargingstationdata.services.IChargingStationService;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.ChargingStationDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController("/api")
@CommonsLog
public class ChargingStationController {

    private IChargingStationService chargingStationService;

    @Autowired
    public ChargingStationController(IChargingStationService pChargingService) {
        this.chargingStationService = pChargingService;
    }


    @RequestMapping(value = "/charging-station", method = RequestMethod.POST)
    public ResponseEntity<ChargingStationDTO> postLocation(@RequestBody ChargingStationDTO pChargingStationDTO) {

        Long id = chargingStationService.saveChargingStation(pChargingStationDTO);

        UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/location/{id}").buildAndExpand(id).toUri());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    /*
    @RequestMapping(value = "/location/{id}", method = RequestMethod.GET)
    public ResponseEntity getLocationById(@RequestHeader(value = AUTHORIZATION) String userId,
            @PathVariable("id") Long id) {

        Optional<Feature> location = locationService.findLocationById(userId, id);
        return location.map(i -> new ResponseEntity<>(i, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/location/{id}", method = RequestMethod.PUT)
    public ResponseEntity putLocation(@RequestHeader(value = AUTHORIZATION) String userId,
            @PathVariable("id") Long id,
            @RequestBody Feature feature) {

        if (!locationService.exists(userId, id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        locationService.updateLocation(userId, id, feature);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/location/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteLocation(@RequestHeader(value = AUTHORIZATION) String userId,
            @PathVariable("id") Long id) {

        if (!locationService.exists(userId, id)) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        locationService.deleteLocation(userId, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/locations", method = RequestMethod.GET)
    public ResponseEntity<FeatureCollection> getAllLocations(@RequestHeader(value = AUTHORIZATION) String userId) {
        return new ResponseEntity<>(locationService.findAllLocations(userId), HttpStatus.OK);
    }

    @RequestMapping(value = "/locations/within", method = RequestMethod.POST)
    public ResponseEntity<FeatureCollection> getLocationsByGeometry(@RequestHeader(value = AUTHORIZATION) String userId,
            @RequestBody org.wololo.geojson.Geometry geoJson) {

        return new ResponseEntity<>(locationService.findAllLocationsWithin(userId, geoJson), HttpStatus.OK);
    }
    */
}
