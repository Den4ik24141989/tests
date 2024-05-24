package models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public final class CreatePhaseDTO {

    /**
     * Name phase custom.
     */
    @SerializedName("customNames")
    private List<String> customNames;

    /**
     * Name from phase directory.
     */
    @SerializedName("templateIds")
    private List<String> templateId;

    /**
     * Object constructor.
     */
    public CreatePhaseDTO() {
        this.templateId = new ArrayList<>();
        this.customNames = new ArrayList<>();
    }

    /**
     * Add name phase custom.
     * @param customName custom name
     */
    public void addCustomNames(final String customName) {
       customNames.add(customName);
    }

    /**
     * Add name from phase directory.
     * @param id template id from directory
     */
    public void addTemplate(final String id) {
        templateId.add(id);
    }


    /**
     * Get name phase.
     * @return name phase
     */
    public String getName() {
        return customNames.isEmpty()
                ? "Мобильное приложение" : customNames.get(0);
    }
}
