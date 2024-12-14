package edu.ncsu.csc216.wolf_tracker.model.log;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tracker.model.task.Task;

/**
 * Tests the functionality of the AbstractTaskLog class.
 */
class AbstractTaskLogTest {

	/**
	 * Tests the AbstractTaskLog.removeTask() method and getTasksAsArray() 
	 */
	@Test
	public void testRemoveTask() { 
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");    
		Task t1 = new Task("Created CRC", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   
		CategoryLog category = new CategoryLog("Design"); 
		//t.addCategory(category);  
		category.addTask(t); 
		category.addTask(t1);   
		assertEquals(2, category.getTaskCount()); 
		category.removeTask(0); 
		assertEquals(1, category.getTaskCount()); 
		String[][] tasks = category.getTasksAsArray(); 
		assertEquals(tasks[0][0], t1.getTaskTitle() ); 
		assertEquals(tasks[0][1], String.valueOf(t1.getTaskDuration()));
		assertEquals(tasks[0][2], t1.getCategoryName() );
		assertEquals(category.getTask(0), t1); 
	}
	
	/**
	 * Testing min, max and avg durations
	 */
	@Test 
	public void testDuration() {
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");    
		Task t1 = new Task("Created CRC", 23, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   
		Task t2 = new Task("Created CRC", 30, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   
		CategoryLog category = new CategoryLog("Design");  
		category.addTask(t); 
		category.addTask(t1);
		category.addTask(t2);
		assertEquals(3, category.getTasks().size()); 
		assertEquals(23, category.getMinDuration()); 
		assertEquals(30, category.getMaxDuration()); 
		assertEquals(26.7, category.getAvgDuration()); 
	}
	
	/**
	 * Test AbstractTaskLog.toString()
	 */
	@Test
	public void testToString() {
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");    
		Task t1 = new Task("Created CRC", 23, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   
		Task t2 = new Task("Created CRC", 30, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   
		CategoryLog category = new CategoryLog("Design");  
		category.addTask(t); 
		category.addTask(t1);
		category.addTask(t2); 
		String summary = "Design,3,23,30,26.7"; 
		assertEquals(summary, category.toString()); 
	}
	
	/**
	 * Tests setTask() and setTaskLogName() 
	 */
	@Test 
	public void setTaskandSetTaskLogName() {
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");    
		Task t1 = new Task("Created CRC", 23, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   
		Task t2 = new Task("Created CRC", 30, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   
		CategoryLog category = new CategoryLog("Design");  
		category.addTask(t); 
		category.addTask(t1);
		category.addTask(t2);  
		category.setTask(0, t1); 
		assertEquals(category.getTask(0), t1); 
		
		category.setTaskLogName("Project"); 
		assertEquals(category.getName(), "Project"); 
	}

}
