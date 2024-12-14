package edu.ncsu.csc216.wolf_tracker.model.log;

/**
 * Class that extends the AbstractTaskLog and overrides behavior for 
 * setting the log's name 
 * 
 * @author Diya Patel 
 * @author Jayani Sivakumar 
 */
public class AllTasksLog extends AbstractTaskLog {
	/** constant for task name */ 
	public static final String ALL_TASKS_NAME = "All Tasks"; 
	
	/**
	 * Constructs the AllTaskLog with the expected name. 
	 */
	public AllTasksLog() {
		 super(ALL_TASKS_NAME);
	}
	
	/**
	 * sets the name of the Task Log and overrides the method to ensure 
	 * that the parameter value matches the expected name. 
	 * and IAE is thrown if the name is not set  
	 * @param taskLogName the the name to set the task log name to 
	 * @throws IllegalArgumentException if user tries edit all tasks list
	 */
	public void setTaskLogName(String taskLogName) {
		if (!ALL_TASKS_NAME.equals(taskLogName)) {
            throw new IllegalArgumentException("The All Tasks log may not be edited.");
        }
		
        super.setTaskLogName(ALL_TASKS_NAME);
	}
}
