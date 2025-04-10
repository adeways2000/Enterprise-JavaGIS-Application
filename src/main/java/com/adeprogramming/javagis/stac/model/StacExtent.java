package com.adeprogramming.javagis.stac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a STAC Extent object.
 * The Extent object describes the spatial and temporal extents of a Collection.
 *
 * @see <a href="https://github.com/radiantearth/stac-spec/blob/master/collection-spec/collection-spec.md#extent-object">STAC Extent Object Specification</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StacExtent {

    /**
     * The spatial extent of the collection.
     */
    private SpatialExtent spatial;

    /**
     * The temporal extent of the collection.
     */
    private TemporalExtent temporal;

    /**
     * Represents the spatial extent of a collection.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SpatialExtent {
        /**
         * The bounding box of the collection in the format [west, south, east, north].
         * For collections that cross the antimeridian, the first bounding box describes the region
         * from the antimeridian to the eastern-most boundary, and the second describes the region
         * from the western-most boundary to the antimeridian.
         */
        private double[][] bbox;
    }

    /**
     * Represents the temporal extent of a collection.
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TemporalExtent {
        /**
         * The temporal interval of the collection in the format [start, end].
         * Open-ended intervals can be represented by providing null for the start or end time.
         */
        private String[][] interval;
    }
}
