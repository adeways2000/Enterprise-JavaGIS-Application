package com.adeprogramming.javagis.model.agricultural;

import com.adeprogramming.javagis.model.spatial.SpatialEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Entity representing an agricultural field.
 * Used for tracking field boundaries, crop information, and spray recommendations.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agricultural_fields")
public class AgriculturalField extends SpatialEntity {

    @Column(name = "field_id", nullable = false, unique = true)
    private String fieldId;

    @Column(name = "area_hectares")
    private Double areaHectares;

    @Column(name = "soil_type")
    private String soilType;

    @Column(name = "current_crop")
    private String currentCrop;

    @Column(name = "planting_date")
    private LocalDate plantingDate;

    @Column(name = "expected_harvest_date")
    private LocalDate expectedHarvestDate;

    @Column(name = "last_spray_date")
    private LocalDate lastSprayDate;

    @Column(name = "last_spray_product")
    private String lastSprayProduct;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "contact_information")
    private String contactInformation;

    @Column(name = "notes")
    private String notes;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FieldStatus status = FieldStatus.ACTIVE;

    /**
     * Enum representing the status of an agricultural field.
     */
    public enum FieldStatus {
        ACTIVE,
        FALLOW,
        PREPARATION,
        HARVESTED,
        INACTIVE
    }
}

