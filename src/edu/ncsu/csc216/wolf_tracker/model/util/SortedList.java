package edu.ncsu.csc216.wolf_tracker.model.util;

/**
 * The SortedList class implements a sorted linked list that maintains category elements
 * in a sorted order. It handles basic operations such as adding, removing, 
 * and retrieving category elements while ensuring they remain sorted.
 *
 * @param <E> type of elements in SortedList, extends Comparable, implements ISortedList.
 * 
 * @author Jayani Sivakumar
 * @author Diya Patel
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {
	
	/** Current size of SortedList. */
	private int size;
	/** The front node in the list */
    private ListNode front;
	
	
	/**
	 * Constructor for the SortedList class.
	 */
	public SortedList() {
		size = 0;
		front = null;
	}
	
	/**
     * Adds a new category element to the sorted list, maintaining sorted order.
     *
     * @param element the category element to be added.
     * @throws NullPointerException if the category element is null.
     * @throws IllegalArgumentException if element is a duplicate existence of another category element.
     */
	public void add(E element) {
		// checking if element is null
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		
		// checking for duplicates
        if (contains(element)) {
            throw new IllegalArgumentException("Cannot add duplicate element.");
        }
        
        ListNode newNode = new ListNode(element, null);
        
        if (front == null || front.data.compareTo(element) > 0) {
            newNode.next = front;
            front = newNode;
        } else {
            ListNode current = front;
            while (current.next != null && current.next.data.compareTo(element) < 0) {
                current = current.next;
            }
            
            newNode.next = current.next;
            current.next = newNode;
        }
        
        size++;
	}
	
	/**
     * Removes the category element at the given index from the sorted list.
     *
     * @param idx the index of the category element to remove.
     * @return the category element that was removed.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
	public E remove(int idx) {
		// checking if index is out of bounds
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		E removedElement;
        if (idx == 0) {
            removedElement = front.data;
            front = front.next;
        } else {
            ListNode current = front;
            for (int i = 0; i < idx - 1; i++) {
                current = current.next;
            }
            removedElement = current.next.data;
            current.next = current.next.next;
        }
        
        size--;
        return removedElement;
		
		
	}
	
	/**
     * Checks if the sorted list contains the category element.
     *
     * @param element the category element to look for.
     * @return returns true if the category element is found, else false.
     */
	public boolean contains(E element) {
		ListNode current = front;
		while (current != null) {
			if (current.data.equals(element)) {
			    return true;
			}
			
			current = current.next;
			
		}
		
		return false;
	}
	
	 /**
     * Retrieves the category element at the given index in the sorted list.
     *
     * @param idx the index of the category element to retrieve
     * @return the category element at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
	public E get(int idx) {
		// checking if index is out of bounds
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		ListNode current = front;
        for (int i = 0; i < idx; i++) {
            current = current.next;
        }
        return current.data;
	}
	
	/**
     * Returns the current number of category elements in the sorted list.
     *
     * @return the number of category elements in the sorted list
     */
	public int size() {
		return size;
	}
	
	/**
     * A private inner class representing a node in the sorted linked list.
     */
    private class ListNode {
    /** The data in the node */
    public E data;
    /** The next node in the list */
    public ListNode next;
    
    /**
     * Constructs a new ListNode with the specified data and a reference to the next node.
     * 
     * @param data the data to store in this node
     * @param next the reference to the next node
     */
     public ListNode(E data, ListNode next) {
       this.data = data;
       this.next = next;
     }
   }
}
