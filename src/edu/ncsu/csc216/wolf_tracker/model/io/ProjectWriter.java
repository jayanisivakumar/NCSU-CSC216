package edu.ncsu.csc216.wolf_tracker.model.io;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import edu.ncsu.csc216.wolf_tracker.model.log.AbstractTaskLog;
import edu.ncsu.csc216.wolf_tracker.model.log.CategoryLog;
import edu.ncsu.csc216.wolf_tracker.model.project.Project;
import edu.ncsu.csc216.wolf_tracker.model.task.Task;

/**
 * The ProjectWriter is responsible for writing project data 
 * to specified files. It provides methods to save the project information 
 * and statistics.
 * 
 * @author Jayani Sivakumar
 * @author Diya Patel
 * 
 */
public class ProjectWriter {
	
	/**
     * Writes the project data to the project file.
     *
     * @param projectFile the file object to write the project data to.
     * @param project the project object data to be written.
     * @throws IllegalArgumentException if error occurs while writing to the file.
     * 
     */
	public static void writeProjectFile(File projectFile, Project project) {
        try (PrintWriter out = new PrintWriter(projectFile)) {
            // get log from the project
        	out.println("! " + project.getProjectName());
        	String[] names = project.getCategoryNames(); 
        	for(int i = 1; i < names.length; i++ ) {
        		out.println("# " + names[i]);
        	}
        	project.setCurrentTaskLog("All Tasks");
            AbstractTaskLog allTasksLog = project.getCurrentLog();
            if (allTasksLog != null) {
                // using Task's toString() method
                for (int i = 0; i < allTasksLog.getTaskCount(); i++) {
                    Task task = allTasksLog.getTask(i);
                    out.println(task.toString());
                }
                out.flush();
                out.close();
                project.setChanged(false);
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to save file.", e);
        }
    }
	
	/**
     * Writes the statistics data of the project to the stats file.
     *
     * @param statsFile the file object to write the statistics to.
     * @param project the project object data for statistics.
     * @throws IllegalArgumentException if error occurs while writing to the file.
     * 
     */
	public static void writeStatsFile(File statsFile, Project project) {
        try (PrintWriter writer = new PrintWriter(statsFile)) {
            // Write the header
            writer.println("Category,Count,Min,Max,Average");
            
            for (String categoryName : project.getCategoryNames()) {
            	if(!"All Tasks".equals(categoryName)) {
            		project.setCurrentTaskLog(categoryName);
                    CategoryLog log = (CategoryLog) project.getCurrentLog();
                    if (log.getName().equals(categoryName)) {
                        writer.println(log.toString());
                    }
            	}
            	
            }
            project.setCurrentTaskLog("All Tasks");
            // "All Tasks" log is written last
            AbstractTaskLog allTasksLog = project.getCurrentLog();
            writer.println(allTasksLog.toString());

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Unable to save file.", e);
        }
    }
	
	


}
