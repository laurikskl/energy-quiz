package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActivitySearchRequestTest {
    private ActivitySearchRequest activitySearchRequest;
    private String name;
    private Long powerConsumptionMin;
    private Long powerConsumptionMax;
    private String source;

    /**
     * Set up objects needed for the tests
     */
    @BeforeEach
    void initialize() {
        this.name = "name";
        this.powerConsumptionMin = 1l;
        this.powerConsumptionMax = 2l;
        this.source = "source";

        this.activitySearchRequest = new ActivitySearchRequest(
                this.name,
                this.powerConsumptionMin,
                this.powerConsumptionMax,
                this.source
        );

    }

    /**
     * Test getName
     */
    @Test
    void getName() {
        assertEquals(this.activitySearchRequest.getName(), this.name);
    }

    /**
     * Test setName
     */
    @Test
    void setName() {
        String name2 = "name2";
        this.activitySearchRequest.setName(name2);
        assertEquals(this.activitySearchRequest.getName(), name2);
    }

    /**
     * Test getPowerConsumptionMin
     */
    @Test
    void getPowerConsumptionMin() {
        assertEquals(this.activitySearchRequest.getPowerConsumptionMin(), this.powerConsumptionMin);
    }

    /**
     * Test setPowerConsumptionMin
     */
    @Test
    void setPowerConsumptionMin() {
        Long powerConsumptionMin2 = 3l;
        this.activitySearchRequest.setPowerConsumptionMin(powerConsumptionMin2);
        assertEquals(this.activitySearchRequest.getPowerConsumptionMin(), powerConsumptionMin2);
    }

    /**
     * Test getPowerConsumptionMax
     */
    @Test
    void getPowerConsumptionMax() {
        assertEquals(this.activitySearchRequest.getPowerConsumptionMax(), this.powerConsumptionMax);
    }

    /**
     * Test setPowerConsumptionMax
     */
    @Test
    void setPowerConsumptionMax() {
        Long powerConsumptionMax2 = 4l;
        this.activitySearchRequest.setPowerConsumptionMax(powerConsumptionMax2);
        assertEquals(this.activitySearchRequest.getPowerConsumptionMax(), powerConsumptionMax2);
    }

    /**
     * Test getSource
     */
    @Test
    void getSource() {
        assertEquals(this.activitySearchRequest.getSource(), this.source);
    }

    /**
     * Test setSource
     */
    @Test
    void setSource() {
        String source2 = "source2";
        this.activitySearchRequest.setSource(source2);
        assertEquals(this.activitySearchRequest.getSource(), source2);
    }

    /**
     * Test equals
     */
    @Test
    void testEquals() {
        ActivitySearchRequest activitySearchRequest2 = new ActivitySearchRequest(
                this.name,
                this.powerConsumptionMin,
                this.powerConsumptionMax,
                this.source
        );

        assertEquals(activitySearchRequest2, this.activitySearchRequest);
    }

    /**
     * Test toString
     */
    @Test
    void testToString() {
        String string = "ActivitySearchRequest{" +
                "name='" + this.name + '\'' +
                ", powerConsumptionMin=" + this.powerConsumptionMin +
                ", powerConsumptionMax=" + this.powerConsumptionMax +
                ", source='" + this.source + '\'' +
                '}';

        assertEquals(this.activitySearchRequest.toString(), string);
    }
}
