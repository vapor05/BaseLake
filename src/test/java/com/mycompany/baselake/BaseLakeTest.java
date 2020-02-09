package com.mycompany.baselake;

import com.mycompany.baselake.dataprocessing.*;
import com.mycompany.baselake.importer.CSVImporter;
import java.io.*;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 *
 * @author NicholasBocchini
 */
public class BaseLakeTest extends AbstractTestClass{
    
    @Test
    public void testInitBaseLake() throws IOException
    {
        BaseLake baseLake;
        File baseLakeFile;
        BufferedReader reader;
        String line;
        
        System.out.println("Testing initBaseLake...");
        delete(new File("target/BaseLake"));
        
        baseLake = new BaseLake("target/BaseLake");
        baseLake.initBaseLake();
        
        baseLakeFile = new File("target/BaseLake/BaseLake/baselake.csv");
        reader = new BufferedReader(new FileReader(baseLakeFile));
        
        line = reader.readLine();
        assertEquals("\"root\"", line);
        line = reader.readLine();
        assertEquals("\"target/BaseLake/BaseLake\"", line);
        
        reader.close();
    }
    
    @Test
    public void testSaveDatabases() throws IOException, DataObjectException, BaseLakeException
    {
        BaseLake baseLake;
        File test;
        CSVImporter reader;
        DataObjectArray<DataObject> results;
        
        System.out.println("Testing saveDatabase...");
        delete(new File("target/BaseLake"));
        baseLake = new BaseLake("target/BaseLake");
        
        baseLake.createNewDatabase("TestData");
        baseLake.createNewDatabase("A_NEW_DATABASE");
        baseLake.saveDatabases();
        
        test = new File("target/BaseLake/BaseLake/databases.csv");
        reader = new CSVImporter(test);
        results = reader.read();

        assertEquals("TestData", results.getDataObject(0).getString("name"));
        assertEquals("A_NEW_DATABASE", results.getDataObject(1).getString("name"));
        assertTrue(results.getDataObject(0).containsKey("uuid"));
        reader.finish();
        
        test = new File("target/BaseLake/BaseLake", results.getDataObject(0).getString("uuid"));
        assertTrue(test.isDirectory());        
    }
    
    @Test
    public void testLoadBaseLake() throws IOException, DataObjectException
    {
        File test;
        BaseLake baseLake;
        DataObjectArray<DataObject> results;
        
        System.out.println("Testing loadBaseLake...");
        test = new File("src/test/resources/baseLake");
        baseLake = new BaseLake(test);
        baseLake.loadBaseLake();

        results = baseLake.getDatabases();
        assertEquals(3, results.size());
        assertEquals("TestData", results.getDataObject(0).getString("name"));
        assertEquals("A_NEW_DATABASE", results.getDataObject(1).getString("name"));
        assertEquals("BigData", results.getDataObject(2).getString("name"));
    }
    
    @Test 
    public void testGetDatabase() throws BaseLakeException, DataObjectException, IOException
    {
        File test;
        BaseLake baselake;
        Database database;
        
        System.out.println("Testing getDatabase...");
        test = new File("src/test/resources/database");
        baselake = new BaseLake(test);
        assertEquals("Test", baselake.getDatabase("Test").getName());
    }
    
    @Test
    public void testDropDatabase() throws IOException, BaseLakeException, DataObjectException
    {
        File test;
        BaseLake baselake;
        DataObjectArray<DataObject> databases;
        File check;
        
        test = new File("target/drop");
        baselake = new BaseLake(test);
        baselake.initBaseLake();
        baselake.createNewDatabase("TestData");
        baselake.saveDatabases();
        databases = baselake.getDatabases();
        
        System.out.println("Testing dropDatabase...");
        check = new File(new File(test, "BaseLake"), databases.getDataObject(0).getString("uuid"));
        assertTrue(check.isDirectory());
        
        baselake.dropDatabase("TestData");
        assertFalse(check.isDirectory());

        assertEquals(0, baselake.getDatabases().size());
    }
    
    @Test
    public void testListDatabases() throws IOException, BaseLakeException, DataObjectException
    {
        BaseLake baselake;
        String[] results;
        
        System.out.println("Testing listDatabases...");
        baselake = new BaseLake(new File("target/list"));
        baselake.initBaseLake();
        baselake.createNewDatabase("TestData");
        baselake.createNewDatabase("DataTest");
        baselake.createNewDatabase("BigData");
        baselake.saveDatabases();
        results = baselake.listDatabases();

        assertEquals(3, results.length);
        assertTrue(Arrays.toString(results).contains("TestData"));
        assertTrue(Arrays.toString(results).contains("DataTest"));
        assertTrue(Arrays.toString(results).contains("BigData"));
        
    }
}
