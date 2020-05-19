-- Needed to add H2GIS support for spatial data types
CREATE ALIAS InitGeoDB for "geodb.GeoDB.InitGeoDB";
CALL InitGeoDB();