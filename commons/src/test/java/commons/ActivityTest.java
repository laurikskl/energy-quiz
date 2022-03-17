package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    Activity activity;
    Activity activityChanged;
    String json1;
    String json2;

    /**
     * Initializing activity and activityChange for the rest of the tests
     */
    @BeforeEach
    public void initialize() {
        activity = new Activity("Cycling", 420, "randomURL1", "activities/00/tesla.jpg");
        activityChanged = new Activity("Cycling", 420, "randomURL2", "activities/01/tesla.jpg");
        json1 = "{\n" +
                "    \"title\" : \"Using a blender for one hour\",\n" +
                "    \"consumption_in_wh\" : 400,\n" +
                "    \"source\" : \"https://www.electricalclassroom.com/power-consume-energy-usage-of-blenders/" +
                "#:~:text=Power%20consumption%20of%20blenders,stator%20winding%20for%20speed%20control.\"\n" +
                "}";
        json2 = "{\n" +
                "    \"title\": \"Vacuuming your home for 30min\",\n" +
                "    \"consumption_in_wh\": 9000000000000,\n" +
                "    \"source\": \"https://www.philips.com.sg/c-p/FC9350_61/3000-series-bagless-vacuum-cleaner\"\n" +
                "}\n";
    }

    /**
     * Testing if the constructor worked
     */
    @Test
    public void constructorTest() {
        assertNotNull(activity);
    }

    /**
     * Testing if the id of the 1st activity is 0
     */
    @Test
    public void getIdTest1() {
        assertEquals(0, activity.getId());
    }

    /**
     * Testing getter for the name
     */
    @Test
    public void getNameTest() {
        assertEquals("Cycling", activity.getName());
    }

    /**
     * Testing getter for the power consumption
     */
    @Test
    public void getPowerConsumptionTest() {
        assertEquals(420, activity.getPowerConsumption());
    }

    /**
     * Testing getter for the source
     */
    @Test
    void getSourceTest() {
        assertEquals("randomURL1", activity.getSource());
    }

    /**
     * Testing getter for image path
     */
    @Test
    void getImagePathTest() {
        assertEquals("activities/00/tesla.jpg", activity.getImagePath());
    }

    /**
     * Testing if setting Id to 2 works
     */
    @Test
    public void setIdTest() {
        activityChanged.setId(2);
        assertEquals(2, activityChanged.getId());
    }

    /**
     * Testing if seting name to "Biking" works
     */
    @Test
    public void setNameTest() {
        activityChanged.setName("Biking");
        assertEquals("Biking", activityChanged.getName());
    }

    /**
     * Testing if setting power consumption to 666 works
     */
    @Test
    public void setPowerConsumptionTest() {
        activityChanged.setPowerConsumption(666);
        assertEquals(666, activityChanged.getPowerConsumption());
    }

    /**
     * Testing if setting source to 'randomURL' works
     */
    @Test
    void setSourceTest() {
        activityChanged.setSource("randomURL");
        assertEquals("randomURL", activityChanged.getSource());
    }

    @Test
    void setImagePathTest() {
        activityChanged.setImagePath("activities/17/tesla.jpg");
        assertEquals("activities/17/tesla.jpg", activityChanged.getImagePath());
    }

    /**
     * Testing if equals method works for the same reference
     */
    @Test
    public void equalsSameObjectTest() {
        assertTrue(activity.equals(activity));
    }

    /**
     * Testing if equals method works when comparing with null
     */
    @Test
    public void equalsNullTest() {
        assertFalse(activity.equals(null));
    }

    /**
     * Testing if equals method works
     */
    @Test
    public void equalsTest() {
        Activity activitySame = new Activity("Cycling", 420, "randomURL1", "activities/00/tesla.jpg");
        Activity activityDiff = new Activity("Biking", 420, "randomURL", "activities/00/tesla.jpg");
        assertTrue(activity.equals(activitySame));
        assertTrue(activitySame.equals(activity));
        assertFalse(activity.equals(activityDiff));
        assertFalse(activityDiff.equals(activity));
    }

    /**
     * Testing if the hash code is same for activities with equal fields
     */
    @Test
    void hashEqualTest() {
        Activity activitySame = new Activity("Cycling", 420, "randomURL1", "activities/00/tesla.jpg");
        assertEquals(activity.hashCode(), activitySame.hashCode());
    }

    /**
     * Testing if the hash code is different for activities with different fields
     */
    @Test
    void hashDifferentTest() {
        Activity activityDiff = new Activity("Biking", 420, "randomURL", "activities/00/tesla.jpg");
        assertNotEquals(activity.hashCode(), activityDiff.hashCode());
    }

    /**
     * Testing if toString method works
     */
    @Test
    public void toStringTest() {
        assertEquals("Activity{id=" + 0 + ", name='Cycling', powerConsumption=" + 420
                        + ", source='randomURL1', imagePath='activities/00/tesla.jpg'" + '}'
                , activity.toString());
    }

    /**
     * Testing the reader if it properly reads JSON. Example from the activities/01/blender.json with added spaces
     * before the ":"
     *
     * @throws FileNotFoundException
     */
    @Test
    void JSONActivityReaderTest1() throws FileNotFoundException {
        Activity readFromJson = Activity.JSONActivityReader(json1);
        assertEquals("Using a blender for one hour", readFromJson.getName());
        assertEquals(400, readFromJson.getPowerConsumption());
        assertEquals("https://www.electricalclassroom.com/power-consume-energy-usage-of-blenders/" +
                        "#:~:text=Power%20consumption%20of%20blenders,stator%20winding%20for%20speed%20control."
                , readFromJson.getSource());
    }

    /**
     * Testing the reader if it properly reads JSON. Example from the activities/00/vacuuming.json
     * with changed the power consumption to Long
     *
     * @throws FileNotFoundException
     */
    @Test
    void JSONActivityReaderTest2() throws FileNotFoundException {
        Activity readFromJson = Activity.JSONActivityReader(json2);
        assertEquals("Vacuuming your home for 30min", readFromJson.getName());
        assertEquals(9000000000000L, readFromJson.getPowerConsumption());
        assertEquals("https://www.philips.com.sg/c-p/FC9350_61/3000-series-bagless-vacuum-cleaner"
                , readFromJson.getSource());
    }
}
