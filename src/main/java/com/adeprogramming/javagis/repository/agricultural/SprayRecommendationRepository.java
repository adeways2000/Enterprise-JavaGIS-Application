package com.adeprogramming.javagis.repository.agricultural;

import com.adeprogramming.javagis.model.agricultural.AgriculturalField;
import com.adeprogramming.javagis.model.agricultural.SprayRecommendation;
import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for SprayRecommendation entities.
 * Provides methods for accessing and querying spray recommendation data.
 */
@Repository
public interface SprayRecommendationRepository extends JpaRepository<SprayRecommendation, Long> {

    /**
     * Find all spray recommendations for a specific agricultural field.
     *
     * @param field the agricultural field
     * @return a list of spray recommendations
     */
    List<SprayRecommendation> findByField(AgriculturalField field);

    /**
     * Find all spray recommendations for a specific agricultural field by field ID.
     *
     * @param fieldId the ID of the agricultural field
     * @return a list of spray recommendations
     */
    @Query("SELECT sr FROM SprayRecommendation sr WHERE sr.field.id = :fieldId")
    List<SprayRecommendation> findByFieldId(@Param("fieldId") Long fieldId);

    /**
     * Find all spray recommendations for a specific product.
     *
     * @param productName the name of the product
     * @return a list of spray recommendations
     */
    List<SprayRecommendation> findByProductName(String productName);

    /**
     * Find all spray recommendations with a specific status.
     *
     * @param status the status of the recommendation
     * @return a list of spray recommendations
     */
    List<SprayRecommendation> findByStatus(SprayRecommendation.RecommendationStatus status);

    /**
     * Find all spray recommendations for a specific date.
     *
     * @param recommendedDate the recommended date
     * @return a list of spray recommendations
     */
    List<SprayRecommendation> findByRecommendedDate(LocalDate recommendedDate);

    /**
     * Find all spray recommendations for a date range.
     *
     * @param startDate the start date
     * @param endDate the end date
     * @return a list of spray recommendations
     */
    List<SprayRecommendation> findByRecommendedDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Find all spray recommendations that are valid on a specific date.
     *
     * @param date the date to check
     * @return a list of spray recommendations
     */
    @Query("SELECT sr FROM SprayRecommendation sr WHERE sr.recommendedDate <= :date AND (sr.validUntil IS NULL OR sr.validUntil >= :date)")
    List<SprayRecommendation> findValidOnDate(@Param("date") LocalDate date);

    /**
     * Find all spray recommendations where the target area intersects with a specified geometry.
     *
     * @param geometry the geometry to check for intersection
     * @return a list of spray recommendations
     */
    @Query(value = "SELECT sr FROM SprayRecommendation sr WHERE ST_Intersects(sr.targetArea, :geometry) = true")
    List<SprayRecommendation> findIntersectingGeometry(@Param("geometry") Geometry geometry);

    /**
     * Find all spray recommendations created by a specific user.
     *
     * @param createdBy the username of the creator
     * @return a list of spray recommendations
     */
    List<SprayRecommendation> findByCreatedBy(String createdBy);

    /**
     * Find all spray recommendations approved by a specific user.
     *
     * @param approvedBy the username of the approver
     * @return a list of spray recommendations
     */
    List<SprayRecommendation> findByApprovedBy(String approvedBy);
}
