package com.adeprogramming.javagis.stac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a STAC Item object.
 * A STAC Item is a GeoJSON Feature with additional metadata properties and links.
 *
 * @see <a href="https://github.com/radiantearth/stac-spec/blob/master/item-spec/item-spec.md">STAC Item Specification</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StacItem {

    /**
     * The STAC version the item implements.
     */
    private String stac_version;

    /**
     * The ID of the item.
     */
    private String id;

    /**
     * The type of the GeoJSON object (always "Feature" for items).
     */
    private String type;

    /**
     * The geometry of the item.
     */
    private Map<String, Object> geometry;

    /**
     * The bounding box of the item in the format [west, south, east, north].
     */
    private double[] bbox;

    /**
     * Properties of the item.
     */
    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();

    /**
     * Links to related STAC objects and resources.
     */
    @Builder.Default
    private List<StacLink> links = new ArrayList<>();

    /**
     * Assets associated with the item.
     */
    @Builder.Default
    private Map<String, StacAsset> assets = new HashMap<>();

    /**
     * The collection ID this item belongs to.
     */
    private String collection;
}
