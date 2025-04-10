package com.adeprogramming.javagis.config;

import org.geotools.api.referencing.FactoryException;
import org.geotools.api.referencing.crs.CoordinateReferenceSystem;
import org.geotools.referencing.CRS;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GeoTools configuration for the BASF Enterprise JavaGIS Application.
 * Configures GeoTools-related beans and settings.
 */
@Configuration
public class GeoToolsConfig {

    /**
     * Default coordinate reference system (WGS84)
     */
    @Bean
    public CoordinateReferenceSystem defaultCrs() throws FactoryException {
        System.setProperty("org.geotools.referencing.forceXY", "true");
        return CRS.decode("EPSG:4326", true);
    }

    /**
     * European ETRS89 coordinate reference system commonly used in Germany
     */
    @Bean
    public CoordinateReferenceSystem etrs89Crs() throws FactoryException {
        return CRS.decode("EPSG:3035", true);
    }
}