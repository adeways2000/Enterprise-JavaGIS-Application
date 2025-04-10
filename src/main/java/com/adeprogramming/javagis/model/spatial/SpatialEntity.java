package com.adeprogramming.javagis.model.spatial;


import com.adeprogramming.javagis.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDateTime;

/**
 * Base entity class for all spatial entities.
 * Contains common fields for geospatial data.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class SpatialEntity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "geometry", columnDefinition = "geometry")
    private Geometry geometry;

    @Column(name = "srid")
    private Integer srid = 4326; // Default to WGS84

    @Column(name = "observation_date")
    private LocalDateTime observationDate;

    @Column(name = "source")
    private String source;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;
}