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

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String name;
    public int powerConsumption;
    public String source;

    /**
     * Basic constructor
     */
    private Activity() {
        // for object mappers
    }

    /**
     * Constructor with name and powerConsumption
     */
    public Activity(String name, int powerConsumption, String source) {
        this.name = name;
        this.powerConsumption = powerConsumption;
        this.source = source;
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
    public int getPowerConsumption() {
        return powerConsumption;
    }

    /**
     * Setter for the powerConsumption
     *
     * @param powerConsumption
     */
    public void setPowerConsumption(int powerConsumption) {
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
     * Equals method comparing to activities if the name and power consumption is the same they are treated as equal
     *
     * @param o
     * @return true if equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return powerConsumption == activity.powerConsumption && Objects.equals(name, activity.name);
    }

    /**
     * Hash code for activity object
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, powerConsumption);
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
                '}';
    }
}
