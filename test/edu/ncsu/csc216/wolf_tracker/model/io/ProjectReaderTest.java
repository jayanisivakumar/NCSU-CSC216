package edu.ncsu.csc216.wolf_tracker.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tracker.model.log.CategoryLog;
import edu.ncsu.csc216.wolf_tracker.model.project.Project;
import edu.ncsu.csc216.wolf_tracker.model.task.Task;

/**
 * Tests the functionality of ProjectReader.
 */
public class ProjectReaderTest {
	
	//String testFilesPath = "test-files" + File.separator;

	/**
	 * Tests readProjectFile().
	 */
	@Test
    void testReadProjectFile() {
        // Specify the path to the test file
        File testFile = new File("test-files/projectRead.txt");
		//File testFile = new File(testFilesPath + "projectRead.txt");
        
        // Use ProjectReader to read the file and create the Project object
        Project project = ProjectReader.readProjectFile(testFile);
        
        // Validate project name
        assertEquals("Testing Project", project.getProjectName());

        // Validate that categories were added correctly
        String[] categories = project.getCategoryNames();
        assertEquals(3, categories.length);
        assertTrue(categories[0].contains("All Tasks"));
        assertTrue(categories[1].contains("Debugging"));
        assertTrue(categories[2].contains("Design"));
        

        // Validate that tasks were added correctly in the respective categories
        assertEquals(1, project.getCurrentLog().getTaskCount());
        assertEquals("Debugging Code", project.getCurrentLog().getTask(0).getTaskTitle());
    }
	
	/**
	 * Test reading project 
	 */
	@Test 
	public void testReadProject() {
		// Specify the path to the test file
        File testFile = new File("test-files/project1.txt");
		Project project = ProjectReader.readProjectFile(testFile);  
		Task t1 = new Task("Created CRC Cards", 27, "Identified the key classes and created CRC cards. Noted\nresponsibilities, collaborators, and possible state.\n");
		Task t2 = new Task("Transfered CRC Cards to UMLetino", 35, "Started creating a UML class diagram from the requirements\n");
		project.setCurrentTaskLog("Design"); 
		CategoryLog log = new CategoryLog("Design"); 
		t1.addCategory(log); 
		t2.addCategory(log); 
		assertEquals(project.getCurrentLog().getTask(1).toString(), t1.toString());
		assertEquals(project.getCurrentLog().getTask(2).toString(), t2.toString()); 
		
		project.setCurrentTaskLog("Documentation"); 
		assertEquals(project.getCurrentLog().getTask(0).getTaskTitle(), "Added Javadoc"); 
		assertEquals(project.getCurrentLog().getTask(1).getTaskTitle(), "Generated Javadoc"); 
		
		project.setCurrentTaskLog("All Tasks"); 
		assertEquals(project.getCurrentLog().getTask(0).getTaskTitle(), "Read Project 2 requirements"); 
		assertEquals(project.getCurrentLog().getTask(1).getTaskTitle(), "Created CRC Cards");  
		
		
		
		
		}
	

		
}
