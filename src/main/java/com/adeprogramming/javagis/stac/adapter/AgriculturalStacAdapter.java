package com.adeprogramming.javagis.stac.adapter;

import com.adeprogramming.javagis.model.agricultural.AgriculturalField;
import com.adeprogramming.javagis.model.agricultural.SprayRecommendation;
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
 * Adapter for converting agricultural entities to STAC items.
 */
@Component
public class AgriculturalStacAdapter {

    private final GeoJsonWriter geoJsonWriter;
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public AgriculturalStacAdapter() {
        this.geoJsonWriter = new GeoJsonWriter();
        this.geoJsonWriter.setEncodeCRS(true);
    }

    /**
     * Convert an AgriculturalField to a STAC Item.
     *
     * @param field the agricultural field
     * @param baseUrl the base URL for generating links
     * @return the STAC item
     */
    public StacItem agriculturalFieldToStacItem(AgriculturalField field, String baseUrl) {
        // Create item ID
        String itemId = "field-" + field.getFieldId();

        // Convert geometry to GeoJSON
        Map<String, Object> geometryMap = geometryToMap(field.getGeometry());

        // Create properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("datetime", field.getCreatedAt().atZone(ZoneOffset.UTC).format(ISO_FORMATTER));
        properties.put("name", field.getName());
        properties.put("description", field.getDescription());
        properties.put("field_id", field.getFieldId());
        properties.put("area_hectares", field.getAreaHectares());
        properties.put("soil_type", field.getSoilType());
        properties.put("current_crop", field.getCurrentCrop());
        properties.put("planting_date", field.getPlantingDate() != null ? field.getPlantingDate().toString() : null);
        properties.put("expected_harvest_date", field.getExpectedHarvestDate() != null ? field.getExpectedHarvestDate().toString() : null);
        properties.put("last_spray_date", field.getLastSprayDate() != null ? field.getLastSprayDate().toString() : null);
        properties.put("last_spray_product", field.getLastSprayProduct());
        properties.put("owner_name", field.getOwnerName());
        properties.put("status", field.getStatus().name());

        // Create links
        List<StacLink> links = new ArrayList<>();
        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets/items/" + itemId)
                .rel("self")
                .type("application/json")
                .title("Agricultural Field " + field.getFieldId())
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets")
                .rel("parent")
                .type("application/json")
                .title("Agricultural Assets Collection")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets")
                .rel("collection")
                .type("application/json")
                .title("Agricultural Assets Collection")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac")
                .rel("root")
                .type("application/json")
                .title("Root STAC Catalog")
                .build());

        // Create assets
        Map<String, StacAsset> assets = new HashMap<>();

        // Add field data asset
        assets.put("field-data", StacAsset.builder()
                .href(baseUrl + "/api/agricultural/fields/" + field.getId())
                .title("Field Data")
                .description("Detailed information about the agricultural field")
                .type("application/json")
                .roles(new String[]{"data"})
                .build());

        // Add spray recommendations asset
        assets.put("spray-recommendations", StacAsset.builder()
                .href(baseUrl + "/api/agricultural/fields/" + field.getId() + "/recommendations")
                .title("Spray Recommendations")
                .description("Spray recommendations for this field")
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
                .collection("agricultural-assets")
                .build();
    }

    /**
     * Convert a SprayRecommendation to a STAC Item.
     *
     * @param recommendation the spray recommendation
     * @param baseUrl the base URL for generating links
     * @return the STAC item
     */
    public StacItem sprayRecommendationToStacItem(SprayRecommendation recommendation, String baseUrl) {
        // Create item ID
        String itemId = "recommendation-" + recommendation.getId();

        // Convert geometry to GeoJSON (use target area if available, otherwise use field geometry)
        Geometry geometry = recommendation.getTargetArea() != null ?
                recommendation.getTargetArea() : recommendation.getField().getGeometry();
        Map<String, Object> geometryMap = geometryToMap(geometry);

        // Create properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("datetime", recommendation.getCreatedAt().atZone(ZoneOffset.UTC).format(ISO_FORMATTER));
        properties.put("field_id", recommendation.getField().getFieldId());
        properties.put("product_name", recommendation.getProductName());
        properties.put("application_rate", recommendation.getApplicationRate());
        properties.put("application_rate_unit", recommendation.getApplicationRateUnit());
        properties.put("recommended_date", recommendation.getRecommendedDate().toString());
        properties.put("valid_until", recommendation.getValidUntil() != null ? recommendation.getValidUntil().toString() : null);
        properties.put("weather_conditions", recommendation.getWeatherConditions());
        properties.put("wind_speed", recommendation.getWindSpeed());
        properties.put("wind_direction", recommendation.getWindDirection());
        properties.put("temperature", recommendation.getTemperature());
        properties.put("humidity", recommendation.getHumidity());
        properties.put("precipitation_risk", recommendation.getPrecipitationRisk());
        properties.put("buffer_zone_size", recommendation.getBufferZoneSize());
        properties.put("buffer_zone_unit", recommendation.getBufferZoneUnit());
        properties.put("status", recommendation.getStatus().name());
        properties.put("created_by", recommendation.getCreatedBy());
        properties.put("approved_by", recommendation.getApprovedBy());

        // Create links
        List<StacLink> links = new ArrayList<>();
        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets/items/" + itemId)
                .rel("self")
                .type("application/json")
                .title("Spray Recommendation " + recommendation.getId())
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets")
                .rel("parent")
                .type("application/json")
                .title("Agricultural Assets Collection")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets")
                .rel("collection")
                .type("application/json")
                .title("Agricultural Assets Collection")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac")
                .rel("root")
                .type("application/json")
                .title("Root STAC Catalog")
                .build());

        // Create field link
        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets/items/field-" + recommendation.getField().getFieldId())
                .rel("related")
                .type("application/json")
                .title("Related Field")
                .build());

        // Create assets
        Map<String, StacAsset> assets = new HashMap<>();

        // Add recommendation data asset
        assets.put("recommendation-data", StacAsset.builder()
                .href(baseUrl + "/api/agricultural/recommendations/" + recommendation.getId())
                .title("Recommendation Data")
                .description("Detailed information about the spray recommendation")
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
                .collection("agricultural-assets")
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
