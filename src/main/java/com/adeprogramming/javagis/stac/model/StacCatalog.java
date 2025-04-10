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
 * Represents a STAC Catalog object.
 * A STAC Catalog is a collection of STAC Items or other STAC Catalogs.
 *
 * @see <a href="https://github.com/radiantearth/stac-spec/blob/master/catalog-spec/catalog-spec.md">STAC Catalog Specification</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StacCatalog {

    /**
     * The STAC version the catalog implements.
     */
    private String stac_version;

    /**
     * The ID of the catalog.
     */
    private String id;

    /**
     * The title of the catalog.
     */
    private String title;

    /**
     * A description of the catalog.
     */
    private String description;

    /**
     * The type of the STAC object (always "Catalog" for catalogs).
     */
    private String type;

    /**
     * Links to related STAC objects and resources.
     */
    @Builder.Default
    private List<StacLink> links = new ArrayList<>();

    /**
     * Additional fields not defined in the STAC specification.
     */
    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();
}
