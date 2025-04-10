package com.adeprogramming.javagis.stac.service;

import com.adeprogramming.javagis.stac.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service for managing the STAC catalog.
 * Provides methods for creating and retrieving STAC objects.
 */
@Service
public class StacCatalogService {

    @Value("${stac.catalog.title}")
    private String catalogTitle;

    @Value("${stac.catalog.description}")
    private String catalogDescription;

    @Value("${stac.catalog.version}")
    private String catalogVersion;

    @Value("${stac.catalog.id}")
    private String catalogId;

    /**
     * Get the root STAC catalog.
     *
     * @param baseUrl the base URL for generating links
     * @return the root STAC catalog
     */
    public StacCatalog getRootCatalog(String baseUrl) {
        List<StacLink> links = new ArrayList<>();
        links.add(StacLink.builder()
                .href(baseUrl + "/stac")
                .rel("self")
                .type("application/json")
                .title("Root STAC Catalog")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections")
                .rel("collections")
                .type("application/json")
                .title("Collections")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/search")
                .rel("search")
                .type("application/json")
                .title("STAC Search")
                .build());

        return StacCatalog.builder()
                .stac_version(catalogVersion)
                .id(catalogId)
                .title(catalogTitle)
                .description(catalogDescription)
                .type("Catalog")
                .links(links)
                .build();
    }

    /**
     * Get all STAC collections.
     *
     * @param baseUrl the base URL for generating links
     * @return a list of STAC collections
     */
    public List<StacCollection> getCollections(String baseUrl) {
        List<StacCollection> collections = new ArrayList<>();

        // Environmental Monitoring Collection
        collections.add(createEnvironmentalMonitoringCollection(baseUrl));

        // Agricultural Assets Collection
        collections.add(createAgriculturalAssetsCollection(baseUrl));

        return collections;
    }

    /**
     * Get a specific STAC collection by ID.
     *
     * @param collectionId the ID of the collection
     * @param baseUrl the base URL for generating links
     * @return the STAC collection
     */
    public StacCollection getCollection(String collectionId, String baseUrl) {
        if ("environmental-monitoring".equals(collectionId)) {
            return createEnvironmentalMonitoringCollection(baseUrl);
        } else if ("agricultural-assets".equals(collectionId)) {
            return createAgriculturalAssetsCollection(baseUrl);
        }
        return null;
    }

    /**
     * Create the Environmental Monitoring STAC collection.
     *
     * @param baseUrl the base URL for generating links
     * @return the Environmental Monitoring STAC collection
     */
    private StacCollection createEnvironmentalMonitoringCollection(String baseUrl) {
        List<StacLink> links = new ArrayList<>();
        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/environmental-monitoring")
                .rel("self")
                .type("application/json")
                .title("Environmental Monitoring Collection")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac")
                .rel("parent")
                .type("application/json")
                .title("Root STAC Catalog")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/environmental-monitoring/items")
                .rel("items")
                .type("application/json")
                .title("Environmental Monitoring Items")
                .build());

        // Create spatial extent (covering Germany)
        double[][] bbox = {{5.866, 47.270, 15.042, 55.099}};
        StacExtent.SpatialExtent spatialExtent = StacExtent.SpatialExtent.builder()
                .bbox(bbox)
                .build();

        // Create temporal extent (open-ended)
        String[][] interval = {{"2020-01-01T00:00:00Z", null}};
        StacExtent.TemporalExtent temporalExtent = StacExtent.TemporalExtent.builder()
                .interval(interval)
                .build();

        StacExtent extent = StacExtent.builder()
                .spatial(spatialExtent)
                .temporal(temporalExtent)
                .build();

        // Create provider
        StacProvider provider = StacProvider.builder()
                .name("BASF GmbH")
                .url("https://www.basf.com")
                .roles(new String[]{"producer", "licensor", "host"})
                .description("BASF Environmental Monitoring Center")
                .build();

        return StacCollection.builder()
                .stac_version(catalogVersion)
                .id("environmental-monitoring")
                .title("Environmental Monitoring Assets")
                .description("Collection of environmental monitoring data including air quality, water quality, and noise pollution")
                .type("Collection")
                .links(links)
                .license("proprietary")
                .providers(Arrays.asList(provider))
                .extent(extent)
                .build();
    }

    /**
     * Create the Agricultural Assets STAC collection.
     *
     * @param baseUrl the base URL for generating links
     * @return the Agricultural Assets STAC collection
     */
    private StacCollection createAgriculturalAssetsCollection(String baseUrl) {
        List<StacLink> links = new ArrayList<>();
        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets")
                .rel("self")
                .type("application/json")
                .title("Agricultural Assets Collection")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac")
                .rel("parent")
                .type("application/json")
                .title("Root STAC Catalog")
                .build());

        links.add(StacLink.builder()
                .href(baseUrl + "/stac/collections/agricultural-assets/items")
                .rel("items")
                .type("application/json")
                .title("Agricultural Assets Items")
                .build());

        // Create spatial extent (covering Germany)
        double[][] bbox = {{5.866, 47.270, 15.042, 55.099}};
        StacExtent.SpatialExtent spatialExtent = StacExtent.SpatialExtent.builder()
                .bbox(bbox)
                .build();

        // Create temporal extent (open-ended)
        String[][] interval = {{"2020-01-01T00:00:00Z", null}};
        StacExtent.TemporalExtent temporalExtent = StacExtent.TemporalExtent.builder()
                .interval(interval)
                .build();

        StacExtent extent = StacExtent.builder()
                .spatial(spatialExtent)
                .temporal(temporalExtent)
                .build();

        // Create provider
        StacProvider provider = StacProvider.builder()
                .name("BASF GmbH")
                .url("https://agriculture.basf.com")
                .roles(new String[]{"producer", "licensor", "host"})
                .description("BASF Agricultural Solutions")
                .build();

        return StacCollection.builder()
                .stac_version(catalogVersion)
                .id("agricultural-assets")
                .title("Agricultural Assets")
                .description("Collection of agricultural data including field boundaries, crop health, and spray recommendations")
                .type("Collection")
                .links(links)
                .license("proprietary")
                .providers(Arrays.asList(provider))
                .extent(extent)
                .build();
    }
}

