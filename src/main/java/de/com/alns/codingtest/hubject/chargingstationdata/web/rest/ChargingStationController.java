package de.com.alns.codingtest.hubject.chargingstationdata.web.rest;

import de.com.alns.codingtest.hubject.chargingstationdata.domain.models.ChargingStation;
import de.com.alns.codingtest.hubject.chargingstationdata.services.IChargingStationService;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CommonsLog
public class ChargingStationController {

    public static final String REST_API_ENTITY_PATH = "charging-stations";

    private final IChargingStationService chargingStationService;

    @Autowired
    public ChargingStationController(IChargingStationService pChargingService) {
        this.chargingStationService = pChargingService;
    }

    @PostMapping(value = "/" + REST_API_ENTITY_PATH)
    public ResponseEntity<ChargingStation> createChargingStation(@RequestBody ChargingStation pChargingStation) {

        ChargingStation newEntity;
        UriComponentsBuilder ucBuilder;
        HttpHeaders httpHeaders;

        newEntity = chargingStationService.saveChargingStation(pChargingStation);

        ucBuilder = UriComponentsBuilder.newInstance();
        httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/" + REST_API_ENTITY_PATH + "/{pId}").buildAndExpand(newEntity.getMeaningfullId()).toUri());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PostMapping(value = "/" + REST_API_ENTITY_PATH + "/insertAsList")
    public ResponseEntity<List<ChargingStation>> createCollectionChargingStation(@RequestBody List<ChargingStation> pChargingStationsList) {

        List<ChargingStation> newEntitiesList;

        newEntitiesList = chargingStationService.saveChargingStationsList(pChargingStationsList);

        return ResponseEntity.ok(newEntitiesList);
    }

    @PutMapping(value = "/" + REST_API_ENTITY_PATH)
    public ResponseEntity<ChargingStation> modifyChargingStation(@RequestBody ChargingStation pChargingStation) {
        ResponseEntity<ChargingStation> responseEntityResult = null;

        if (!chargingStationService.exists(pChargingStation.getMeaningfullId())) {
            responseEntityResult = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            pChargingStation = chargingStationService.saveChargingStation(pChargingStation);
            responseEntityResult = ResponseEntity.ok(pChargingStation);
        }

        return responseEntityResult;
    }

    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/{pId}")
    public ResponseEntity<ChargingStation> getChargingStationById(@PathVariable("pId") String pId) {
        ResponseEntity<ChargingStation> responseEntityResult;
        Optional<ChargingStation> optChangingStationFound;

        optChangingStationFound = chargingStationService.searchChargingStationById(pId);

        responseEntityResult = optChangingStationFound.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));

        return responseEntityResult;
    }

    @DeleteMapping(value = "/" + REST_API_ENTITY_PATH + "/{pId}")
    public ResponseEntity<Void> deleteChargingStationById(@PathVariable("pId") String pId) {
        ResponseEntity<Void> responseEntityResult = null;

        if (!chargingStationService.exists(pId)) {
            responseEntityResult = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            chargingStationService.deleteChargingStation(pId);
            responseEntityResult = ResponseEntity.ok().build();
        }

        return responseEntityResult;

    }

    @GetMapping(value = "/" + REST_API_ENTITY_PATH)
    public Page<ChargingStation> getAllChargingStations(Pageable pPageable) {
        Page<ChargingStation> entitiesFoundPage;

        entitiesFoundPage = chargingStationService.searchAllChargingStations(pPageable);

        return entitiesFoundPage;
    }

    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/searchByZipCode", params = {"pZipCodeNumber"})
    public ResponseEntity<List<ChargingStation>> getChargingStationsByZipCode(@RequestParam("pZipCodeNumber") String pZipCodeNumber) {
        ResponseEntity<List<ChargingStation>> responseEntityResult;
        List<ChargingStation> entitiesFoundList;

        entitiesFoundList = chargingStationService.searchChargingStationsByZipCode(pZipCodeNumber);

        responseEntityResult = ResponseEntity.ok(entitiesFoundList);

        return responseEntityResult;
    }

    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/searchByPerimeter", params = {"pLatitudeRef", "pLongitudeRef", "pRadius"})
    public ResponseEntity<List<ChargingStation>> getChargingStationsInMyPerimeter(@RequestParam("pLatitudeRef") Double pLatitudeRef,
                                                                                  @RequestParam("pLongitudeRef") Double pLongitudeRef,
                                                                                  @RequestParam("pRadius") Double pRadius) {
        ResponseEntity<List<ChargingStation>> responseEntityResult;
        List<ChargingStation> entitiesFoundList;
        PointLocationDTO circleCentralPoint = null;

        circleCentralPoint = new PointLocationDTO(pLatitudeRef, pLongitudeRef);

        entitiesFoundList = chargingStationService.searchChargingStationsInCirclePerimeter(circleCentralPoint, pRadius);

        responseEntityResult = ResponseEntity.ok(entitiesFoundList);

        return responseEntityResult;
    }

}
