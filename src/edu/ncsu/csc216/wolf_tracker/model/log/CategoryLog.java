package edu.ncsu.csc216.wolf_tracker.model.log;

import edu.ncsu.csc216.wolf_tracker.model.task.Task;

 /**
 * CategoryLog represents a log for tasks within a specific category.
 * Extends AbstractTaskLog to inherit basic task log functionalities
 * and implements Comparable to allow category logs to be compared
 * by name.
 * 
 * @author Jayani Sivakumar
 * @author Diya Patel
 */
 public class CategoryLog extends AbstractTaskLog implements Comparable<CategoryLog> {


	 /**
	  * Constructs a CategoryLog with the specified name.
	  * 
	  * @param name the name of the category log
	  * @throws IllegalArgumentException if the name is null or empty
	  */
	 public CategoryLog(String name){
		 super(name);
	 }
	 
	 /**
	  * Compares this CategoryLog with another CategoryLog
	  * based on their names, ignoring case.
	  * 
	  * @param categoryName the other CategoryLog to compare to
	  * @return a negative integer, zero, or a positive integer as this
	  *    CategoryLog's name is lexicographically less than, equal to,
	  *      or greater than the other CategoryLog's name
	  */
	@Override
	public int compareTo(CategoryLog categoryName) {
		return this.getName().toLowerCase().compareTo(categoryName.getName().toLowerCase());
	}
	
	 /**
     * Sets a task at a specified index in this CategoryLog. Calls the
     * AbstractTaskLog's setTask method to perform the update,
     * then associates this CategoryLog with the task.
     * 
     * @param idx the index of the task to set
     * @param task the new task to replace the existing task at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     * @throws NullPointerException if the task is null
     */
	@Override
	public void setTask(int idx, Task task) {
		if (task == null) {
			throw new NullPointerException("Cannot set a null task.");
		}
		if (idx < 0 || idx >= getTaskCount()) {
	        throw new IndexOutOfBoundsException("Invalid index.");
	    }
		if (!this.getName().equals(task.getCategoryName())) {
		    task.addCategory(this);
		}
		super.setTask(idx, task);
	}
	
	/**
     * Adds a task to this CategoryLog. Calls the AbstractTaskLog's
     * addTask method to add the task, then associates this CategoryLog
     * with the task.
     * 
     * @param task the Task to add to this CategoryLog
     * @throws NullPointerException if the task is null
     */
	@Override
	public void addTask(Task task) {
		if (task == null) {
			throw new NullPointerException("Cannot set a null task.");
		}
		
		super.addTask(task); 
		task.addCategory(this);
		
	}	

}
