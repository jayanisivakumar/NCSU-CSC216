package edu.ncsu.csc216.wolf_tracker.model.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tracker.model.io.ProjectReader;
import edu.ncsu.csc216.wolf_tracker.model.log.AllTasksLog;
import edu.ncsu.csc216.wolf_tracker.model.task.Task;

/**
 * Tests the functionality of the Project class.
 */
class ProjectTest {

	/**
	 * Tests the Project() and setProjectName() 
	 */
	@Test
	public void testProject() {
		Project p = new Project("Project 1"); 
		assertEquals("Project 1", p.getProjectName()); 
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> new Project(null));
	    assertEquals("Invalid name.", e1.getMessage());
	    
	    Exception e2 = assertThrows(IllegalArgumentException.class, () -> new Project(""));
	    assertEquals("Invalid name.", e2.getMessage());
	    
	    Exception e3 = assertThrows(IllegalArgumentException.class, () -> new Project(AllTasksLog.ALL_TASKS_NAME));
	    assertEquals("Invalid name.", e3.getMessage());
	}
	
//	/**
//	 * Tests the saveProject() and saveStats() 
//	 */
//	@Test
//	public void testSaveProject() {
//		//yet to test 
//		fail("Not yet implemented");
//	}
	
	/**
	 * Tests the addCategoryLog() 
	 */
	@Test
	public void testaddCategoryLog() {
		Project p = new Project("Project 1"); 
		p.addCategoryLog("Design"); 
		p.addCategoryLog("Testing"); 
		assertEquals("All Tasks", p.getCategoryNames()[0]); 
		assertEquals("Design", p.getCategoryNames()[1]); 
		assertEquals("Testing", p.getCategoryNames()[2]); 
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> p.addCategoryLog(null));
	    assertEquals("Invalid name.", e1.getMessage());
	    
	    Exception e2 = assertThrows(IllegalArgumentException.class, () -> p.addCategoryLog(""));
	    assertEquals("Invalid name.", e2.getMessage());
	    
	    Exception e3 = assertThrows(IllegalArgumentException.class, () -> p.addCategoryLog(AllTasksLog.ALL_TASKS_NAME));
	    assertEquals("Invalid name.", e3.getMessage());
	    
		assertTrue(p.isChanged()); 
	}
	
	/**
	 * Test addTask(), removeTask(), editTask(), and getMostRecentTasks() 
	 */
	@Test 
	public void testTaskMethods() {
		Project p = new Project("Project 1");  
		p.addCategoryLog("Design"); 
		p.addCategoryLog("Testing");
		p.setCurrentTaskLog("Design");  
		Task t = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");    
		Task t1 = new Task("UML Diagram", 23, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");   
		Task t2 = new Task("Created CRC", 30, "Identified the key classes and created CRC cards. Noted responsibilities, collaborators, and possible state.");    
		p.addTask(t);  
		p.addTask(t1); 
		p.addTask(t2);      
		assertEquals(p.getCurrentLog().getTaskCount(), 3);  
		p.editTask(0, "Changed Title", 12, "changed details"); 
		assertEquals(t.getTaskTitle(), "Changed Title"); 
		assertEquals(t.getTaskDuration(), 12); 
		assertEquals(t.getTaskDetails(), "changed details"); 
	
		p.removeTask(0); 
		assertEquals(p.getCurrentLog().getTaskCount(), 2); 
		assertEquals(p.getCurrentLog().getTask(0), t1); 
		assertEquals(p.getCurrentLog().getTask(1), t2);
		
		assertEquals(p.getMostRecentTasks()[0][0], t2.getTaskTitle()); 
		assertEquals(p.getMostRecentTasks()[0][1], "30");
		assertEquals(p.getMostRecentTasks()[0][2], t2.getCategoryName()); 
		assertEquals(p.getMostRecentTasks()[1][0], "None"); 
		assertEquals(p.getMostRecentTasks()[1][1], "");
		assertEquals(p.getMostRecentTasks()[1][2], "Testing"); 
		
	}
	
	/**
	 * Test addCategoryLog() and removeCategoryLog() and editCategoryLogName() 
	 */
	@Test
	public void testAddRemoveEditCategory() {
		Project p = new Project("Project 1");  
		p.addCategoryLog("Design"); 
		p.addCategoryLog("Testing");
		assertEquals(p.getCategoryNames().length, 3);
		
		Exception e1 = assertThrows(IllegalArgumentException.class, () -> p.editCategoryLogName(null));
	    assertEquals("Invalid name.", e1.getMessage());
	    
	    Exception e2 = assertThrows(IllegalArgumentException.class, () -> p.editCategoryLogName(""));
	    assertEquals("Invalid name.", e2.getMessage());
	    
	    Exception e3 = assertThrows(IllegalArgumentException.class, () -> p.editCategoryLogName(AllTasksLog.ALL_TASKS_NAME));
	    assertEquals("Invalid name.", e3.getMessage());
	    
	    Exception e4 = assertThrows(IllegalArgumentException.class, () -> p.editCategoryLogName("Testing"));
	    assertEquals("Invalid name.", e4.getMessage());
	    
		p.setCurrentTaskLog("Design"); 
		assertEquals(p.getCurrentLog().getName(), "Design"); 
		p.editCategoryLogName("My Design"); 
		assertEquals(p.getCurrentLog().getName(), "My Design");
		p.removeCategoryLog(); 
		assertEquals(p.getCategoryNames().length, 2);
		p.setCurrentTaskLog("System"); 
		assertEquals(p.getCurrentLog().getName(), "All Tasks"); 
		
	    Exception e5 = assertThrows(IllegalArgumentException.class, () -> p.editCategoryLogName("Design"));
		assertEquals("The All Tasks log may not be edited.", e5.getMessage());
		
		Exception e6 = assertThrows(IllegalArgumentException.class, () -> p.removeCategoryLog());
		assertEquals("The All Tasks log may not be deleted.", e6.getMessage());
		    
		
		
		
	}
	
	/**
	 * Reading project 1 and remove task 
	 */
	@Test 
	public void testRemoveTask() {
		File testFile = new File("test-files/project1.txt");
		Project project = ProjectReader.readProjectFile(testFile); 
		project.setCurrentTaskLog("Design"); 
		project.removeTask(3); 
		project.setCurrentTaskLog("All Tasks"); 
		assertEquals(project.getCurrentLog().getTask(3).getTaskTitle(), "Wrote design proposal and rationale"); 
	}
	
	/**
	 * Testing removeCategoryLog 
	 */
	@Test 
	public void testRemoveCategoryLog() {
		File testFile = new File("test-files/project1.txt");
		Project project = ProjectReader.readProjectFile(testFile);
		project.setCurrentTaskLog("System Test"); 
		project.removeCategoryLog(); 
		
		assertEquals("Read Project 2 requirements", project.getCurrentLog().getTask(0).getTaskTitle()); 
	}
		
	
	
	
}