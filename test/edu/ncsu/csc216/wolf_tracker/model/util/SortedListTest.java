package edu.ncsu.csc216.wolf_tracker.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the SortedList class.
 */
public class SortedListTest {
	
	/** SortedList of type String that will be used to test construction of SortedList*/
	private SortedList<String> sortedList;

	/**
     * Before each test, instantiate list to a new SortedList that isn't null
     */
    @BeforeEach
    public void setUp() {
        sortedList = new SortedList<>();
    }

	/**
	 * Tests the SortedList constructor.
	 */
	@Test
	public void testSortedListConstrctor() {
		assertEquals(0, sortedList.size());
	}
	
	/**
	 * Tests the add() method.
	 */
	@Test
	public void testAdd() {
		sortedList.add("Design");
		assertEquals(1, sortedList.size());
		assertEquals("Design", sortedList.get(0));
		
		// testing null
		Exception e = assertThrows(NullPointerException.class, () -> 
        sortedList.add(null));
        assertEquals("Cannot add null element.", e.getMessage());
        
        // testing duplicates
        sortedList.add("Debugging");
        assertEquals("Debugging", sortedList.get(0));
        Exception e1 = assertThrows(IllegalArgumentException.class, () -> 
            sortedList.add("Debugging"));
        assertEquals("Cannot add duplicate element.", e1.getMessage());
        
        sortedList.remove(0);
        sortedList.remove(0);
        
        
        //testing sorted order
        sortedList.add("Implementation");
        sortedList.add("Unit Test");
        sortedList.add("Deployment");
        
        assertEquals(3, sortedList.size());
        assertEquals("Deployment", sortedList.get(0));
        assertEquals("Implementation", sortedList.get(1));
        assertEquals("Unit Test", sortedList.get(2));
        
        //adding to the middle
        sortedList.add("Documentation");
        
        assertEquals(4, sortedList.size());
        assertEquals("Deployment", sortedList.get(0));
        assertEquals("Documentation", sortedList.get(1));
        assertEquals("Implementation", sortedList.get(2));
        assertEquals("Unit Test", sortedList.get(3));
	}
	
	/**
	 * Tests the remove() method.
	 */
	@Test
	public void testRemove() {
		Exception e = assertThrows(IndexOutOfBoundsException.class, () -> 
        sortedList.remove(-9));
        assertEquals("Invalid index.", e.getMessage());
		// front is null
	    Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> 
	    sortedList.remove(0));
	    assertEquals("Invalid index.", e1.getMessage());
	    
	    sortedList.add("System Test");
	    sortedList.add("Unit Test");
	    sortedList.add("All Tasks");
	    sortedList.add("Deployment");

	    // remove from beginning
	    assertEquals("All Tasks", sortedList.remove(0));
	    assertEquals(3, sortedList.size());
	    assertEquals("Deployment", sortedList.get(0));
	    assertEquals("System Test", sortedList.get(1));
	    assertEquals("Unit Test", sortedList.get(2));

	    //remove from middle of the list
	    assertEquals("System Test", sortedList.remove(1));
	    assertEquals(2, sortedList.size());
	    assertEquals("Deployment", sortedList.get(0));
	    assertEquals("Unit Test", sortedList.get(1));

	    //remove from end of the list
	    assertEquals("Unit Test", sortedList.remove(1));
	    assertEquals(1, sortedList.size());
	    assertEquals("Deployment", sortedList.get(0));

	    // Remove last remaining element
	    assertEquals("Deployment", sortedList.remove(0));
	    assertEquals(0, sortedList.size());

	    Exception e2 = assertThrows(IndexOutOfBoundsException.class, () -> 
	        sortedList.remove(0));
	    assertEquals("Invalid index.", e2.getMessage());
	}
	
	/**
	 * Tests the list nodes of the SortedList class
	 */
	@Test
	public void testLongerSortedList() {
	    sortedList.add("Design");
	    sortedList.add("Debugging");
	    sortedList.add("Testing");
	    sortedList.add("Documentation");
	    sortedList.add("Implementation");

	    assertEquals("Documentation", sortedList.remove(2));
	    assertEquals(4, sortedList.size());
	    assertEquals("Debugging", sortedList.get(0));
	    assertEquals("Design", sortedList.get(1));
	    assertEquals("Implementation", sortedList.get(2));
	    assertEquals("Testing", sortedList.get(3));
	}
	
	/**
	 * Tests the contains() method.
	 */
	@Test
	public void testContains() {
		sortedList.add("System Test");
        sortedList.add("Unit Test");

        assertTrue(sortedList.contains("System Test"));
        assertTrue(sortedList.contains("Unit Test"));
        assertFalse(sortedList.contains("Design"));
	}
	
	/**
	 * Tests the get() method.
	 */
	@Test
	public void testGet() {
		//testing valid
		sortedList.add("Deployment");
		assertEquals("Deployment", sortedList.get(0));
		
		// testing invalid index
		Exception e = assertThrows(IndexOutOfBoundsException.class, () -> 
        sortedList.get(5));
        assertEquals("Invalid index.", e.getMessage());
        
        Exception e1 = assertThrows(IndexOutOfBoundsException.class, () -> 
        sortedList.get(-5));
        assertEquals("Invalid index.", e1.getMessage());
	}
	
	/**
	 * Tests the size() method.
	 */
	@Test
	public void testSize() {
		sortedList.add("Debugging");
        sortedList.add("Deployment");
        assertEquals(2, sortedList.size());
	}
	

}
