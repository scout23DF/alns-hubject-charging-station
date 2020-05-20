CREATE SCHEMA IF NOT EXISTS "HUBJECT";

-- Needed to add H2GIS support for spatial data types
CREATE ALIAS IF NOT EXISTS H2GIS_SPATIAL FOR "org.h2gis.functions.factory.H2GISFunctions.load";
CALL H2GIS_SPATIAL();


drop table if exists public.country;
drop table if exists public.location;

create table public.country (id bigint not null, shape GEOMETRY, iso_a2 varchar(2), iso_a3 varchar(3), name varchar(40), primary key (id));
create table public.location (id bigint not null, geometry GEOMETRY, name varchar(255), user_id varchar(30), primary key (id));

CREATE INDEX IF NOT EXISTS sidx_country_shape ON public.country (shape);
CREATE INDEX IF NOT EXISTS sidx_location_geometry ON public.location (geometry);



