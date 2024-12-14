package edu.ncsu.csc216.wolf_tracker.model.util;

/**
 * Interface for logged items.  Items are added to the end of the list.
 * Items may be edited (set) and removed.
 * @param <E> type for ILogList
 */
public interface ILogList<E> {
	
	/**
	 * Adds the element to the end of the list.
	 * @param element element to add
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element cannot be added
	 */
	void addLog(E element);
	
	/**
	 * Set the log at the given index to the given element.
	 * @param idx index of the log to edit
	 * @param element element to replace at the index
	 * @throws NullPointerException if element is null
	 * @throws IllegalArgumentException if element cannot be added
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * 		for the list
	 */
	void setLog(int idx, E element);
	
	/**
	 * Removes the log at the given index.
	 * @param idx index of log to remove
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * 		for the list
	 */
	E removeLog(int idx);
	
	/**
	 * Returns the log at the given index.
	 * @param idx index of log to get
	 * @return element at the given index
	 * @throws IndexOutOfBoundsException if the index is out of bounds
	 * 		for the list
	 */
	E getLog(int idx);
	
	/**
	 * Returns the number of logged elements.
	 * @return number of logged elements
	 */
	int size();

}
