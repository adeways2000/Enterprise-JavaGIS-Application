package com.adeprogramming.javagis.stac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a STAC Asset object.
 * Assets are the actual data and metadata files that a STAC Item references.
 *
 * @see <a href="https://github.com/radiantearth/stac-spec/blob/master/item-spec/item-spec.md#asset-object">STAC Asset Object Specification</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StacAsset {

    /**
     * The URI of the asset.
     */
    private String href;

    /**
     * The title of the asset.
     */
    private String title;

    /**
     * A description of the asset.
     */
    private String description;

    /**
     * The media type of the asset.
     */
    private String type;

    /**
     * The role of the asset.
     */
    private String[] roles;

    /**
     * Additional fields not defined in the STAC specification.
     */
    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();
}
