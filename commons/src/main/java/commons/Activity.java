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

/**
 * Temporary comment for checkstyle.
 */
@Entity
public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  public long id;

  public String name;
  public int powerConsumption;


  private Activity() {
    // for object mappers
  }


  public Activity(String name, int powerConsumption) {
    this.name = name;
    this.powerConsumption = powerConsumption;
  }


  public long getId() {
    return id;
  }


  public String getName() {
    return name;
  }


  public int getPowerConsumption() {
    return powerConsumption;
  }


  public void setId(long id) {
    this.id = id;
  }


  public void setName(String name) {
    this.name = name;
  }


  public void setPowerConsumption(int powerConsumption) {
    this.powerConsumption = powerConsumption;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Activity activity = (Activity) o;
    return powerConsumption == activity.powerConsumption && Objects.equals(name, activity.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, powerConsumption);
  }

  @Override
  public String toString() {
    return "Activity{"
        + "id=" + id
        + ", name='" + name + '\''
        + ", powerConsumption=" + powerConsumption
        + '}';
  }
}
