package com.adeprogramming.javagis.repository.environmental;

import com.adeprogramming.javagis.model.environmental.Measurement;

import com.adeprogramming.javagis.model.environmental.MonitoringStation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Measurement entities.
 * Provides methods for accessing and querying measurement data.
 */
@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    /**
     * Find all measurements for a specific monitoring station.
     *
     * @param station the monitoring station
     * @return a list of measurements
     */
    List<Measurement> findByStation(MonitoringStation station);

    /**
     * Find all measurements for a specific monitoring station by station ID.
     *
     * @param stationId the ID of the monitoring station
     * @return a list of measurements
     */
    @Query("SELECT m FROM Measurement m WHERE m.station.id = :stationId")
    List<Measurement> findByStationId(@Param("stationId") Long stationId);

    /**
     * Find all measurements for a specific parameter.
     *
     * @param parameterName the name of the parameter
     * @return a list of measurements
     */
    List<Measurement> findByParameterName(String parameterName);

    /**
     * Find all measurements taken between two dates.
     *
     * @param startTime the start date and time
     * @param endTime the end date and time
     * @return a list of measurements
     */
    List<Measurement> findByMeasurementTimeBetween(LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Find all measurements for a specific station and parameter.
     *
     * @param station the monitoring station
     * @param parameterName the name of the parameter
     * @return a list of measurements
     */
    List<Measurement> findByStationAndParameterName(MonitoringStation station, String parameterName);

    /**
     * Find all measurements for a specific station and parameter between two dates.
     *
     * @param station the monitoring station
     * @param parameterName the name of the parameter
     * @param startTime the start date and time
     * @param endTime the end date and time
     * @return a list of measurements
     */
    List<Measurement> findByStationAndParameterNameAndMeasurementTimeBetween(
            MonitoringStation station, String parameterName, LocalDateTime startTime, LocalDateTime endTime);

    /**
     * Find all measurements where the parameter value exceeds a threshold.
     *
     * @param parameterName the name of the parameter
     * @param thresholdValue the threshold value
     * @return a list of measurements
     */
    @Query("SELECT m FROM Measurement m WHERE m.parameterName = :parameterName AND m.parameterValue > :thresholdValue")
    List<Measurement> findExceedingThreshold(@Param("parameterName") String parameterName, @Param("thresholdValue") Double thresholdValue);

    /**
     * Find the latest measurement for each station.
     *
     * @return a list of the latest measurements
     */
    @Query("SELECT m FROM Measurement m WHERE m.measurementTime = (SELECT MAX(m2.measurementTime) FROM Measurement m2 WHERE m2.station = m.station)")
    List<Measurement> findLatestMeasurements();
}

