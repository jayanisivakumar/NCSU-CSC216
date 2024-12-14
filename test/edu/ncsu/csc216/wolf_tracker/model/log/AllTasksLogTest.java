package edu.ncsu.csc216.wolf_tracker.model.log;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the functionality of the AllTasksLog class.
 */
class AllTasksLogTest {

	/** instance of allTasksLog. */
    private AllTasksLog allTasksLog;

    /**
     * Setting up allTasks log instance used for testing.
     */
    @BeforeEach
    public void setUp() {
        allTasksLog = new AllTasksLog();
    }

    /**
     * Test constructor to ensure the AllTasksLog is initialized with the correct name.
     */
    @Test
    public void testConstructor() {
        assertEquals(AllTasksLog.ALL_TASKS_NAME, allTasksLog.getName());
    }

    /**
     * Test setTaskLogName with the correct name, ensuring no exception is thrown.
     */
    @Test
    public void testSetTaskLogNameWithCorrectName() {
        // set task log name with correct name
        assertDoesNotThrow(() -> allTasksLog.setTaskLogName(AllTasksLog.ALL_TASKS_NAME));
        assertEquals(AllTasksLog.ALL_TASKS_NAME, allTasksLog.getName());
        
        // set task log name with incorrect name
        Exception e = assertThrows(IllegalArgumentException.class, 
            () -> allTasksLog.setTaskLogName("Invalid Task Log Name")
        );
        assertEquals("The All Tasks log may not be edited.", e.getMessage());
    }

    /**
     * Edge case: test if null is passed to setTaskLogName to ensure it throws an IllegalArgumentException.
     */
    @Test
    public void testSetTaskLogNameWithNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> allTasksLog.setTaskLogName(null)
        );
        assertEquals("The All Tasks log may not be edited.", e.getMessage());
    }
}
