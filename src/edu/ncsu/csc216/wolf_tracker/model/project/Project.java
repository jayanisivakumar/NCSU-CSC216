package edu.ncsu.csc216.wolf_tracker.model.project;

import java.io.File;

import edu.ncsu.csc216.wolf_tracker.model.io.ProjectWriter;
import edu.ncsu.csc216.wolf_tracker.model.log.AbstractTaskLog;
import edu.ncsu.csc216.wolf_tracker.model.log.AllTasksLog;
import edu.ncsu.csc216.wolf_tracker.model.log.CategoryLog;
import edu.ncsu.csc216.wolf_tracker.model.task.Task;
import edu.ncsu.csc216.wolf_tracker.model.util.ILogList;
import edu.ncsu.csc216.wolf_tracker.model.util.ISortedList;
import edu.ncsu.csc216.wolf_tracker.model.util.SortedList;

/**
 * Contains an ISrotedList of CategoryLog s, the AllTasksLog, and operations 
 * to manipulate these logs. 
 * 
 * @author Diya Patel 
 * @author Jayani Sivakumar 
 */
public class Project {
	/** project name */ 
	private String projectName; 
	/** boolean for change */ 
	private boolean isChanged;
	/** Project has an AllTasksLog of allTasksLog. */
	private AllTasksLog allTasksLog;
	/** Project has an ISortedList of categories. */
	private ISortedList<CategoryLog> categories;
	/** Project has an AbstractTaskLog of currentLog. */
	private AbstractTaskLog currentLog; 
	
	/**
	 * Constructs a Project with the given name 
	 * @param projectName the name of the project 
	 */
	public Project(String projectName) {
		this.categories = new SortedList<>();
		setProjectName(projectName);
		this.currentLog = allTasksLog;
		this.allTasksLog = new AllTasksLog();
		this.isChanged = true;
		
	}
	
	/**
	 * Saves the current project to the given file. 
	 * @param projectFile the file to save project to 
	 */
	public void saveProject(File projectFile) {
		ProjectWriter.writeProjectFile(projectFile, this); 
	}
	
	/**
	 * Saves the stats for the current project to the given file. 
	 * @param statsFile the file to save the stats to 
	 */
	public void saveStats(File statsFile) {
		ProjectWriter.writeStatsFile(statsFile, this); 
	}
	
	/**
	 * gets the name of the project 
	 * @return projectName the name of the project 
	 */
	public String getProjectName() {
		return projectName; 
	}
	
	/**
	 * sets the project name 
	 * @param projectName the name to set to the project 
	 * @throws IllegalArgumentException if the parameter is null, empty, or matcges ALL_TASKS_NAME
	 */
	private void setProjectName(String projectName){
		if(projectName == null || projectName.length() == 0 || projectName.equals(AllTasksLog.ALL_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name."); 
		}
		this.projectName = projectName; 
	}
	
	/**
	 * gets the isChanged to see if log has changed 
	 * @return false if the log is not changed
	 */
	public boolean isChanged() {
		return isChanged; 
	}
	
	/**
	 * sets the isChanged to see if the log had changed 
	 * @param isChanged the boolean to set 
	 */
	public void setChanged(boolean isChanged) {
		this.isChanged = isChanged; 
	}
	
	/**
	 * Creates a CategoryLog and adds it to the categories in sorted order. 
	 * The currentLog is updated to the new CategoryLog and isChanged is updated to true. 
	 * @param categoryName the name to add to the categories 
	 * @throws IllegalArgumentException if parameter is null or an empty string 
	 * or if it matches ALL_TASKS_NAME or there is a duplicate of the existing name 
	 */
	public void addCategoryLog(String categoryName) {
		if(categoryName == null || categoryName.length() == 0 || AllTasksLog.ALL_TASKS_NAME.equals(categoryName)) {
			throw new IllegalArgumentException("Invalid name."); 
		}
		
		// Check if a category with the same name already exists
	    for (int i = 0; i < categories.size(); i++) {
	        if (categories.get(i).getName().equals(categoryName)) {
	            throw new IllegalArgumentException("Invalid name.");
	        }
	    }
	    
		CategoryLog log = new CategoryLog(categoryName); 
		categories.add(log);
	    currentLog = log;
		setChanged(true); 
		 
	}
	
	/**
	 * returns a list of category names. The "All Tasks" log is always listed first. 
	 * @return categoryNames a list of category names 
	 */
	public String[] getCategoryNames() {
		String[] categoryNames = new String[categories.size() + 1]; 
		categoryNames[0] = "All Tasks"; 
		for(int i = 0; i < categories.size(); i++) {
			//if(!categories.get(i).getName().equals("All Tasks"))
				categoryNames[i + 1] = categories.get(i).getName(); 
		}
		
		System.out.println("Categories in order: ");
	    for (String categoryName : categoryNames) {
	        System.out.println(categoryName);
	    }
	    
		return categoryNames;          
	}
	
	/**
	 * sets the currentLog to the AbstractTaskLog with the given name. 
	 * If a CategoryLog with that name is not found then the currentLog is set to the allTasksLog. 
	 * @param logName the name to set the currentLog to 
	 */
	public void setCurrentTaskLog(String logName) {  
		System.out.println("Set current log: " + logName); 
		for(int i = 0; i < categories.size(); i++) {
			if(categories.get(i).getName().equals(logName)) {
				currentLog = categories.get(i); 
				return; 
			} 
		}
			currentLog = allTasksLog; 
		 
	}
	
	/**
	 * returns the currentLog in the form of a AbstractTaskLog 
	 * @return log the currentLog 
	 */
	public AbstractTaskLog getCurrentLog() {
		if(currentLog == null) {
			return allTasksLog; 
		}
		return currentLog; 
	}
	
	/**
	 * edits the name of the category 
	 * @param categoryName the name of the category to edit 
	 * @throws IllegalArgumentException if the parameter is null or empty String 
	 * or is a duplicate of another CategoryLog or if the currentLog is an AllTasksLog 
	 */
	public void editCategoryLogName(String categoryName) {
		if(categoryName == null || categoryName.length() == 0 || categoryName.equals(AllTasksLog.ALL_TASKS_NAME)) {
			throw new IllegalArgumentException("Invalid name.");  
		}
		for(int i = 0; i < categories.size(); i++) {
			//CategoryLog category = categories.get(i); 
			if(categories.get(i).getName().toUpperCase().equals(categoryName.toUpperCase())) {
				throw new IllegalArgumentException("Invalid name.");  
			}
		}
		if (currentLog == null) {
	        throw new IllegalArgumentException("The All Tasks log may not be edited.");
	    }
		if (AllTasksLog.ALL_TASKS_NAME.equals(currentLog.getName())) {
	        throw new IllegalArgumentException("The All Tasks log may not be edited.");
	    }
	
		for(int i = 0; i < categories.size(); i++) {
			if(categories.get(i).getName().equals(currentLog.getName())) {
				categories.remove(i); 
				break; 
			}
		}
		currentLog.setTaskLogName(categoryName);  
		categories.add((CategoryLog)currentLog);
		setChanged(true); 
		
	}
	
	/**
	 * currentLog is removed and then set to the allTasksLog any tasks from the log are removed 
	 * from AllTasksLog and name is removed 
	 * @throws IllegalArgumentException if the currentLog is an AllTasksLog 
	 */
	public void removeCategoryLog() {
		if (currentLog == null) {
	        throw new IllegalArgumentException("The All Tasks log may not be deleted.");
	    }
		
		if (AllTasksLog.ALL_TASKS_NAME.equals(currentLog.getName())) {
	        throw new IllegalArgumentException("The All Tasks log may not be deleted."); 
	    }
		//String name = currentLog.getName(); 
		for(int i = 0; i < categories.size(); i++) {
			if(categories.get(i).getName().equals(currentLog.getName())) {
				ILogList<Task> tasks = allTasksLog.getTasks(); 
				for(int j = tasks.size() - 1; j >= 0; j--) {
					if(tasks.getLog(j).getCategoryName().equals(currentLog.getName())) {
						tasks.removeLog(j); 
						//j--;
					}
				}
//				if(i > 0) {
//					this.setCurrentTaskLog(categories.get(i - 1).getName()); 
//				}
				categories.remove(i); 
				break; 
			}
		}
		
	    currentLog = allTasksLog;
		setChanged(true); 
		
	}
	
	/**
	 * adds a task directly to a CategoryLog, currentLog can only be a CategoryLog to add task 
	 * @param t task to add to a CategoryLog  
	 */
	public void addTask(Task t) {
		if(currentLog instanceof CategoryLog) {
			currentLog.addTask(t);
			allTasksLog.addTask(t);
			setChanged(true); 
		}
	}
	

	/**
	 * updates the fields of the Task at the index given
	 * @param idx the index of the task to edit 
	 * @param taskName the new name of the task 
	 * @param taskDuration the new duration of the task 
	 * @param taskDetails the new details of the task 
	 * @throws IllegalArgumentException if parameters are not valid for task construction 
	 */
	public void editTask(int idx, String taskName, int taskDuration, String taskDetails) {
	    Task t = currentLog.getTask(idx); 
	    t.setTaskDetails(taskDetails);
	    t.setTaskTitle(taskName);
	    t.setTaskDuration(taskDuration);
		setChanged(true);  
	}
	
	/**
	 * removes the task at the index from both CategoryLog and AllTasksLog 
	 * @param idx the task to remove at that index 
	 */
	public void removeTask(int idx) {
		Task removed = currentLog.getTask(idx); 
		currentLog.removeTask(idx); 
		if(!currentLog.getName().equals(allTasksLog.getName())) {
			for(int i = 0; i < allTasksLog.getTaskCount(); i++) {
				if(allTasksLog.getTask(i).equals(removed)) {
					allTasksLog.removeTask(i); 
				}
			}
		}
		else {
			this.setCurrentTaskLog(removed.getCategoryName());
			for(int i = 0; i < currentLog.getTaskCount(); i++) {
				if(currentLog.getTask(i).equals(removed)) {
					currentLog.removeTask(i); 
				}
			}
			this.setCurrentTaskLog("All Tasks");
		}
		
		setChanged(true);  
	}
	
	/**
	 * returns a 2D string array with the last logged item in each categoryLog if there is no logged tasks 
	 * the title is none for that task and duration is a empty string 
	 * @return recentTasks a 2D String array with the most recently logged task for each CategoryLog 
	 */
	public String[][] getMostRecentTasks(){ 
		String[][] recentTasks = new String[categories.size()][3]; 
		for(int i = 0; i < categories.size(); i++) {
			CategoryLog category = categories.get(i); 
			if(category.getTaskCount() > 0) {
				Task t = category.getTask(category.getTaskCount() - 1); 
				recentTasks[i][0] = t.getTaskTitle();  
				recentTasks[i][1] = String.valueOf(t.getTaskDuration()); 
				recentTasks[i][2] = t.getCategoryName();
			}
			else {
				recentTasks[i][0] = "None";  
				recentTasks[i][1] = ""; 
				recentTasks[i][2] = category.getName();
			}
		}
		return recentTasks; 
	}
}
	
		