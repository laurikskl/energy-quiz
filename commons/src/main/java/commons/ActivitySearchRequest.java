package commons;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class to manage activity search requests
 */
public class ActivitySearchRequest implements Serializable {
    //fields
    private String id;
    private String name;
    private Long powerConsumptionMin;
    private Long powerConsumptionMax;
    private String source;

    /**
     * @param name
     * @param powerConsumptionMin
     * @param powerConsumptionMax
     * @param source
     */
    public ActivitySearchRequest(String id, String name, Long powerConsumptionMin, Long powerConsumptionMax, String source) {
        this.id = id;
        this.name = name;
        this.powerConsumptionMin = powerConsumptionMin;
        this.powerConsumptionMax = powerConsumptionMax;
        this.source = source;
    }

    /**
     * default constructor for serialization
     */
    public ActivitySearchRequest() {

    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return powerConsumptionMin
     */
    public Long getPowerConsumptionMin() {
        return powerConsumptionMin;
    }

    /**
     * @param powerConsumptionMin
     */
    public void setPowerConsumptionMin(Long powerConsumptionMin) {
        this.powerConsumptionMin = powerConsumptionMin;
    }

    /**
     * @return powerConsumptionMax
     */
    public Long getPowerConsumptionMax() {
        return powerConsumptionMax;
    }

    /**
     * @param powerConsumptionMax
     */
    public void setPowerConsumptionMax(Long powerConsumptionMax) {
        this.powerConsumptionMax = powerConsumptionMax;
    }

    /**
     * @return source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @param obj
     * @return if this equals obj
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ActivitySearchRequest that = (ActivitySearchRequest) obj;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(powerConsumptionMin, that.powerConsumptionMin) && Objects.equals(powerConsumptionMax, that.powerConsumptionMax) && Objects.equals(source, that.source);
    }

    /**
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "ActivitySearchRequest{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", powerConsumptionMin=" + powerConsumptionMin +
                ", powerConsumptionMax=" + powerConsumptionMax +
                ", source='" + source + '\'' +
                '}';
    }
}
