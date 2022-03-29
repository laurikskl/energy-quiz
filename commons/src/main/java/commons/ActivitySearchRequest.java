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
    private byte[] imageContent;

    /**
     * @param name
     * @param powerConsumptionMin
     * @param powerConsumptionMax
     * @param source
     * @param imageContent
     */
    public ActivitySearchRequest(String name, Long powerConsumptionMin, Long powerConsumptionMax, String source, byte[] imageContent) {
        this.name = name;
        this.powerConsumptionMin = powerConsumptionMin;
        this.powerConsumptionMax = powerConsumptionMax;
        this.source = source;
        this.imageContent = imageContent;
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
     * Getter for image content
     *
     * @return
     */
    public byte[] getImageContent() {
        return imageContent;
    }

    /**
     * Setter for image content
     *
     * @param imageContent
     */
    public void setImageContent(byte[] imageContent) {
        this.imageContent = imageContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivitySearchRequest that = (ActivitySearchRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(powerConsumptionMin, that.powerConsumptionMin) && Objects.equals(powerConsumptionMax, that.powerConsumptionMax) && Objects.equals(source, that.source) && Objects.equals(imageContent, that.imageContent);
    }

    @Override
    public String toString() {
        return "ActivitySearchRequest{" +
                "name='" + name + '\'' +
                ", powerConsumptionMin=" + powerConsumptionMin +
                ", powerConsumptionMax=" + powerConsumptionMax +
                ", source='" + source + '\'' +
                ", imageContent='" + imageContent + '\'' +
                '}';
    }
}
