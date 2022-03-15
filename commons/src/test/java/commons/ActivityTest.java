package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ActivityTest {

  Activity activity;
  Activity activityChanged;

  /**
   * Initializing activity and activityChange for the rest of the tests.
   */
  @BeforeEach
  public void initialize() {
    activity = new Activity("Cycling", 420);
    activityChanged = new Activity("Cycling", 420);
  }

  /**
   * Testing if the constructor worked.
   */
  @Test
  public void constructorTest() {
    assertNotNull(activity);
  }

  /**
   * Testing if the id of the 1st activity is 0.
   */
  @Test
  public void getIdTest1() {
    assertEquals(0, activity.getId());
  }

  /**
   * Testing getter for the name.
   */
  @Test
  public void getNameTest() {
    assertEquals("Cycling", activity.getName());
  }

  /**
   * Testing getter for the power consumption.
   */
  @Test
  public void getPowerConsumptionTest() {
    assertEquals(420, activity.getPowerConsumption());
  }

  /**
   * Testing if setting ID to 2 works.
   */
  @Test
  public void setIdTest() {
    activityChanged.setId(2);
    assertEquals(2, activityChanged.getId());
  }

  /**
   * Testing if setting name to "Biking" works.
   */
  @Test
  public void setNameTest() {
    activityChanged.setName("Biking");
    assertEquals("Biking", activityChanged.getName());
  }

  /**
   * Testing if setting power consumption to 666 works.
   */
  @Test
  public void setPowerConsumptionTest() {
    activityChanged.setPowerConsumption(666);
    assertEquals(666, activityChanged.getPowerConsumption());
  }

  /**
   * Testing if equals method works for the same reference.
   */
  @Test
  public void equalsSameObjectTest() {
    assertTrue(activity.equals(activity));
  }

  /**
   * Testing if equals method works when comparing with null.
   */
  @Test
  public void equalsNullTest() {
    assertFalse(activity.equals(null));
  }

  /**
   * Testing if equals method works when id is.
   */
  @Test
  public void equalsTest() {
    Activity activitySame = new Activity("Cycling", 420);
    Activity activityDiff = new Activity("Biking", 420);
    assertTrue(activity.equals(activitySame));
    assertTrue(activitySame.equals(activity));
    assertFalse(activity.equals(activityDiff));
    assertFalse(activityDiff.equals(activity));
  }

  /**
   * Testing if toString method works.
   */
  @Test
  public void toStringTest() {
    assertEquals("Activity{id=" + 0 + ", name='Cycling', powerConsumption=" + 420 + '}'
        , activity.toString());
  }
}
