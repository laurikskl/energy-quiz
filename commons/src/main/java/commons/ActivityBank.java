package commons;

import org.json.JSONArray;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class to manage adding Activity Banks
 */
public class ActivityBank implements Serializable {

    //Fields
    private List<Activity> activities;
    private boolean override;

    /**
     * @param activities
     * @param override Should the current data in the database be deleted?
     */
    public ActivityBank(List<Activity> activities, boolean override) {
        this.activities = activities;
        this.override = override;
    }

    /**
     * Constructor for JSON reading
     */
    public ActivityBank(List<Activity> activities) {
        this.activities = activities;
    }

    /**
     * Sefault constructor for serialization
     */
    public ActivityBank() {
    }

    /**
     * Read an ActivityBank from a JSON String
     * @param path Path to read Json file from
     * @return new ActivityBank based on the JSON String
     * @throws IOException
     */
    public static ActivityBank fileReader(Path path) throws IOException {
        String jsonString = Files.readString(path);
        JSONArray jsonArray = new JSONArray(jsonString);

        List<Activity> activities = new ArrayList<>();
        jsonArray.forEach(obj -> {
            activities.add(Activity.JSONActivityReader(obj.toString(), path.getParent().toString()));
        });

        return new ActivityBank(activities);
    }

    /**
     * @return activities
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * @param activities
     */
    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    /**
     * @return override
     */
    public boolean getOverride() {
        return override;
    }

    /**
     * @param override
     */
    public void setOverride(boolean override) {
        this.override = override;
    }

    /**
     * @param obj
     * @return if this equals obj
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ActivityBank that = (ActivityBank) obj;
        return override == that.override && Objects.equals(activities, that.activities);
    }

    /**
     * @return string representation of this object
     */
    @Override
    public String toString() {
        return "ActivityBank{" +
                "activities=" + activities +
                ", override=" + override +
                '}';
    }
}
