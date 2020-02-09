
package com.mycompany.baselake.exporter;

import com.mycompany.baselake.AbstractTestClass;
import com.mycompany.baselake.dataprocessing.DataObject;
import com.mycompany.baselake.dataprocessing.DataObjectArray;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author NicholasBocchini
 */
public class CSVExporterTest extends AbstractTestClass {
    
    
    @Test
    public void testWriteString() throws IOException
    {
        File test;
        CSVExporter out;
        String[] keys;
        Object[] values;
        BufferedReader reader;
        String line;
        
        System.out.println("Testing write...");
        delete(new File("target/exporter"));
        keys = new String[] {"column1", "column2", "column3"};
        values = new Object[] {"value1","value2",12};
        
        test = new File("target/exporter/write.csv");
        out = new CSVExporter(test);
        out.write(keys, values);
        out.finish();
        
        reader = new BufferedReader(new FileReader(test));
        line = reader.readLine();  
        assertEquals("\"column1\",\"column2\",\"column3\"", line);
        line = reader.readLine();  
        assertEquals("\"value1\",\"value2\",12", line);
        reader.close();
    }
    
    @Test
    public void testWriteObject() throws IOException
    {
        File test;
        CSVExporter out;
        DataObject object;
        BufferedReader reader;
        String line;
        
        System.out.println("Testing write DataObject...");
        delete(new File("target/exporter"));
        object = new DataObject();
        object.put("key1", "value1");
        object.put("key2", "value2");
        object.put("key3", 5);
       
        test = new File("target/exporter/write.csv");
        out = new CSVExporter(test);
        out.write(object);
        out.finish();
        
        reader = new BufferedReader(new FileReader(test));
        line = reader.readLine();  
        assertEquals("\"key1\",\"key2\",\"key3\"", line);
        line = reader.readLine();  
        assertEquals("\"value1\",\"value2\",5", line);
        reader.close();
    }
    
    @Test
    public void testWriteArray() throws IOException
    {
        File test;
        CSVExporter out;
        DataObjectArray array = new DataObjectArray();
        DataObject object;
        BufferedReader reader;
        String line;
        
        System.out.println("Testing write DataObjectArray...");
        delete(new File("target/exporter"));
        object = new DataObject();
        object.put("key1", "value1");
        object.put("key2", "value2");
        object.put("key3", 5);
        array.add(object);
        object = new DataObject();
        object.put("key1", "value3");
        object.put("key2", "value4");
        object.put("key3", 6);
        array.add(object);
        object = new DataObject();
        object.put("key1", "value5");
        object.put("key2", "value6");
        object.put("key3", 7);
        array.add(object);
        
        test = new File("target/exporter/write.csv");
        out = new CSVExporter(test);
        out.write(array);
        out.finish();
        
        reader = new BufferedReader(new FileReader(test));
        line = reader.readLine();  
        assertEquals("\"key1\",\"key2\",\"key3\"", line);
        line = reader.readLine(); 
        System.out.println(line);
        assertEquals("\"value1\",\"value2\",5", line);
        line = reader.readLine();  
        assertEquals("\"value3\",\"value4\",6", line);
        line = reader.readLine();  
        assertEquals("\"value5\",\"value6\",7", line);
        reader.close();
    }
}
