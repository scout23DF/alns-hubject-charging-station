package de.com.alns.codingtest.hubject.chargingstationdata.web.rest;

import de.com.alns.codingtest.hubject.chargingstationdata.models.ChargingStation;
import de.com.alns.codingtest.hubject.chargingstationdata.services.IChargingStationService;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;
import io.swagger.annotations.*;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CommonsLog
@Api(value = "charging-station", description = "The Charging Station API", tags = { "ChargingStation Resource" })
public class ChargingStationController {

    public static final String REST_API_ENTITY_PATH = "charging-stations";

    private final IChargingStationService chargingStationService;

    @Autowired
    public ChargingStationController(IChargingStationService pChargingService) {
        this.chargingStationService = pChargingService;
    }

    @ApiOperation(value = "Create one Charging Station", nickname = "createChargingStation", notes = "", response = ChargingStation.class, tags={ "ChargingStation Resource" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChargingStation.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @PostMapping(value = "/" + REST_API_ENTITY_PATH, produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<ChargingStation> createChargingStation(@ApiParam(value = "Charging Station to create", required=true)
                                                                 @Valid @RequestBody ChargingStation pChargingStation) {

        ChargingStation newEntity;
        UriComponentsBuilder ucBuilder;
        HttpHeaders httpHeaders;

        newEntity = chargingStationService.saveChargingStation(pChargingStation);

        ucBuilder = UriComponentsBuilder.newInstance();
        httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("/api/" + REST_API_ENTITY_PATH + "/{pId}").buildAndExpand(newEntity.getMeaningfullId()).toUri());

        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Create multiple Charging Stations", nickname = "createChargingStations", notes = "", response = ChargingStation.class, tags={ "ChargingStation Resource" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChargingStation.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @PostMapping(value = "/" + REST_API_ENTITY_PATH + "/insertAsList", produces = { "application/json" }, consumes = { "application/json" })
    public ResponseEntity<List<ChargingStation>> createChargingStations(@ApiParam(value = "List of Charging Stations to create", required=true)
                                                                        @Valid @RequestBody List<ChargingStation> pChargingStationsList) {

        List<ChargingStation> newEntitiesList;

        newEntitiesList = chargingStationService.saveChargingStationsList(pChargingStationsList);

        return ResponseEntity.ok(newEntitiesList);
    }

    @ApiOperation(value = "Modify a Charging Station", nickname = "modifyChargingStation", notes = "", response = ChargingStation.class, tags={ "ChargingStation Resource" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChargingStation.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @PutMapping(value = "/" + REST_API_ENTITY_PATH, produces = { "application/json" }, consumes = { "application/json" })
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

    @ApiOperation(value = "Remove a Charging Station", nickname = "deleteChargingStationById", notes = "", response = ChargingStation.class, tags={ "ChargingStation Resource" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChargingStation.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
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

    @ApiOperation(value = "Get all paginated Charging Stations created", nickname = "getAllChargingStations", notes = "", response = ChargingStation.class, tags={ "ChargingStation Resource" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChargingStation.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping(value = "/" + REST_API_ENTITY_PATH, produces = { "application/json" })
    public Page<ChargingStation> getAllChargingStations(Pageable pPageable) {
        Page<ChargingStation> entitiesFoundPage;

        entitiesFoundPage = chargingStationService.searchAllChargingStations(pPageable);

        return entitiesFoundPage;
    }

    @ApiOperation(value = "Get the Charging Station of respective Id", nickname = "getChargingStationById", notes = "", response = ChargingStation.class, tags={ "ChargingStation Resource" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChargingStation.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/{pId}", produces = { "application/json" })
    public ResponseEntity<ChargingStation> getChargingStationById(@PathVariable("pId") String pId) {
        ResponseEntity<ChargingStation> responseEntityResult;
        Optional<ChargingStation> optChangingStationFound;

        optChangingStationFound = chargingStationService.searchChargingStationById(pId);
        responseEntityResult = optChangingStationFound.map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));

        return responseEntityResult;
    }

    @ApiOperation(value = "Get the Charging Stations associated with ZipCode number informed", nickname = "getChargingStationsByZipCode", notes = "", response = ChargingStation.class, tags={ "ChargingStation Resource" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChargingStation.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/searchByZipCode", params = {"pZipCodeNumber"}, produces = { "application/json" })
    public ResponseEntity<List<ChargingStation>> getChargingStationsByZipCode(@RequestParam("pZipCodeNumber") String pZipCodeNumber) {
        ResponseEntity<List<ChargingStation>> responseEntityResult;
        List<ChargingStation> entitiesFoundList;

        entitiesFoundList = chargingStationService.searchChargingStationsByZipCode(pZipCodeNumber);
        responseEntityResult = ResponseEntity.ok(entitiesFoundList);

        return responseEntityResult;
    }

    @ApiOperation(value = "Get the Charging Stations located within the circle perimeter around the Point informed, based on Radius in Km informed", nickname = "getChargingStationsInMyPerimeter", notes = "", response = ChargingStation.class, tags={ "ChargingStation Resource" })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ChargingStation.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping(value = "/" + REST_API_ENTITY_PATH + "/searchByPerimeter", params = {"pLatitudeRef", "pLongitudeRef", "pRadiusInKm"}, produces = { "application/json" })
    public ResponseEntity<List<ChargingStation>> getChargingStationsInMyPerimeter(@RequestParam("pLatitudeRef") Double pLatitudeRef,
                                                                                  @RequestParam("pLongitudeRef") Double pLongitudeRef,
                                                                                  @RequestParam("pRadiusInKm") Double pRadiusInKm) {
        ResponseEntity<List<ChargingStation>> responseEntityResult;
        List<ChargingStation> entitiesFoundList;
        PointLocationDTO circleCentralPoint = null;

        circleCentralPoint = new PointLocationDTO(pLatitudeRef, pLongitudeRef);
        entitiesFoundList = chargingStationService.searchChargingStationsInCirclePerimeter(circleCentralPoint, pRadiusInKm);
        responseEntityResult = ResponseEntity.ok(entitiesFoundList);

        return responseEntityResult;
    }

}
