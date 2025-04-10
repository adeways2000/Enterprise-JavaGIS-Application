package com.adeprogramming.javagis.stac.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a STAC Provider object.
 * Providers include information about the producer and distributor of data.
 *
 * @see <a href="https://github.com/radiantearth/stac-spec/blob/master/collection-spec/collection-spec.md#provider-object">STAC Provider Object Specification</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StacProvider {

    /**
     * The name of the organization or individual.
     */
    private String name;

    /**
     * The URL associated with the provider.
     */
    private String url;

    /**
     * The roles of the provider.
     * Possible values: licensor, producer, processor, host
     */
    private String[] roles;

    /**
     * Additional information about the provider.
     */
    private String description;
}
