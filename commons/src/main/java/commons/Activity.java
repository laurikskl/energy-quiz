/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package commons;


import org.json.JSONObject;

import javax.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;

/**
 * Activity class
 */

@Entity
@Table
public class Activity implements Comparable {

    /**
     * All fields for activities
     */

    @Id
    @GeneratedValue
    private Long internalID;

    @Column
    public String id;

    @Column
    public String name;
    @Column
    public Long powerConsumption;
    @Column(length = 500)
    public String source;
    @Column
    @Lob
    public byte[] imageContent;


    /**
     * Basic constructor
     */

    public Activity() {
        // for object mappers
    }


    /**
     * Constructor with name, powerConsumption, source and imageContent
     *
     * @param id               id of activity
     * @param name             title of activity
     * @param powerConsumption consumption of activity in wh
     * @param source           source of info
     * @param imageContent     content of the image
     */
    public Activity(String id, String name, Long powerConsumption, String source, byte[] imageContent) {
        this.id = id;
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.source = source;
        this.imageContent = imageContent;
    }


    /**
     * JSON reader creating an Activity object from a JSON string
     *
     * @param jsonString
     * @return Activity from a JSON
     */

    public static Activity JSONActivityReader(String jsonString, String path) {
        JSONObject jsonObject = new JSONObject(jsonString);
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("title");
        Long powerConsumption = jsonObject.getLong("consumption_in_wh");
        String source = jsonObject.getString("source");

        Path imagePath = Path.of(path, (jsonObject.getString("image_path")));

        byte[] imageContent = null;

        if (Files.exists(imagePath)){
            try {
                imageContent = Files.readAllBytes(imagePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Activity(id, name, powerConsumption, source, imageContent);
    }


    /**
     * Getter for Id
     *
     * @return id
     */
    public String getId() {
        return id;
    }


    /**
     * Setter for id
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }


    /**
     * Getter for name
     *
     * @return name
     */
    public String getName() {
        return name;
    }


    /**
     * Setter for the name
     *
     * @param name of activity
     */

    public void setName(String name) {
        this.name = name;
    }


    /**
     * Getter for powerConsumption
     *
     * @return powerConsumption
     */
    public Long getPowerConsumption() {
        return powerConsumption;
    }


    /**
     * Setter for the powerConsumption
     *
     * @param powerConsumption of activity
     */
    public void setPowerConsumption(Long powerConsumption) {
        this.powerConsumption = powerConsumption;
    }


    /**
     * Getter for the source
     *
     * @return source
     */
    public String getSource() {
        return source;
    }


    /**
     * Setter for the source
     *
     * @param source of activity
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


    /**
     * @param obj
     * @return if this equals obj
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Activity activity = (Activity) obj;
        return Objects.equals(id, activity.id) && Objects.equals(name, activity.name) && Objects.equals(powerConsumption, activity.powerConsumption) && Objects.equals(source, activity.source) && Arrays.equals(imageContent, activity.imageContent);
    }

    /**
     * @return hashCode of the object
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, powerConsumption, source);
        result = 31 * result + Arrays.hashCode(imageContent);
        return result;
    }

    /**
     * To string method listing all parameters of activity except image content
     *
     * @return stringified activity
     */
    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", powerConsumption=" + powerConsumption +
                ", source='" + source + '\'' +
                '}';
    }


    /**
     * Basic compare method based on energy consumption
     *
     * @param o other object
     * @return 0 if consumption is equal to that of o,
     * 1 if consumption is lower than of o,
     * -1 if consumption is higher than of o
     */
    @Override
    public int compareTo(Object o) {
        Activity that = (Activity) o;
        if (that.getPowerConsumption() == this.getPowerConsumption()) {
            return 0;
        }
        if (getPowerConsumption() < that.getPowerConsumption()) {
            return 1;
        }
        return -1;
    }

}
