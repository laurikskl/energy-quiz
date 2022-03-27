package commons;

import java.io.Serializable;
import java.util.Objects;

/**
 * Class to manage activity search requests
 */
public class ActivitySearchRequest implements Serializable {
    //fields
    private String name;
    private Long powerConsumptionMin;
    private Long powerConsumptionMax;
    private String source;
    private String imagePath;

    /**
     * @param name
     * @param powerConsumptionMin
     * @param powerConsumptionMax
     * @param source
     * @param imagePath
     */
    public ActivitySearchRequest(String name, Long powerConsumptionMin, Long powerConsumptionMax, String source, String imagePath) {
        this.name = name;
        this.powerConsumptionMin = powerConsumptionMin;
        this.powerConsumptionMax = powerConsumptionMax;
        this.source = source;
        this.imagePath = imagePath;
    }

    /**
     * default constructor for serialization
     */
    public ActivitySearchRequest() {

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
     * @return imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivitySearchRequest that = (ActivitySearchRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(powerConsumptionMin, that.powerConsumptionMin) && Objects.equals(powerConsumptionMax, that.powerConsumptionMax) && Objects.equals(source, that.source) && Objects.equals(imagePath, that.imagePath);
    }

    @Override
    public String toString() {
        return "ActivitySearchRequest{" +
                "name='" + name + '\'' +
                ", powerConsumptionMin=" + powerConsumptionMin +
                ", powerConsumptionMax=" + powerConsumptionMax +
                ", source='" + source + '\'' +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
