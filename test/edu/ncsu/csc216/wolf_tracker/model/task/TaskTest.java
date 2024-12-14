package edu.ncsu.csc216.wolf_tracker.model.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tracker.model.log.CategoryLog;

/**
 * Tests the functionality of the Test class.
 */
class TaskTest {

	/**
	 * Tests the Task.get() methods for task construction 
	 */
	@Test
	public void testGet() {
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state."); 
		assertEquals(t.getTaskTitle(), "Created CRC Cards");
		assertEquals(t.getTaskDuration(), 27); 
		assertEquals(t.getTaskDetails(), "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state."); 
		
	}
	
	/**
	 * Tests the Task.set() methods for task construction 
	 */
	@Test 
	public void testSet() {
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");  
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> t.setTaskTitle(null));
	    assertEquals("Incomplete task information.", e1.getMessage());
	    
	    Exception e2 = assertThrows(IllegalArgumentException.class, () -> t.setTaskTitle(""));
	    assertEquals("Incomplete task information.", e2.getMessage());
	    
	    Exception e3 = assertThrows(IllegalArgumentException.class, () -> t.setTaskDuration(0));
	    assertEquals("Incomplete task information.", e3.getMessage());
	    
	    Exception e4 = assertThrows(IllegalArgumentException.class, () -> t.setTaskDetails(null));
	    assertEquals("Incomplete task information.", e4.getMessage());
	    
	    Exception e5 = assertThrows(IllegalArgumentException.class, () -> t.setTaskDetails(""));
	    assertEquals("Incomplete task information.", e5.getMessage());
        
		 
	}
	
	/**
	 * Tests Task.addCategory() and getCategoryName() 
	 */
	@Test 
	public void testAddCategoryandGetCategoryName() {
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   

		Exception e1 = assertThrows(IllegalArgumentException.class, () -> t.addCategory(null));
	    assertEquals("Incomplete task information.", e1.getMessage());
	    
	    CategoryLog category = new CategoryLog("Design"); 
	    t.addCategory(category); 
	    
	    assertEquals("Design", t.getCategoryName()); 
	    
	    Exception e2 = assertThrows(IllegalArgumentException.class, () -> t.addCategory(category));
	    assertEquals("Incomplete task information.", e2.getMessage());
	    
	    
	}
	
	/**
	 * Test Task.toString() 
	 */
	@Test 
	public void testToString() {
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");    
		String task = "* " + t.getTaskTitle() + "," + String.valueOf(t.getTaskDuration()) + "," + t.getCategoryName() + "\n" + t.getTaskDetails();  
		assertEquals(task, t.toString()); 
	}

}
