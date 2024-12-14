package edu.ncsu.csc216.wolf_tracker.model.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of LogList class.
 */
public class LogListTest {

	/** LogList of type String that will be used to test construction of LogList*/
	private LogList<String> logList;

	/**
     * Before each test, instantiate list to a new LogList that isn't null
     */
	@BeforeEach
	 public void setUp() {
	    logList = new LogList<>();
	}
	
	/**
     * Tests the constructor for LogList class.
     */
	@Test
	public void testLogListConstructor() {
		assertEquals(0, logList.size());
	}
	
	/**
	 * Tests the addLog().
	 */
	@Test
	public void testAddLog() {
		logList.addLog("Task 1");
		assertEquals(1, logList.size());
		assertEquals("Task 1", logList.getLog(0));
		
		logList.addLog("Task 2");
        assertEquals(2, logList.size());
        assertEquals("Task 2", logList.getLog(1));
        
        //testing null
        Exception e = assertThrows(NullPointerException.class, () -> 
            logList.addLog(null));
        assertEquals("Cannot add null element.", e.getMessage());
	}
	
	/**
	 * Tests the setLog().
	 */
	@Test
	public void testSetLog() {
		logList.addLog("Task 1");
		logList.addLog("Task 2");
        logList.setLog(1, "Set Task 2");
        assertEquals("Set Task 2", logList.getLog(1));
        
        //testing invalid index
        Exception e = assertThrows(IndexOutOfBoundsException.class, () ->
            logList.setLog(2, "Task 3"));
        assertEquals("Invalid index.", e.getMessage());
        
        Exception e1 = assertThrows(IndexOutOfBoundsException.class, () ->
            logList.setLog(-4, "Task 3"));
        assertEquals("Invalid index.", e1.getMessage());
		
        //testing null
        Exception e2 = assertThrows(NullPointerException.class, () ->
            logList.setLog(0, null));
        assertEquals("Cannot add null element.", e2.getMessage());
	}
	
	/**
	 * Tests the removeLog().
	 */
	@Test
	public void testRemoveLog() {
		logList.addLog("Task 1");
		logList.addLog("Task 2");
		String removed = logList.removeLog(0);
		assertEquals("Task 1", removed);
		assertEquals(1, logList.size());
		assertEquals("Task 2", logList.getLog(0));
		
		//testing invalid index
		Exception e = assertThrows(IndexOutOfBoundsException.class, () ->
            logList.removeLog(1));
        assertEquals("Invalid index.", e.getMessage());
        
        Exception e1 = assertThrows(IndexOutOfBoundsException.class, () ->
        logList.removeLog(-10));
        assertEquals("Invalid index.", e1.getMessage());
        
        
	}
	
	/**
	 * Tests the getLog().
	 */
	@Test
	public void testGetLog() {
		logList.addLog("Task 1");
	    logList.addLog("Task 2");
	    assertEquals("Task 1", logList.getLog(0));
	    assertEquals("Task 2", logList.getLog(1));

	    //testing invalid index
	    Exception e = assertThrows(IndexOutOfBoundsException.class, () ->
	       logList.getLog(2));
	    assertEquals("Invalid index.", e.getMessage());
	    
	    Exception e1 = assertThrows(IndexOutOfBoundsException.class, () ->
	       logList.getLog(-7));
	    assertEquals("Invalid index.", e1.getMessage());
	}
	
	/**
	 * Tests the size().
	 */
	@Test
	public void testSize() {
		assertEquals(0, logList.size());
		logList.addLog("Task 1");
		assertEquals(1, logList.size());
		logList.addLog("Task 2");
        assertEquals(2, logList.size());
		logList.removeLog(0);
		assertEquals(1, logList.size());
	}
	
	/**
	 * Tests the ensureCapacity() private method.
	 */
	@Test
    public void testEnsureCapacity() {
    
        assertEquals(0, logList.size());

        for (int i = 0; i < 10; i++) {
            logList.addLog("Task " + i);
        }
    
        // Check size and ensure no capacity issues at initial cap
        assertEquals(10, logList.size());
        assertEquals("Task 9", logList.getLog(9));

        logList.addLog("Task 10");
    
        assertEquals(11, logList.size());
        assertEquals("Task 10", logList.getLog(10));
    
        assertEquals("Task 0", logList.getLog(0));
        assertEquals("Task 9", logList.getLog(9));
	}
	
	

}
