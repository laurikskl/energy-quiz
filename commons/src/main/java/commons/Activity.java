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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table
public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;

  @Column
  public String name;
  @Column
  public Long powerConsumption;
  @Column(length = 500)
  public String source;
  @Column
  public String imagePath;

  /**
   * Basic constructor
   */
  public Activity() {
    // for object mappers
  }

  /**
   * Constructor with name, powerConsumption, source and imagePath
   *
   * @param name
   * @param powerConsumption
   * @param source
   * @param imagePath
   */
  public Activity(String name, long powerConsumption, String source, String imagePath) {
    this.name = name;
    this.powerConsumption = powerConsumption;
    this.source = source;
    this.imagePath = imagePath;
  }

  /**
   * Constructor with name, power consupmtion and source
   *
   * @param name
   * @param powerConsumption
   * @param source
   */
  public Activity(String name, long powerConsumption, String source) {
    this.name = name;
    this.powerConsumption = powerConsumption;
    this.source = source;
  }

  /**
   * JSON reader creating an Activity object from a JSON string
   *
   * @param jsonString
   * @return Activity from a JSON
   */
  public static Activity JSONActivityReader(String jsonString) {
    JSONObject jsonObject = new JSONObject(jsonString);
    String name = jsonObject.getString("title");
    long powerConsumption = jsonObject.getLong("consumption_in_wh");
    String source = jsonObject.getString("source");

    return new Activity(name, powerConsumption, source);
  }

  /**
   * Getter for Id
   *
   * @return id
   */
  public long getId() {
    return id;
  }

  /**
   * Setter for id
   *
   * @param id
   */
  public void setId(long id) {
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
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Getter for powerConsumption
   *
   * @return powerConsumption
   */
  public long getPowerConsumption() {
    return powerConsumption;
  }

  /**
   * Setter for the powerConsumption
   *
   * @param powerConsumption
   */
  public void setPowerConsumption(long powerConsumption) {
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
   * @param source
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * Getter fot the image path
   *
   * @return imagePath
   */
  public String getImagePath() {
    return imagePath;
  }

  /**
   * Setter for the image path
   *
   * @param imagePath
   */
  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  /**
   * Equals method comparing all fields except id of the activity class
   *
   * @param o
   * @return If the fields match the activities are treated as equal and the method returns true
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Activity activity = (Activity) o;
    return Objects.equals(name, activity.name)
        && Objects.equals(powerConsumption, activity.powerConsumption)
        && Objects.equals(source, activity.source)
        && Objects.equals(imagePath, activity.imagePath);
  }

  /**
   * Hash method creating hash from name, powerConsumption, source and image path
   *
   * @return
   */
  @Override
  public int hashCode() {
    return Objects.hash(name, powerConsumption, source, imagePath);
  }

  /**
   * To string method listing all parameters of activity
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
        ", imagePath='" + imagePath + '\'' +
        '}';
  }
}
