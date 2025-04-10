package com.adeprogramming.javagis.repository.environmental;

import com.adeprogramming.javagis.model.environmental.MonitoringStation;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for MonitoringStation entities.
 * Provides methods for accessing and querying monitoring station data.
 */
@Repository
public interface MonitoringStationRepository extends JpaRepository<MonitoringStation, Long> {

    /**
     * Find a monitoring station by its unique station ID.
     *
     * @param stationId the station ID
     * @return an Optional containing the station if found
     */
    Optional<MonitoringStation> findByStationId(String stationId);

    /**
     * Find all monitoring stations of a specific type.
     *
     * @param stationType the type of station
     * @return a list of monitoring stations
     */
    List<MonitoringStation> findByStationType(MonitoringStation.StationType stationType);

    /**
     * Find all monitoring stations with a specific status.
     *
     * @param status the status of the station
     * @return a list of monitoring stations
     */
    List<MonitoringStation> findByStatus(MonitoringStation.StationStatus status);

    /**
     * Find all monitoring stations within a specified distance from a point.
     *
     * @param geometry the point geometry
     * @param distance the distance in meters
     * @return a list of monitoring stations
     */
    @Query(value = "SELECT ms FROM MonitoringStation ms WHERE ST_DWithin(ms.geometry, :geometry, :distance)")
    List<MonitoringStation> findWithinDistance(@Param("geometry") Geometry geometry, @Param("distance") double distance);

    /**
     * Find all monitoring stations within a specified geometry.
     *
     * @param geometry the geometry to search within
     * @return a list of monitoring stations
     */
    @Query(value = "SELECT ms FROM MonitoringStation ms WHERE ST_Within(ms.geometry, :geometry) = true")
    List<MonitoringStation> findWithinGeometry(@Param("geometry") Geometry geometry);
}
