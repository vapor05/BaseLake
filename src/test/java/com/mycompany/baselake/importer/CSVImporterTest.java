package com.mycompany.baselake.importer;

import com.mycompany.baselake.dataprocessing.DataObject;
import com.mycompany.baselake.dataprocessing.DataObjectArray;
import com.mycompany.baselake.dataprocessing.DataObjectException;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author NicholasBocchini
 */
public class CSVImporterTest {
    
    @Test
    public void testReadObject() throws IOException, DataObjectException
    {
        File test;
        CSVImporter importer;
        DataObjectArray results;
        DataObject object;
        
        System.out.println("Testing CSV read...");
        test = new File("src/test/resources/importer/import.csv");
        importer = new CSVImporter(test);
        results = importer.read();
        
        assertEquals(results.size(), 3);
        
        object = results.getDataObject(0);
        assertEquals("John Smith", object.getString("name"));
        assertEquals(3, object.getInt("number"));
        assertEquals("140 West Leaf Drive", object.getString("address"));
        
        object = results.getDataObject(1);
        assertEquals("Jan Doe", object.getString("name"));
        assertEquals(35, object.getInt("number"));
        assertEquals("1 North Lane", object.getString("address"));
        
        object = results.getDataObject(2);
        assertEquals("Max Reed", object.getString("name"));
        assertEquals(67, object.getInt("number"));
        assertEquals("798 End Lane", object.getString("address"));
        
        importer.finish();
    }
    
    @Test
    public void testEmptyFile() throws IOException, DataObjectException
    {
        File test;
        CSVImporter importer;
        DataObjectArray<DataObject> results;
        
        test = new File("src/test/resources/importer/empty.csv");
        importer = new CSVImporter(test);
        results = importer.read();
        
        assertEquals(0, results.size());
    }
}
