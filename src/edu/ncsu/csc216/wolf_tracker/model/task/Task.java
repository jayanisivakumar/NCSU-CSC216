package edu.ncsu.csc216.wolf_tracker.model.task;

import edu.ncsu.csc216.wolf_tracker.model.log.CategoryLog;

/**
 * The Task Class contains the information about each individual task 
 * includiing the taskTitle, taskDuration, and taskDetails. 
 * The Task additionally contains a CategoryLog field named category, that is the category the task belongs to.
 * @author Diya Patel 
 * @author Jayani Sivakumar 
 */
public class Task {
	/** the title of the task */ 
	private String taskTitle; 
	/** the duration of the task */ 
	private int taskDuration; 
	/** the details of the task */ 
	private String taskDetails; 
	/** Task has an instance of categoryLog */
	private CategoryLog category;
	
	/**
	 * Constructs the task with the given parameters. The category is initially null; 
	 * @param taskTitle the title of the task 
	 * @param taskDuration the duration of the task 
	 * @param taskDetails the details of the task 
	 */
	public Task(String taskTitle, int taskDuration, String taskDetails) {
		setTaskTitle(taskTitle); 
		setTaskDuration(taskDuration); 
		setTaskDetails(taskDetails);
		category = null; 
	}
	
	/**
	 * returns the task title 
	 * @return this.taskTitle the title of the task
	 */
	public String getTaskTitle() {
		return this.taskTitle; 
	}
	
	/**
	 * public helper method that check for a valid task title 
	 * @param taskTitle the title of the task to set 
	 * @throws IllegalArgumentException if the parameter is null or empty string
	 */
	public void setTaskTitle(String taskTitle) {
		if(taskTitle == null || taskTitle.length() == 0) {
			throw new IllegalArgumentException("Incomplete task information."); 
		}
		this.taskTitle = taskTitle; 
	}
	
	/**
	 * Public helper method that checks the duration is a positive number of at least one 
	 * @param taskDuration the duration of the task to set 
	 * @throws IllegalArgumentException if the duration is not a positive number of at least 1 
	 */
	public void setTaskDuration(int taskDuration) { 
		if(taskDuration < 1) {
			throw new IllegalArgumentException("Incomplete task information."); 
		}
		this.taskDuration = taskDuration; 
	}
	
	/**
	 * returns the task duration of the task 
	 * @return this.taskDuration the duration of the task 
	 */
	public int getTaskDuration() {
		return this.taskDuration; 
	}
	
	/**
	 * returns the details of the task 
	 * @return this.taskDetails the details of the task 
	 */
	public String getTaskDetails() {
		return this.taskDetails; 
	}
	
	/**
	 * Public helper method that checks for a valid task details  
	 * @param taskDetails the details of the task to set 
	 * @throws IllegalArgumentException if the parameter is null or empty String 
	 */
	public void setTaskDetails(String taskDetails) {
		if(taskDetails == null || taskDetails.length() == 0) {
			throw new IllegalArgumentException("Incomplete task information.");  
		}
		this.taskDetails = taskDetails; 
	}
	
	/**
	 * Sets the task’s CategoryLog category field 
	 * @param category the category of the task 
	 * @throws IllegalArgumentException if parameter is null or there 
	 * is already an assigned category 
	 */
	public void addCategory(CategoryLog category) {
		if(category == null) {
			throw new IllegalArgumentException("Incomplete task information.");   
		} 
		else if (this.category != null) {
            throw new IllegalArgumentException("Incomplete task information.");
        }
        
        this.category = category;
   	}
	
	/**
	 * returns the category name or empty string if the category is null. 
	 * @return name the name of the category 
	 */
	public String getCategoryName() {
		if (category == null) {
	        return "";
	    } 
		else {
	        return category.getName(); 
	    } 
	}
	
	/**
	 *  Returns a string representation of the Task for printing to a file. 
	 *  @return taskString the string representation of the task 
	 */
	@Override
	public String toString() {
		return "* " + this.getTaskTitle() + "," + String.valueOf(this.getTaskDuration()) + "," + this.getCategoryName() + "\n" + this.getTaskDetails(); 
	}
}
