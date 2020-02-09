package com.mycompany.baselake;

import com.mycompany.baselake.dataprocessing.*;
import com.mycompany.baselake.importer.CSVImporter;
import java.io.File;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author NicholasBocchini
 */
public class DatabaseTest extends AbstractTestClass {

    @Test
    public void testInit() throws IOException, DataObjectException
    {
        File test;
        Database database;
        CSVImporter reader;
        DataObjectArray<DataObject> results;
        
        test = new File("target/database/1111-eee-444-rrrd");
        delete(test);
        test.mkdirs();
        
        database = new Database(test, new File("target/database"), "TestData");
        database.init();
        
        reader = new CSVImporter(new File(test, "database.csv"));
        results = reader.read();
        reader.finish();
        
        assertEquals("target/database", results.getDataObject(0).getString("baselake"));
        assertEquals("target/database/1111-eee-444-rrrd", results.getDataObject(0).getString("root"));
        
        test = new File("target/database/1111-eee-444-rrrd/tables.csv");
        assertTrue(test.isFile());
    }
    
    @Test 
    public void testCreateTable() throws IOException, BaseLakeException, DataObjectException
    {
        File test;
        Database database;
        
        test = new File("target/database/222-333-ddd");
        delete(test);
        test.mkdirs();
        
        database = new Database(test, new File("target/database"), "TestCreate");
        database.init();
        database.createTable("TestTable");
        database.createTable("Table2");
        
        assertEquals(2, database.getTables().size());
        assertEquals("TestTable", database.getTables().getDataObject(0).getString("name"));
        assertEquals("Table2", database.getTables().getDataObject(1).getString("name"));
        
    }
    
    @Test
    public void testLoadTables() throws IOException, DataObjectException
    {
        Database database;
        
        database = new Database(new File("src/test/resources/database/BaseLake/2222-3333-aabb"), new File("src/test/resources/database/BaseLake"), "Data");
        database.loadTables();
        
        assertEquals(1, database.getTables().size());
        assertEquals("TestTable", database.getTables().getDataObject(0).getString("name"));
    }
    
    @Test
    public void testSaveTables() throws IOException, BaseLakeException, DataObjectException
    {
        File test;
        Database database;
        CSVImporter reader;
        DataObjectArray<DataObject> results;
        
        test = new File("target/database/222-333-ddd");
        delete(test);
        test.mkdirs();
        
        database = new Database(test, new File("target/database"), "TestCreate");
        database.init();
        database.createTable("TestTable");
        database.createTable("Table2");
        database.saveTables();
        
        test = new File("target/database/222-333-ddd/tables.csv");
        reader = new CSVImporter(test);
        results = reader.read();
        reader.finish();
        
        assertEquals(2, results.size());
        assertEquals("TestTable", results.getDataObject(0).getString("name"));
        assertEquals("Table2", results.getDataObject(1).getString("name"));
    }
    
    @Test
    public void testListTables() throws IOException, DataObjectException
    {
        Database database;
        String[] results;
        
        database = new Database(new File("src/test/resources/database/BaseLake/3333-4444-aabb"), new File("src/test/resources/database/BaseLake"), "List");
        database.loadTables();
        results = database.listTables();
        
        assertEquals(2, results.length);
        assertEquals("TestTable", results[0]);
        assertEquals("Table2", results[1]);
    }
    
}
