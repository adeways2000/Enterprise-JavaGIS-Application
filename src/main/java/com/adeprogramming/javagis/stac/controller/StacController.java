package com.adeprogramming.javagis.stac.controller;

import com.adeprogramming.javagis.stac.model.StacCatalog;
import com.adeprogramming.javagis.stac.model.StacCollection;
import com.adeprogramming.javagis.stac.service.StacCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * REST controller for STAC catalog endpoints.
 * Provides endpoints for accessing STAC catalog, collections, and items.
 */
@RestController
@RequestMapping("/stac")
public class StacController {

    private final StacCatalogService stacCatalogService;

    @Autowired
    public StacController(StacCatalogService stacCatalogService) {
        this.stacCatalogService = stacCatalogService;
    }

    /**
     * Get the root STAC catalog.
     *
     * @param request the HTTP request
     * @return the root STAC catalog
     */
    @GetMapping
    public ResponseEntity<StacCatalog> getRootCatalog(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        StacCatalog catalog = stacCatalogService.getRootCatalog(baseUrl);
        return ResponseEntity.ok(catalog);
    }

    /**
     * Get all STAC collections.
     *
     * @param request the HTTP request
     * @return a list of STAC collections
     */
    @GetMapping("/collections")
    public ResponseEntity<List<StacCollection>> getCollections(HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        List<StacCollection> collections = stacCatalogService.getCollections(baseUrl);
        return ResponseEntity.ok(collections);
    }

    /**
     * Get a specific STAC collection by ID.
     *
     * @param collectionId the ID of the collection
     * @param request the HTTP request
     * @return the STAC collection
     */
    @GetMapping("/collections/{collectionId}")
    public ResponseEntity<StacCollection> getCollection(
            @PathVariable String collectionId,
            HttpServletRequest request) {
        String baseUrl = getBaseUrl(request);
        StacCollection collection = stacCatalogService.getCollection(collectionId, baseUrl);
        if (collection == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(collection);
    }

    /**
     * Get the base URL from the HTTP request.
     *
     * @param request the HTTP request
     * @return the base URL
     */
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();

        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if ((serverPort != 80) && (serverPort != 443)) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath);

        if (url.toString().endsWith("/")) {
            url.deleteCharAt(url.length() - 1);
        }

        return url.toString();
    }
}
