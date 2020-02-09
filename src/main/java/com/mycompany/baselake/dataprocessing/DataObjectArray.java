package com.mycompany.baselake.dataprocessing;

import java.util.ArrayList;

/**
 *
 * @author NicholasBocchini
 */
public class DataObjectArray<T> extends ArrayList<T> {
    
    public DataObjectArray() {}
    
    public DataObject getDataObject(int index) throws DataObjectException
    {
        Object value = get(index);
        
        if (value instanceof DataObject) return (DataObject)value;
        
        throw new DataObjectException(value + "is not a valid DataObject");
    }
    
    @Override
    public boolean equals(Object object)
    {
        if (object instanceof DataObjectArray && super.equals(object)) return true;
        return false;
    }
}
