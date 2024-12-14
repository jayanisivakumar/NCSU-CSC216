package edu.ncsu.csc216.wolf_tracker.model.util;

/**
 * LogList implements a custom array-based list to store task elements.
 * This class allows adding, retrieving, modifying, and removing task elements.
 *
 * @param <E> type of elements in LogList.
 * 
 * @author Jayani Sivakumar
 * @author Diya Patel
 */
public class LogList<E> implements ILogList<E> {
	
	/** Array that holds the task elements. */
	private E[] list;
	
	/** The current size of task elements in the LogList. */
	private int size;
	
	/** Initial capacity of LogList. */
	private static final int INIT_CAP = 10;
	
	/**
     * Constructor for LogList class.
     */
	@SuppressWarnings("unchecked")
	public LogList() {
		// creating generic arrayList
		list = (E[]) new Object [INIT_CAP];
		size = 0;
	}
	
	/**
     * Adds a task element to end of the list.
     *
     * @param element the task element to be added.
     * @throws NullPointerException if task element is null.
     */
	public void addLog(E element) {
		// checking if element is null
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		
		// checking capacity before adding
		ensureCapacity(size + 1);
		list[size++] = element;
		
	}
	
	/**
     * Sets the task element at given index.
     *
     * @param idx the index of the task element to be set.
     * @param element the new task element to set.
     * @throws IndexOutOfBoundsException if the index is out of range.
     * @throws NullPointerException if the task element is null.
     */
	public void setLog(int idx, E element) {
		// checking if element is null
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		
		// checking if index is out of bounds
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		list[idx] = element;
		
	}
	
	/**
     * Removes the task element at the given index.
     *
     * @param idx the index of the task element to remove.
     * @return the task element that was removed.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
	public E removeLog(int idx) {
		// checking if index is out of bounds
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		E removedElement = list[idx];
		for (int i = idx; i < size - 1; i++) {
			list[i] = list[i + 1];
		}
		list[size - 1] = null;  
		size--;
		return removedElement;
	}
	
	/**
     * Retrieves the task element at the given index.
     *
     * @param idx the index of the task element to retrieve.
     * @return the task element at the given index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
	public E getLog(int idx) {
		// checking if index is out of bounds
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
	    }
		
		return list[idx];
				
	}
	
	 /**
     * Returns the number of task elements in the list.
     *
     * @return current size of the log list.
     */
	public int size() {
		return size;
	}
	
	/**
     * Ensures list has enough capacity to hold the given size.
     *
     * @param size size of the log list.
     */
	private void ensureCapacity(int size) {
		if (size > list.length) {
			@SuppressWarnings("unchecked")
			// doubling capacity
			E[] newList = (E[]) new Object[list.length * 2]; // doubling the capacity
	        
			// functionality
			for (int i = 0; i < list.length; i++) {
		        newList[i] = list[i];
		    }
			
			// update reference to point to the newList
	        list = newList;
			
		}
	}

}
