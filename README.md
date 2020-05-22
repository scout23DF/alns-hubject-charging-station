# alns-hubject-charging-station
Project for handle Charging Station Data :: A Coding Task for a position in HubJect Company


# Coding Task

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


# Test Data:

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
 
 
 52.514448, 13.412000, 1 => []
 52.514449, 13.412000, 1 => [DE.BER.D007.S0003]
 52.514459, 13.412000, 1 => [DE.BER.D007.S0002, DE.BER.D007.S0003]
 52.514471, 13.412000, 1 => [DE.BER.D007.S0001, DE.BER.D007.S0002, DE.BER.D007.S0003]
 ```
 
