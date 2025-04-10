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
 * Represents a STAC Collection object.
 * A STAC Collection is a group of STAC Items that share common metadata and properties.
 *
 * @see <a href="https://github.com/radiantearth/stac-spec/blob/master/collection-spec/collection-spec.md">STAC Collection Specification</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StacCollection {

    /**
     * The STAC version the collection implements.
     */
    private String stac_version;

    /**
     * The ID of the collection.
     */
    private String id;

    /**
     * The title of the collection.
     */
    private String title;

    /**
     * A description of the collection.
     */
    private String description;

    /**
     * The type of the STAC object (always "Collection" for collections).
     */
    private String type;

    /**
     * Links to related STAC objects and resources.
     */
    @Builder.Default
    private List<StacLink> links = new ArrayList<>();

    /**
     * The license(s) under which the data in this collection is published.
     */
    private String license;

    /**
     * The providers of the data in this collection.
     */
    @Builder.Default
    private List<StacProvider> providers = new ArrayList<>();

    /**
     * The spatial extent of the collection.
     */
    private StacExtent extent;

    /**
     * Additional fields not defined in the STAC specification.
     */
    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();
}
