package com.adeprogramming.javagis.stac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a STAC Link object.
 * Links are used to connect STAC objects to related resources.
 *
 * @see <a href="https://github.com/radiantearth/stac-spec/blob/master/item-spec/item-spec.md#link-object">STAC Link Object Specification</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StacLink {

    /**
     * The URI of the linked resource.
     */
    private String href;

    /**
     * The relationship between the current document and the linked resource.
     */
    private String rel;

    /**
     * The media type of the linked resource.
     */
    private String type;

    /**
     * The title of the linked resource.
     */
    private String title;
}
