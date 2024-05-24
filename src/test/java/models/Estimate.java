package models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public final class Estimate {

    /**
     * Estimate id.
     */
    @SerializedName("_id")
    private String id;

    /**
     * Estimate client.
     */
    @SerializedName("customer")
    private String client;

    /**
     * Estimate name.
     */
    @SerializedName("name")
    private String estimateName;

    /**
     * Estimate phases.
     */
    @SerializedName("phases")
    private List<Phase> phases;

    /**
     * Estimate linkToCrm.
     */
    @SerializedName("linkToCRM")
    private String linkToCrm;

    /**
     * Estimate description.
     */
    @SerializedName("description")
    private String description;
}
