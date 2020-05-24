# alns-hubject-charging-station
Project for handle Charging Station Data :: A Coding Task for a position in HubJect Company


# Coding Task :: The Mission

```
Develop a REST Interface for storage and retrieval of charging station data. The following information needs to be stored per charging station:

- Unique ID (is user-defined)
- Geo-Coordinates
- Zipcode / Postal Code

The interface should provide the following functionality:

- Persist charging stations
- Retrieve/Query charging stations by:
  - ID
  - Zipcode / Postal Code
  - a perimeter around a given geolocation

Write some test cases (conceptual test cases, not automated tests) that cover the functionality adequately.

Pay special attention to:

- REST conventions
- Structure of the source code
- OOP and related principles (SOLID, DRY)
- Documentation and comprehensibility
- Quality Assurance
- Use Java and Spring

Please submit your project via a private GitHub repository (further instruction in the email).

The focus of this task is on your ways of working and the structure of your code. Your do not need to implement all features and your application does not need to work without errors.
```

# The Solution Implemented

I decided to use [PostgreSQL with PostGIS](https://postgis.net/) to support in a professional and robust way the required feature to provide geometric/geographic spatial functions, thus it's easier to handle Hubject Charging Stations data.

I built a simple Spring Boot web application, that handles data operations around Charging Station Entity and its respective database table.

Inspect the `pom.xml` file to know which frameworks and dependencies are used. Inspect the webapp code to compreehend what is the package structure, naming conventions, classes and their tests. 
 
Thanks to Hibernate-Spatial + PostGIS features, the `ChargingStation` Entity/Table has a column of `Geometry(Point)` datatype. This enables us to use the spatial functions. Then please see the `ChargingStationRepository.findInPerimeter()` method and note that I use the following `HQL Query`:

`
@Query("select chgsta from tb_charging_station as chgsta where within(chgsta.geoLocationPoint, :pPerimeterFilter) = TRUE")
` 

As a `:pPerimeterFilter` parameter, I build a `JTS Circle Polygon` calculated using geometric functions, based on the `Radius in Kilometers` parameter input in REST API `/api/charging-stations/searchByPerimeter`.
 
Then before running this WebApp, some infrastructure services should be already started. See how to make them work properly in the next section.

## Running [PostgreSQL + PostGIS] + PGAdmin

### Pre-requisites:
 - Java 8+
 - Maven 3.6.2+
 - [Docker Compose](https://docs.docker.com/compose/) version 1.8.0+

In the Development Environment, [PostgreSQL + PostGIS] + PGAdmin and its dependencies services can be run in Docker Containers throughout Docker-Compose. Follow the steps below:

- Clone the project: [alns-hubject-charging-station)](https://github.com/scout23DF/alns-hubject-charging-station.git)
- Make sure you are at the right branch (master);   
- Create one directory to store the docker volumes:
   * Suggestion: Create the directory under the path below: 
     * `[project-dir]/src/main/docker/volumes/postgreql-data`
- Open and edit the file: `[project-dir]/src/main/docker/.env`;
- Change the Volume path's at the Environment Variable below, point to the folder you've created earlier, like this:
    * `VOLUME_POSTGRESQL_SRV_01_DATA_PATH={Folder You created earlier :: [project-dir]/src/main/docker/volumes/postgreql-data]`

- Build and start the Docker containers issuing the following command:
````
> [project-dir]/src/main/docker/clear && docker-compose up
````  
- If each container has started properly, then you have the following services:

````
> http://localhost:5432  : PostgreSQL + PostGIS Instance with `DBHubjectChargingStation` database.
> [http://localhost:16543)](http://localhost:16543) : PgAdmin web tool
````
  
### Log in at PGAdmin Tool (Optional - Just to inspect database, tables and data, If you wish):

````
* Open URL:      [http://localhost:16543)](http://localhost:16543)
* Connect using this login credential: 
  - User: dba-qq@pgadmin.com 
  - Password: 1a88a1
* Click on `Add New Server` button;
  - Use these settings:
      - Tab: General -> Name: HubjectDocker
      - Tab: Connection -> 
         - Hostname/address: "db-postgis-hubject"
         - Port: 5432
         - Maitenance database: postgres
         - Username: docker
         - Password: 1a88a1  
````
       
- Navigate in this console and inspect the Database objects.

## Building and Running the Application

- Make sure you already have the [PostgreSQL + PostGIS] Database service running;
- To build and run the `alns-hubject-charging-station` Application perform the following steps:
  - Open the project in your lovely IDE and build and run the app throughout that way; OR
  - Go to the project's root and issue the command:
    - [project-dir]/mvn clear && mvn spring-boot:run

## Using the Application

  - By default, when the Application startups firstly, it will create 100 Charging Station records, by default, to have a minimum mass of data to use the WebApp;
    - These Charging Stations are generated randomly to be located in different points spread in Berlin, Deutschland (Around the Coordinates: Lat: 52.520000, Long: 14.415000);
    - Inspect the method:
      - `HubjectChargingStationDataApplication.initializeDatabase()`
  - Open the Swagger-UI page and test the REST API's:
  * `http://localhost:8080/swagger-ui.html`

## Testing 
Inspect the test and integration tests classes.

### Mass of Data:
Here are some JSON Data to test creation/updating `ChargingStation` :
```
[
   {
     "meaningfullId": "DE.BER.D007.S0001",
     "descriptionChargingStation": "Station N# 1 - Mariendorf - Berlin - DE",
     "isActive": true,
     "percentuaEnergyLoaded": 100,
     "isAvailable": true,
     "zipCodeNumber": "12277",
     "pointLocationDTO": {
       "latitude": 52.52343,
       "longitude": 13.41144
     }
   },
   {
     "meaningfullId": "DE.BER.D007.S0002",
     "descriptionChargingStation": "Station N# 2 - Mariendorf - Berlin - DE",
     "isActive": true,
     "percentuaEnergyLoaded": 100,
     "isAvailable": true,
     "zipCodeNumber": "12277",
     "pointLocationDTO": {
       "latitude": 52.523425,
       "longitude": 13.411448
     }
   },
   {
     "meaningfullId": "DE.BER.D007.S0003",
     "descriptionChargingStation": "Station N# 3 - Mariendorf - Berlin - DE",
     "isActive": true,
     "percentuaEnergyLoaded": 100,
     "isAvailable": true,
     "zipCodeNumber": "12277",
     "pointLocationDTO": {
       "latitude": 52.523415,
       "longitude": 13.411461
     }
   }
 ]
```

To test the REST API `/api/charging-stations/searchByPerimeter` you can use and adapt the following sample parameters:

 ``` 
pLatitudeRef | pLongitudeRef | pRadiusInKm
 52.514448 | 13.412000 | 1.0
 52.514449 | 13.412000 | 1.0
 52.514459 | 13.412000 | 2.5
 52.514471 | 13.412000 | 5.0
 ```
 
## Stopping All Services in Docker Containers

- Open other Terminal Session and issue:
  * To only Stop the Containers:
    * `docker-compose stop` 
  * To Stop and Destroy the Containers and its Volumes: 
    * `docker-compose down -v`
    * `docker-compose rm`

## Know Issues

  * I could improve the testcases and test coverage, but the time is short.