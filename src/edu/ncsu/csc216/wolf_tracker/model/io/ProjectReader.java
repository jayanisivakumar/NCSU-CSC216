package edu.ncsu.csc216.wolf_tracker.model.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.ncsu.csc216.wolf_tracker.model.project.Project;
import edu.ncsu.csc216.wolf_tracker.model.task.Task;

/**
 * ProjectReader is responsible for reading project files 
 * that contain project information, including categories and tasks. 
 * It provides methods to load a project from a specified file and 
 * to process the categories and tasks defined within that file.
 * 
 * @author Jayani Sivakumar
 * @author Diya Patel
 * 
 */
public class ProjectReader {
	
	 /**
     * This method reads the entire contents of the file into a string, processes 
     * the project name, categories, and tasks. It constructs and adds categories 
     * and tasks to the project. Invalid categories or tasks
     * (i.e., they cannot be constructed or information is missing) are ignored.
     *
     * @param file the file object to read from
     * @return returns the loaded project.
     * @throws IllegalArgumentException if file does not exist or cannot be loaded.
     */
	public static Project readProjectFile(File file) {
	    if (!file.exists()) {
	        throw new IllegalArgumentException("Unable to load file.");
	    }

	    try (Scanner scanner = new Scanner(file)) {
	        StringBuilder fileContent = new StringBuilder();
	        while (scanner.hasNextLine()) {
	            fileContent.append(scanner.nextLine()).append("\n");
	        }

	        // Split by newlines, then process lines based on prefix
	        String[] lines = fileContent.toString().split("\\r?\\n");
	        if (lines.length == 0 || !lines[0].startsWith("!")) {
	            throw new IllegalArgumentException("Unable to load file.");
	        }

	        // Initialize project with the project name (trim the leading '!')
	        String projectName = lines[0].substring(1).trim();
	        Project project = new Project(projectName);

	        for (int i = 1; i < lines.length; i++) {
	            String line = lines[i];
	            if (line.startsWith("#")) {
	                // Category line
	                String categoryName = line.substring(1).trim();
	                if (!categoryName.isEmpty()) {
	                    try {
	                        project.addCategoryLog(categoryName);
	                    } catch (IllegalArgumentException e) {
	                    	//System.out.println(); 
	                        // Ignore invalid categories
	                    }
	                }
//	            } else if (line.startsWith("*")) {
//	                // Task line, pass to processTask
//	                processTask(project, line);
	            } 
	        }
	        Scanner fileReader = new Scanner(file);
		    fileReader.useDelimiter("\\r?\\n?[*]");
		    if(fileReader.hasNextLine()) {
		    	fileReader.next();
		    }
		    while (fileReader.hasNextLine()) {  
		    	String line = fileReader.next(); 
		    	processTask(project, line); 
		    } 
		    fileReader.close();
		    project.setCurrentTaskLog("All Tasks");
		    return project;

	    } catch (FileNotFoundException e) {
	        throw new IllegalArgumentException("Unable to load file.", e);
	    }
	    
    }
	
	/**
     * This method helps in parsing the task data from input file.
     * It breaks down the file content into tokens, constructs 
     * task objects, and adds them to the corresponding category. 
     *
     * @param project the project object to which tasks added.
     * @param readLine represents the content of the project file to be read line by line.
     */
	private static void processTask(Project project, String readLine) {
        String title = ""; 
        int duration = 0; 
        String details = ""; 
     
		try {
			Scanner scan = new Scanner(readLine.trim());        
			//scan.useDelimiter("\\r?\\n?");
			scan.useDelimiter("\n");
			String d = scan.next();
			Scanner taskInfo = new Scanner(d);
			taskInfo.useDelimiter(",");
			title = taskInfo.next(); 
			duration = Integer.valueOf(taskInfo.next()); 
			String categoryName = taskInfo.next(); 
			project.setCurrentTaskLog(categoryName);
			while (scan.hasNextLine()) { 	 
		    	details += scan.next() + "\n"; 
		    }
			 scan.close();
		     taskInfo.close();
		} catch(Exception e) {
			//do nothing if exception is thrown by any of the following 
		}
		
//        // Check if line starts with '*' indicating a task line
//        if (readFile.startsWith("*")) {
//            // Remove the leading '*' and split by commas
//            String[] parts = readFile.substring(1).split(",", 3);
//
//            // Extract title
//            if (parts.length > 0 && !parts[0].trim().isEmpty()) {
//                title = parts[0].trim();
//            }
//
//            // Extract duration
//            if (parts.length > 1 && !parts[1].trim().isEmpty()) {
//                try {
//                    duration = Integer.parseInt(parts[1].trim());
//                } catch (NumberFormatException e) {
//                    // Ignore invalid duration, continue with other parts
//                    duration = 0;
//                }
//            }
//
////            // Extract details, if available
////            if (parts.length > 2) {
////                categories = parts[2].trim();
////            }
//        }

        // Create task with empty details if missing
		try {
	        Task task = new Task(title, duration, details);
	        project.addTask(task);
		} catch(Exception e) {
			// do not add to project 
		}
    }



}