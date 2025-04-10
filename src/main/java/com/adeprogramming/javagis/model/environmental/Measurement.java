package com.adeprogramming.javagis.model.environmental;

import com.adeprogramming.javagis.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing an environmental measurement from a monitoring station.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "measurements")
public class Measurement extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "station_id", nullable = false)
    private MonitoringStation station;

    @Column(name = "parameter_name", nullable = false)
    private String parameterName;

    @Column(name = "parameter_value", nullable = false)
    private Double parameterValue;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "measurement_time", nullable = false)
    private LocalDateTime measurementTime;

    @Column(name = "quality_flag")
    @Enumerated(EnumType.STRING)
    private QualityFlag qualityFlag = QualityFlag.VALID;

    @Column(name = "threshold_exceeded")
    private Boolean thresholdExceeded = false;

    @Column(name = "threshold_value")
    private Double thresholdValue;

    @Column(name = "notes")
    private String notes;

    /**
     * Enum representing the quality of a measurement.
     */
    public enum QualityFlag {
        VALID,
        SUSPECT,
        INVALID,
        MISSING,
        ESTIMATED
    }
}
