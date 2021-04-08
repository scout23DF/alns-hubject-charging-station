-- create schema if not exists "hubject";

drop table if exists public.country;

create table public.country (id bigint not null, shape GEOMETRY, iso_a2 varchar(2), iso_a3 varchar(3), name varchar(40), primary key (id));

create index if not exists sidx_country_shape on public.country USING GIST (shape);

truncate table public.country;

-- create table tb_charging_station (
--     meaningfull_id char(17) not null,
--     des_charging_station varchar(255),
--     geo_point_location geometry,
--     sta_active boolean,
--     sta_available boolean,
--     pct_energy_loaded numeric(19, 2),
--     num_zip_code varchar(5),
--     primary key (meaningfull_id)
-- );

-- create index if not exists sidx_chargingstation_geopointlocation on public.tb_charging_station USING GIST (geo_point_location);
