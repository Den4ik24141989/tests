package models;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public final class Phase {

    /**
     * Phase id.
     */
    @SerializedName("_id")
    private String id;

    /**
     * Phase name.
     */
    @SerializedName("name")
    private String name;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Phase phase = (Phase) o;
        return Objects.equals(id, phase.id) && Objects.equals(name, phase.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
