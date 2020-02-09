package com.mycompany.baselake.dataprocessing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author NicholasBocchini
 */
public class DataObjectArrayTest {
    
    @Test
    public void testGetDataObject() throws DataObjectException
    {
        DataObject object = new DataObject();;
        DataObjectArray<DataObject> array = new DataObjectArray();
        DataObject result;

        object.put("key1", "value1");
        array.add(object);
        object = new DataObject();
        object.put("key2", 12);
        object.put("name", "John Smith");
        array.add(object);
        
        assertEquals(2, array.size());
        
        result = array.getDataObject(0);
        assertEquals("key1", result.keySet().toArray()[0]);
        assertEquals("value1", result.getString("key1"));
        
        result = array.getDataObject(1);
        assertEquals(12, result.get("key2"));
        assertEquals("John Smith", result.getString("name"));
    }
}
