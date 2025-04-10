package com.adeprogramming.javagis.stac.adapter;


import com.adeprogramming.javagis.model.environmental.MonitoringStation;
import com.adeprogramming.javagis.stac.model.StacAsset;
import com.adeprogramming.javagis.stac.model.StacItem;
import com.adeprogramming.javagis.stac.model.StacLink;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Adapter for converting environmental monitoring entities to STAC items.
 */
@Component
public class EnvironmentalMonitoringStacAdapter {

    private final GeoJsonWriter geoJsonWriter;
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public EnvironmentalMonitoringStacAdapter() {
        this.geoJsonWriter = new GeoJsonWriter();
        this.geoJsonWriter.setEncodeCRS(true);
    }

    /**
     * Convert a MonitoringStation to a STAC Item.
     *
     * @param station the monitoring station
     * @param baseUrl the base URL for generating links
     * @return the STAC item
     */
    public StacItem monitoringStationToStacItem(MonitoringStation station, String baseUrl) {
        // Create item ID
        String itemId = "station-" + station.getStationId();

        // Convert geometry to GeoJSON
        Map<String, Object> geometryMap = geometryToMap(station.getGeometry());

        // Create properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("datetime", station.getCreatedAt().atZone(ZoneOffset.UTC).format(ISO_FORMATTER));
        properties.put("name", station.getName());
        properties.put("description", station.getDescription());
        properties.put("station_id", station.getStationId());
        properties.put("station_type", station.getStationType().name());
        properties.put("status", station.getStatus().name());
        properties.put("location_description", station.getLocationDescription());
        properties.put("installation_date", station.getInstallationDate());
        properties.put("maintenance_date", station.getMaintenanceDate());

        // Create links
        List<StacLink> links = new ArrayList<>();
        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/environmental-monitoring/items/" + itemId)
                .rel("self")
                .type("application/json")
                .title("Monitoring Station " + station.getStationId())
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/environmental-monitoring")
                .rel("parent")
                .type("application/json")
                .title("Environmental Monitoring Collection")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/environmental-monitoring")
                .rel("collection")
                .type("application/json")
                .title("Environmental Monitoring Collection")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac")
                .rel("root")
                .type("application/json")
                .title("Root STAC Catalog")
                .build());

        // Create assets
        Map<String, StacAsset> assets = new HashMap<>();

        // Add station data asset
        assets.put("station-data", StacAsset.builder()
                .href(baseUrl + "/api/environmental/stations/" + station.getId())
                .title("Station Data")
                .description("Detailed information about the monitoring station")
                .type("application/json")
                .roles(new String[]{"data"})
                .build());

        // Add measurements asset
        assets.put("measurements", StacAsset.builder()
                .href(baseUrl + "/api/environmental/stations/" + station.getId() + "/measurements")
                .title("Measurements")
                .description("Measurements from this monitoring station")
                .type("application/json")
                .roles(new String[]{"data"})
                .build());

        // Create STAC item
        return StacItem.builder()
                .stac_version("1.0.0")
                .id(itemId)
                .type("Feature")
                .geometry(geometryMap)
                .properties(properties)
                .links(links)
                .assets(assets)
                .collection("environmental-monitoring")
                .build();
    }

    /**
     * Convert a JTS Geometry to a GeoJSON Map.
     *
     * @param geometry the JTS geometry
     * @return the GeoJSON map
     */
    private Map<String, Object> geometryToMap(Geometry geometry) {
        try {
            String geoJson = geoJsonWriter.write(geometry);
            // This is a simplified approach - in a real application, you would use a proper JSON parser
            // to convert the GeoJSON string to a Map
            Map<String, Object> geometryMap = new HashMap<>();
            geometryMap.put("type", geometry.getGeometryType());

            // For Point geometries
            if (geometry.getGeometryType().equals("Point")) {
                double[] coordinates = new double[2];
                coordinates[0] = geometry.getCoordinate().x;
                coordinates[1] = geometry.getCoordinate().y;
                geometryMap.put("coordinates", coordinates);
            }
            // For other geometry types, you would need to handle them accordingly

            return geometryMap;
        } catch (Exception e) {
            // Fallback to a simple point at 0,0 if conversion fails
            Map<String, Object> geometryMap = new HashMap<>();
            geometryMap.put("type", "Point");
            geometryMap.put("coordinates", new double[]{0, 0});
            return geometryMap;
        }
    }
}
