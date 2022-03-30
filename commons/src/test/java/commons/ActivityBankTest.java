package commons;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActivityBankTest {

    //Temporary directory
    @TempDir
    private static Path tempDir;

    //fields
    private ActivityBank activityBank;
    private String json;
    private Path jsonFile;
    private List<Activity> activities;

    /**
     * Setup before each test
     */
    @BeforeEach
    void setup() {
        this.jsonFile = tempDir.resolve("json.json");
        this.json = "[\n" +
                "    {\n" +
                "        \"id\": \"38-hairdryer\",\n" +
                "        \"image_path\": \"38/hairdryer.png\",\n" +
                "        \"title\": \"Using a hairdryer for an hour\",\n" +
                "        \"consumption_in_wh\": 1200,\n" +
                "        \"source\": \"https://blog.arcadia.com/electricity-costs-10-key-household-products/#:~:text=Hair%20Dryer%20Electricity%20Costs&text=Since%20it%20takes%201200%20watts,to%20run%20for%2030%20minutes.\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"38-leafblower\",\n" +
                "        \"image_path\": \"38/leafblower.png\",\n" +
                "        \"title\": \"Using a leafblower for 15 minutes\",\n" +
                "        \"consumption_in_wh\": 183,\n" +
                "        \"source\": \"https://www.kompulsa.com/how-much-power-are-your-appliances-consuming/#power_consumption_of_leaf_blowers\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"38-coffee\",\n" +
                "        \"image_path\": \"38/coffee.png\",\n" +
                "        \"title\": \"Making a hot cup of coffee\",\n" +
                "        \"consumption_in_wh\": 300,\n" +
                "        \"source\": \"https://business.directenergy.com/blog/2017/september/international-coffee-day#:~:text=Depending%20on%20the%20model%2C%20it,an%20automated%20drip%20coffee%20maker.\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": \"38-lcd_tv\",\n" +
                "        \"image_path\": \"38/lcd_tv.jpeg\",\n" +
                "        \"title\": \"Watching a 42 inch LCD TV for an hour\",\n" +
                "        \"consumption_in_wh\": 120,\n" +
                "        \"source\": \"https://www.thehomehacksdiy.com/how-much-electricity-power-does-a-tv-use/\"\n" +
                "    }\n" +
                "]";

        try {
            Files.write(this.jsonFile, this.json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.activities = new ArrayList<>();
        this.activities.add(new Activity("38-hairdryer", "Using a hairdryer for an hour", 1200l, "https://blog.arcadia.com/electricity-costs-10-key-household-products/#:~:text=Hair%20Dryer%20Electricity%20Costs&text=Since%20it%20takes%201200%20watts,to%20run%20for%2030%20minutes.", null));
        this.activities.add(new Activity("38-leafblower", "Using a leafblower for 15 minutes", 183l, "https://www.kompulsa.com/how-much-power-are-your-appliances-consuming/#power_consumption_of_leaf_blowers", null));
        this.activities.add(new Activity("38-coffee", "Making a hot cup of coffee", 300l, "https://business.directenergy.com/blog/2017/september/international-coffee-day#:~:text=Depending%20on%20the%20model%2C%20it,an%20automated%20drip%20coffee%20maker.", null));
        this.activities.add(new Activity("38-lcd_tv", "Watching a 42 inch LCD TV for an hour", 120l, "https://www.thehomehacksdiy.com/how-much-electricity-power-does-a-tv-use/", null));

        this.activityBank = new ActivityBank(activities, true);
    }

    /**
     * Test jsonReader
     * @throws IOException
     */
    @Test
    void jsonReader() throws IOException {
        ActivityBank newActivityBank = ActivityBank.JsonReader(this.jsonFile);
        newActivityBank.setOverride(true);

        assertTrue(this.activityBank.equals(newActivityBank));
    }

    /**
     * Test getActivities
     */
    @Test
    void getActivities() {
        assertEquals(this.activities, this.activityBank.getActivities());
    }

    /**
     * Test setActivities
     */
    @Test
    void setActivities() {
        List<Activity> newActivities = new ArrayList<>();
        newActivities.add(new Activity("00-coding", "Coding for an hour", 690l, "https://blog.arcadia.com/electricity-costs-10-key-household-products/#:~:text=Hair%20Dryer%20Electricity%20Costs&text=Since%20it%20takes%201200%20watts,to%20run%20for%2030%20minutes.", null));
        ActivityBank newActivityBank = new ActivityBank();
        newActivityBank.setActivities(newActivities);
        assertEquals(newActivities, newActivityBank.getActivities());
    }

    /**
     * Test getOverride
     */
    @Test
    void getOverride() {
        assertEquals(true, this.activityBank.getOverride());
    }

    /**
     * Test setOverride
     */
    @Test
    void setOverride() {
        this.activityBank.setOverride(false);
        assertEquals(false, this.activityBank.getOverride());
    }
}