package de.com.alns.codingtest.hubject.chargingstationdata.web.rest;

import de.com.alns.codingtest.hubject.chargingstationdata.services.IChargingStationService;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.ChargingStationDTO;
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

    private IChargingStationService chargingStationService;

    @Autowired
    public ChargingStationController(IChargingStationService pChargingService) {
        this.chargingStationService = pChargingService;
    }


    @PostMapping(value = "/" + REST_API_ENTITY_PATH)
    public ResponseEntity<ChargingStationDTO> createChargingStation(@RequestBody ChargingStationDTO pChargingStationDTO) {

        ChargingStationDTO newEntity;
        UriComponentsBuilder ucBuilder;
        HttpHeaders httpHeaders;

        newEntity = chargingStationService.saveChargingStation(pChargingStationDTO);

        ucBuilder = UriComponentsBuilder.newInstance();
        httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/charging-station/{pId}").buildAndExpand(newEntity.getMeaningFullId()).toUri());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping(value = "/" + REST_API_ENTITY_PATH + "/{pId}")
    public ResponseEntity<ChargingStationDTO> modifyChargingStation(@PathVariable("pId") String pId,
                                                                    @RequestBody ChargingStationDTO pChargingStationDTO) {
        ResponseEntity<ChargingStationDTO> responseEntityResult = null;

        if (!chargingStationService.exists(pId)) {
            responseEntityResult = new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            pChargingStationDTO = chargingStationService.updateChargingStation(pChargingStationDTO);
            responseEntityResult = ResponseEntity.ok(pChargingStationDTO);
        }

        return responseEntityResult;
    }

    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/{pId}")
    public ResponseEntity<ChargingStationDTO> getChargingStationById(@PathVariable("pId") String pId) {
        ResponseEntity<ChargingStationDTO> responseEntityResult;
        Optional<ChargingStationDTO> optChangingStationFound;

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
    public Page<ChargingStationDTO> getAllChargingStations(Pageable pPageable) {
        Page<ChargingStationDTO> entitiesFoundPage;

        entitiesFoundPage = chargingStationService.searchAllChargingStations(pPageable);

        return entitiesFoundPage;
    }

    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/searchByZipCode")
    public ResponseEntity<List<ChargingStationDTO>> getChargingStationsByZipCode(@RequestParam("pZipCodeNumber") String pZipCodeNumber) {
        ResponseEntity<List<ChargingStationDTO>> responseEntityResult;
        List<ChargingStationDTO> entitiesFoundList;

        entitiesFoundList = chargingStationService.searchChargingStationsByZipCode(pZipCodeNumber);

        responseEntityResult = ResponseEntity.ok(entitiesFoundList);

        return responseEntityResult;
    }

    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/searchByPerimeter")
    public ResponseEntity<List<ChargingStationDTO>> getChargingStationsInMyPerimeter(@RequestParam("pLatitudeRef") Double pLatitudeRef,
                                                                                     @RequestParam("pLongitudeRef") Double pLongitudeRef,
                                                                                     @RequestParam("pRadius") Double pRadius) {
        ResponseEntity<List<ChargingStationDTO>> responseEntityResult;
        List<ChargingStationDTO> entitiesFoundList;

        entitiesFoundList = chargingStationService.searchChargingStationsInCirclePerimeter(pLatitudeRef, pLongitudeRef, pRadius);

        responseEntityResult = ResponseEntity.ok(entitiesFoundList);

        return responseEntityResult;
    }

}
