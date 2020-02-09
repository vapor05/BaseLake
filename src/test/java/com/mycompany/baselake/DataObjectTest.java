package com.mycompany.baselake;

import com.mycompany.baselake.dataprocessing.DataObject;
import com.mycompany.baselake.dataprocessing.DataObjectException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author NicholasBocchini
 */
public class DataObjectTest {
    
    @Test
    public void testGetString() throws DataObjectException
    {
        DataObject object;
        
        
        object = new DataObject();
        object.put("key", "data");
        object.put("key2", null);
        
        assertEquals(object.getString("key"), "data");
        
        Throwable exception = assertThrows(DataObjectException.class, () -> object.getString("key2"));
        assertEquals("null is not a valid string", exception.getMessage());
    }
}
