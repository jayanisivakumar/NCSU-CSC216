package edu.ncsu.csc216.wolf_tracker.model.log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tracker.model.task.Task;

/**
 * Tests the CategoryLog class.
 */
public class CategoryLogTest {
	
	/**
	 * Tests the CategoryLog constructor.
	 */
	@Test
   void testCategoryLogConstructor() {
       CategoryLog log = new CategoryLog("Debugging");
       assertEquals("Debugging", log.getName());
       // Invalid name (null)
       Exception e = assertThrows(IllegalArgumentException.class, () -> new CategoryLog(null));
       assertEquals("Invalid name.", e.getMessage());
       // Invalid name (empty)
       Exception e2 = assertThrows(IllegalArgumentException.class, () -> new CategoryLog(""));
       assertEquals("Invalid name.", e2.getMessage());
   }
   /**
	 * Tests the compareTo().
	 */
   @Test
   void testCompareTo() {
   	   // different cases
       CategoryLog log1 = new CategoryLog("Debugging");
       CategoryLog log2 = new CategoryLog("debugging");
       assertEquals(0, log1.compareTo(log2));
      
       // different names
       CategoryLog log3 = new CategoryLog("Debugging");
       CategoryLog log4 = new CategoryLog("Design");
       assertTrue(log3.compareTo(log4) < 0);
       assertTrue(log4.compareTo(log3) > 0);
   }
	
	/**
	 * Tests the addTask().
	 */
   @Test
   void testAddTask() {
	   CategoryLog log = new CategoryLog("Debugging");
	   assertEquals("Debugging", log.getName());
       Task task = new Task("Fixed bug", 60, "Resolve issue in code");
      
       log.addTask(task);
       assertEquals(1, log.getTaskCount());
       assertEquals(task, log.getTask(0));
       // Adding a null task should throw an exception
       Exception e = assertThrows(NullPointerException.class, () -> log.addTask(null));
       assertEquals("Cannot set a null task.", e.getMessage());
   }
   /**
	 * Tests the setTask().
	 */
   @Test
   void testSetTask() {
	   CategoryLog log = new CategoryLog("Debugging");
	   assertEquals("Debugging", log.getName());
       Task task1 = new Task("Bug fixed", 60, "Resolved issue in code");
       Task task2 = new Task("Implement change", 120, "Added new method");
       log.addTask(task1);
       log.setTask(0, task2);
       assertEquals(1, log.getTaskCount());
       assertEquals(task2, log.getTask(0));
       // Index out of bounds case
       Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> log.setTask(2, task1));
       assertEquals("Invalid index.", e1.getMessage());
       // Null task case
       Exception e2 = assertThrows(NullPointerException.class, () -> log.setTask(0, null));
       assertEquals("Cannot set a null task.", e2.getMessage());
       // Index out of bounds case
       Exception e3 = assertThrows(IndexOutOfBoundsException.class, () -> log.setTask(-2, task1));
       assertEquals("Invalid index.", e3.getMessage());
   }

}
