package com.adeprogramming.javagis.stac.service;

import com.adeprogramming.javagis.model.agricultural.AgriculturalField;
import com.adeprogramming.javagis.model.agricultural.SprayRecommendation;
import com.adeprogramming.javagis.model.environmental.MonitoringStation;
import com.adeprogramming.javagis.repository.agricultural.AgriculturalFieldRepository;
import com.adeprogramming.javagis.repository.agricultural.SprayRecommendationRepository;
import com.adeprogramming.javagis.repository.environmental.MonitoringStationRepository;
import com.adeprogramming.javagis.stac.adapter.AgriculturalStacAdapter;
import com.adeprogramming.javagis.stac.adapter.EnvironmentalMonitoringStacAdapter;
import com.adeprogramming.javagis.stac.model.StacItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for managing STAC items.
 * Provides methods for retrieving STAC items from the database.
 */
@Service
public class StacItemService {

    private final MonitoringStationRepository monitoringStationRepository;
    private final AgriculturalFieldRepository agriculturalFieldRepository;
    private final SprayRecommendationRepository sprayRecommendationRepository;
    private final EnvironmentalMonitoringStacAdapter environmentalMonitoringStacAdapter;
    private final AgriculturalStacAdapter agriculturalStacAdapter;

    @Autowired
    public StacItemService(
            MonitoringStationRepository monitoringStationRepository,
            AgriculturalFieldRepository agriculturalFieldRepository,
            SprayRecommendationRepository sprayRecommendationRepository,
            EnvironmentalMonitoringStacAdapter environmentalMonitoringStacAdapter,
            AgriculturalStacAdapter agriculturalStacAdapter) {
        this.monitoringStationRepository = monitoringStationRepository;
        this.agriculturalFieldRepository = agriculturalFieldRepository;
        this.sprayRecommendationRepository = sprayRecommendationRepository;
        this.environmentalMonitoringStacAdapter = environmentalMonitoringStacAdapter;
        this.agriculturalStacAdapter = agriculturalStacAdapter;
    }

    /**
     * Get all STAC items for a collection.
     *
     * @param collectionId the ID of the collection
     * @param baseUrl the base URL for generating links
     * @param pageable pagination information
     * @return a list of STAC items
     */
    public List<StacItem> getItemsByCollection(String collectionId, String baseUrl, Pageable pageable) {
        List<StacItem> items = new ArrayList<>();

        if ("environmental-monitoring".equals(collectionId)) {
            Page<MonitoringStation> stations = monitoringStationRepository.findAll(pageable);
            items = stations.stream()
                    .map(station -> environmentalMonitoringStacAdapter.monitoringStationToStacItem(station, baseUrl))
                    .collect(Collectors.toList());
        } else if ("agricultural-assets".equals(collectionId)) {
            // Get agricultural fields
            Page<AgriculturalField> fields = agriculturalFieldRepository.findAll(pageable);
            List<StacItem> fieldItems = fields.stream()
                    .map(field -> agriculturalStacAdapter.agriculturalFieldToStacItem(field, baseUrl))
                    .toList();
            items.addAll(fieldItems);

            // Get spray recommendations
            if (items.size() < pageable.getPageSize()) {
                int remainingItems = pageable.getPageSize() - items.size();
                Page<SprayRecommendation> recommendations = sprayRecommendationRepository.findAll(pageable.withPage(0).withPage(remainingItems));
                List<StacItem> recommendationItems = recommendations.stream()
                        .map(recommendation -> agriculturalStacAdapter.sprayRecommendationToStacItem(recommendation, baseUrl))
                        .toList();
                items.addAll(recommendationItems);
            }
        }

        return items;
    }

    /**
     * Get a specific STAC item by collection ID and item ID.
     *
     * @param collectionId the ID of the collection
     * @param itemId the ID of the item
     * @param baseUrl the base URL for generating links
     * @return the STAC item
     */
    public Optional<StacItem> getItem(String collectionId, String itemId, String baseUrl) {
        if ("environmental-monitoring".equals(collectionId)) {
            if (itemId.startsWith("station-")) {
                String stationId = itemId.substring("station-".length());
                Optional<MonitoringStation> station = monitoringStationRepository.findByStationId(stationId);
                return station.map(s -> environmentalMonitoringStacAdapter.monitoringStationToStacItem(s, baseUrl));
            }
        } else if ("agricultural-assets".equals(collectionId)) {
            if (itemId.startsWith("field-")) {
                String fieldId = itemId.substring("field-".length());
                Optional<AgriculturalField> field = agriculturalFieldRepository.findByFieldId(fieldId);
                return field.map(f -> agriculturalStacAdapter.agriculturalFieldToStacItem(f, baseUrl));
            } else if (itemId.startsWith("recommendation-")) {
                String recommendationId = itemId.substring("recommendation-".length());
                try {
                    Long id = Long.parseLong(recommendationId);
                    Optional<SprayRecommendation> recommendation = sprayRecommendationRepository.findById(id);
                    return recommendation.map(r -> agriculturalStacAdapter.sprayRecommendationToStacItem(r, baseUrl));
                } catch (NumberFormatException e) {
                    return Optional.empty();
                }
            }
        }

        return Optional.empty();
    }
}

