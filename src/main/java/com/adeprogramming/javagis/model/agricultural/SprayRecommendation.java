package com.adeprogramming.javagis.model.agricultural;

import com.adeprogramming.javagis.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Geometry;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a spray recommendation for an agricultural field.
 * Used for tracking spray recommendations based on weather and geo-spatial data.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spray_recommendations")


public class SprayRecommendation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "field_id", nullable = false)
    private AgriculturalField field;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "application_rate", nullable = false)
    private Double applicationRate;

    @Column(name = "application_rate_unit", nullable = false)
    private String applicationRateUnit;

    @Column(name = "recommended_date", nullable = false)
    private LocalDate recommendedDate;

    @Column(name = "valid_until")
    private LocalDate validUntil;

    @Column(name = "weather_conditions")
    private String weatherConditions;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "wind_direction")
    private String windDirection;

    @Column(name = "temperature")
    private Double temperature;

    @Column(name = "humidity")
    private Double humidity;

    @Column(name = "precipitation_risk")
    private Double precipitationRisk;

    @Column(name = "target_area", columnDefinition = "geometry")
    private Geometry targetArea;

    @Column(name = "buffer_zone_size")
    private Double bufferZoneSize;

    @Column(name = "buffer_zone_unit")
    private String bufferZoneUnit;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RecommendationStatus status = RecommendationStatus.PENDING;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    /**
     * Enum representing the status of a spray recommendation.
     */
    public enum RecommendationStatus {
        PENDING,
        APPROVED,
        REJECTED,
        COMPLETED,
        EXPIRED
    }
}
