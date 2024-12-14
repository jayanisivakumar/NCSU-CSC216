package edu.ncsu.csc216.wolf_tracker.model.io;


import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.wolf_tracker.model.project.Project;
import edu.ncsu.csc216.wolf_tracker.model.task.Task;

/**
 * Tests the functionality of ProjectWriter.
 */
class ProjectWriterTest {

    
    /** The Project instance used for testing. */
    private Project project;
    
    /** Project file for writing project contents into. */
    private File projectFile;
    
    /** Statistics file for writing statistics into. */
    private File statsFile;
    

    /**
     * Set up method that initializes projectWriter, project, and projectFile
     * and adds category and task to be used for testing.
     */
    @BeforeEach
    public void setUp() {
        project = new Project("Testing Project");

        project.addCategoryLog("Design");

        // Adding a task to the Debugging category
        //CategoryLog categoryLog = (CategoryLog) project.getCurrentLog();
        Task task = new Task("Finished uml", 60, "Resolve issue in code");
        project.addTask(task);
        //categoryLog.addTask(task);
        assertEquals("Design", project.getCurrentLog().getName());
    }

    /**
     * Tests the functionality of ProjectWriter.writeProjectFile().
     */
    @Test
    public void testWriteProjectFile() {
        projectFile = new File("test-files/testProject.txt");
        ProjectWriter.writeProjectFile(projectFile, project);

        try (Scanner scanner = new Scanner(projectFile)) {
            boolean foundTask = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("Finished uml")) {
                    foundTask = true;
                    break;
                }
            }
            assertTrue(foundTask);
        } catch (FileNotFoundException e) {
            fail();
        }
        
        // invalid file
        File invalidFile = new File("/invalid/testProject.txt");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> ProjectWriter.writeProjectFile(invalidFile, project));
        assertEquals("Unable to save file.", exception.getMessage());
    }
    
    /**
     * Tests the functionality of ProjectWriter.writeStatsFile().
     */
    @Test
    public void testWriteStatsFile() {
    	statsFile = new File("test-files/testStats.txt");
        ProjectWriter.writeStatsFile(statsFile, project);
        
        List<String> lines = null;
		try {
			lines = Files.readAllLines(statsFile.toPath());
		} catch (IOException e) {
			fail();
		}
        
        assertEquals("Category,Count,Min,Max,Average", lines.get(0));

        // Design category line
        assertEquals("Design,1,60,60,60.0", lines.get(1));

        // "All Tasks" line at the end
        assertEquals("All Tasks,1,60,60,60.0", lines.get(lines.size() - 1));
        
        File invalidFile = new File("/invalid/testStats.txt");
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> ProjectWriter.writeStatsFile(invalidFile, project));
        assertEquals("Unable to save file.", exception.getMessage());
    }
    

	/**
	 * Tests Teaching Staff Project Writer 
	 */
    @Test 
    public void testProjectWriter() {
    	Project project1 = new Project("Project"); 
    	project1.addCategoryLog("Category1");
    	Task t1 = new Task("Task1", 45, "Task1Details"); 
    	Task t2 = new Task("Task2", 15, "Task2Details"); 
    	project1.addTask(t1);
    	project1.addTask(t2);
    	project1.addCategoryLog("Category2");
    	Task t3 = new Task("Task3", 25, "Task3Description"); 
    	project1.addTask(t3);
    	project1.addCategoryLog("ACategory");
    	projectFile = new File("test-files/testProject1.txt");
    	ProjectWriter.writeProjectFile(projectFile, project1);
    	assertTrue(checkFiles("test-files/testProject1.txt", "test-files/expected_out.txt")); 
    }
    
    /**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 * @return true if file matches
	 */
	private boolean checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
			 Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
			
			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act, "Expected: " + exp + " Actual: " + act); 
				//The third argument helps with debugging!
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
				return false;
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
				return false;
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
		return true;
	}  


}
