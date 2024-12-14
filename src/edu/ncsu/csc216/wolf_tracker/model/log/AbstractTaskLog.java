package edu.ncsu.csc216.wolf_tracker.model.log;

import java.math.BigDecimal;
import java.math.RoundingMode;

import edu.ncsu.csc216.wolf_tracker.model.task.Task;
import edu.ncsu.csc216.wolf_tracker.model.util.ILogList;
import edu.ncsu.csc216.wolf_tracker.model.util.LogList;

/**
 * The AbstractTaskLog class is an abstract class at the top of the hierarchy for lists of logged tasks. 
 * The AbstractTaskLog knows its takLogName and the ILogList of Tasks. 
 * @author Diya Patel 
 * @author Jayani Sivakumar 
 */
public abstract class AbstractTaskLog {

	/** the name of the task list */ 
	private String taskListName; 
	
	/** AbstractTaskLog has an ILogList of tasks. */
	private ILogList<Task> tasks;
	
	/**
	 * Sets the log’s name and constructs a LogList for the Tasks. 
	 * @param taskListName the name of the task log
	 * @throws IllegalArgumentException if the taskListName is null or empty string  
	 */
	public AbstractTaskLog(String taskListName) {
	    if (taskListName == null || taskListName.isEmpty()) {
	        throw new IllegalArgumentException("Invalid name.");
	    }
	    
	    this.taskListName = taskListName;
	    this.tasks = new LogList<>();
	}
	
	/**
	 * returns the list name of the task 
	 * @return this.taskListName the name of the task
	 */
	public String getName() {
		return this.taskListName; 
	}
	
	/**
	 * Removes the Task from the log of tasks and returns the removed task
	 * @param idx the idx of the task to remove 
	 * @return t the tasked removed 
	 * @throws IndexOutOfBoundsException if index is out of bounds 
	 */
	public Task removeTask(int idx) {
		return this.tasks.removeLog(idx); 
	}
	
	/**
	 * Returns the Task at the given index.
	 * @param idx the idx of the task to retrieve
	 * @return t the task at the idx given 
	 * @throws IndexOutOfBoundsException if index is out of bounds
	 */
	public Task getTask(int idx) {
		return this.tasks.getLog(idx);
	}
	
	/**
	 * Returns the number of logged tasks.
	 * @return count the number of logged tasks. 
	 */
	public int getTaskCount() {
		return this.tasks.size(); 
	}
	
	/**
	 * returns the minimum duration of the logged tasks. 
	 * @return min the minimum duration of the logged tasks
	 */
	public int getMinDuration() {
		String[][] taskList = getTasksAsArray(); 
		if (taskList.length == 0) {
	        return 0;
	    }
		int min = Integer.parseInt(taskList[0][1]); 
		for(int i = 0; i < taskList.length; i++){
			if(min > Integer.parseInt(taskList[i][1])) {
				min = Integer.parseInt(taskList[i][1]); 
			}
		}
        return min;
	}
	
	/**
	 * Returns the maximum duration of the logged tasks.
	 * @return max the maximum duration of the logged tasks 
	 */
	public int getMaxDuration() {
		String[][] taskList = getTasksAsArray(); 
		if (taskList.length == 0) {
	        return 0;
	    }
		int max = Integer.parseInt(taskList[0][1]); 
		for(int i = 0; i < taskList.length; i++){
			if(max < Integer.parseInt(taskList[i][1])) {
				max = Integer.parseInt(taskList[i][1]); 
			}
		}
        return max;
	}
	
	/**
	 *  Returns the average duration of the logged tasks as a double. The result should be rounded to the nearest tenth digit 
	 * @return avg the average duration of the logged tasks 
	 */
	public double getAvgDuration() {
		String[][] taskList = getTasksAsArray();
		if (taskList.length == 0) {
	        return 0;
	    }
		double sum = 0.0; 
		for(int i = 0; i < taskList.length; i++){
				sum += Integer.parseInt(taskList[i][1]); 
			
		}
		double avg = sum / taskList.length; 
		avg = round(avg, 1); 
        return avg;
	}
	
	/**
	 * rounds the double values 
	 * @param value retrieving decimal value
	 * @param places retrieving decimal places
	 * @return number the number rounded 
	 */
	private static double round(double value, int places) {

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	/**
	 *  Returns a 2D string array for display in the GUI. Column 0 is the task title, column 1 is the duration, and column 2 is the category.
	 * @return taskList the 2D string representation for tasks 
	 */
	public String[][] getTasksAsArray(){
		String[][] taskList = new String[getTasks().size()][3]; 
		for(int i = 0; i < taskList.length; i++) {
			taskList[i][0] =  getTasks().getLog(i).getTaskTitle(); 
			taskList[i][1] =  String.valueOf(getTasks().getLog(i).getTaskDuration()); 
			taskList[i][2] =  getTasks().getLog(i).getCategoryName();  
		}
		return taskList; 
	}  
	
	/**
	 * Returns a string representation of the summary statistics for printing to a file.
	 * @return summaryStats a string representation of the summary statistics for printing to a file. 
	 */
	public String toString() {
		if(this.getTaskCount() == 0) {
			return this.getName() + "," + this.getTaskCount() + ",,,";  
		}
		return this.getName() + "," + this.getTaskCount() + "," + this.getMinDuration() + "," + this.getMaxDuration() + "," + this.getAvgDuration(); 
	} 
	
	/**
	 * Sets a task at a specified index in this CategoryLog. Calls the
	 * AbstractTaskLog's setTask method to perform the update,
	 * then associates this CategoryLog with the task.
	 * 
	 * @param idx the index of the task to set
	 * @param task the new task to replace the existing task at the specified index
	 * @throws IndexOutOfBoundsException if the index is out of range
	 */
	public void setTask(int idx, Task task) {
		this.tasks.setLog(idx, task);
		
	}

	/**
	 * Adds a task to this CategoryLog. Calls the AbstractTaskLog's
	 * addTask method to add the task, then associates this CategoryLog
	 * with the task.
	 * 
	 * @param task the Task to add to this CategoryLog
	 * @throws IllegalArgumentException if the task cannot be added to the log
	 */
	public void addTask(Task task) {
		this.tasks.addLog(task); 
		
	}
	
	/**
	 * Returns the logList of tasks.
	 * @return returns the log list of tasks
	 */
	public ILogList<Task> getTasks() {
		return this.tasks;
	}

	/**
	 * sets the task log name 
	 * @param allTasksName the name for the log 
	 */
	public void setTaskLogName(String allTasksName) {		
        this.taskListName = allTasksName;
		
	}

}