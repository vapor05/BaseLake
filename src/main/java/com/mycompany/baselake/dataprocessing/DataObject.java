package com.mycompany.baselake.dataprocessing;

import java.util.HashMap;

/**
 *
 * @author NicholasBocchini
 */
public class DataObject extends HashMap<String, Object> {
    
    public DataObject() {}
    
    public Object put(String key, Object value)
    {
        return super.put(key, value);
    }
    
    public String getString(String key) throws DataObjectException
    {
        Object value = super.get(key);
        
        if (value == null) throw new DataObjectException(value + " is not a valid string");
        
        return value.toString();
    }
    
    public int getInt(String key) throws DataObjectException
    {
        Object value = super.get(key);
        
        if (value instanceof Number) return ((Number)value).intValue();
        if (value instanceof String) return Integer.parseInt((String)value);
        
        throw new DataObjectException(value + " is not a valid integer");
    }
    
}
