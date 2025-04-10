package com.adeprogramming.javagis.model.environmental;

import com.adeprogramming.javagis.model.spatial.SpatialEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing an environmental monitoring station.
 * Used for tracking air quality, water quality, and noise pollution.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monitoring_stations")
public class MonitoringStation extends SpatialEntity {

    @Column(name = "station_id", nullable = false, unique = true)
    private String stationId;

    @Column(name = "station_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private StationType stationType;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StationStatus status = StationStatus.ACTIVE;

    @Column(name = "location_description")
    private String locationDescription;

    @Column(name = "installation_date")
    private String installationDate;

    @Column(name = "maintenance_date")
    private String maintenanceDate;

    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Measurement> measurements = new HashSet<>();

    /**
     * Enum representing the type of monitoring station.
     */
    public enum StationType {
        AIR_QUALITY,
        WATER_QUALITY,
        NOISE_POLLUTION,
        MULTI_PARAMETER
    }

    /**
     * Enum representing the status of a monitoring station.
     */
    public enum StationStatus {
        ACTIVE,
        INACTIVE,
        MAINTENANCE,
        DECOMMISSIONED
    }
}

