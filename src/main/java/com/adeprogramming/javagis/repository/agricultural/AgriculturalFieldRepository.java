package com.adeprogramming.javagis.repository.agricultural;

import com.adeprogramming.javagis.model.agricultural.AgriculturalField;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for AgriculturalField entities.
 * Provides methods for accessing and querying agricultural field data.
 */
@Repository
public interface AgriculturalFieldRepository extends JpaRepository<AgriculturalField, Long> {

    /**
     * Find an agricultural field by its unique field ID.
     *
     * @param fieldId the field ID
     * @return an Optional containing the field if found
     */
    Optional<AgriculturalField> findByFieldId(String fieldId);

    /**
     * Find all agricultural fields with a specific crop.
     *
     * @param crop the crop name
     * @return a list of agricultural fields
     */
    List<AgriculturalField> findByCurrentCrop(String crop);

    /**
     * Find all agricultural fields with a specific status.
     *
     * @param status the status of the field
     * @return a list of agricultural fields
     */
    List<AgriculturalField> findByStatus(AgriculturalField.FieldStatus status);

    /**
     * Find all agricultural fields owned by a specific person.
     *
     * @param ownerName the name of the owner
     * @return a list of agricultural fields
     */
    List<AgriculturalField> findByOwnerName(String ownerName);

    /**
     * Find all agricultural fields within a specified distance from a point.
     *
     * @param geometry the point geometry
     * @param distance the distance in meters
     * @return a list of agricultural fields
     */
    @Query(value = "SELECT af FROM AgriculturalField af WHERE ST_DWithin(af.geometry, :geometry, :distance)")
    List<AgriculturalField> findWithinDistance(@Param("geometry") Geometry geometry, @Param("distance") double distance);

    /**
     * Find all agricultural fields within a specified geometry.
     *
     * @param geometry the geometry to search within
     * @return a list of agricultural fields
     */
    @Query(value = "SELECT af FROM AgriculturalField af WHERE ST_Within(af.geometry, :geometry) = true")
    List<AgriculturalField> findWithinGeometry(@Param("geometry") Geometry geometry);

    /**
     * Find all agricultural fields that intersect with a specified geometry.
     *
     * @param geometry the geometry to check for intersection
     * @return a list of agricultural fields
     */
    @Query(value = "SELECT af FROM AgriculturalField af WHERE ST_Intersects(af.geometry, :geometry) = true")
    List<AgriculturalField> findIntersectingGeometry(@Param("geometry") Geometry geometry);

    /**
     * Find all agricultural fields with an area greater than the specified value.
     *
     * @param areaHectares the minimum area in hectares
     * @return a list of agricultural fields
     */
    List<AgriculturalField> findByAreaHectaresGreaterThan(Double areaHectares);
}