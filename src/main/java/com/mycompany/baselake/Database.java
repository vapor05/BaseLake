package com.mycompany.baselake;

import com.mycompany.baselake.dataprocessing.*;
import com.mycompany.baselake.exporter.CSVExporter;
import com.mycompany.baselake.importer.CSVImporter;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author NicholasBocchini
 */
public class Database {
    private File root;
    private File baselake;
    private String name;
    private File databaseFile;
    private File tablesFile;
    private DataObjectArray<DataObject> tables;
    
    public Database(String root, String baselake, String name)
    {
        this(new File(root), new File(baselake), name);
    }
    
    public Database(String root, File baselake, String name)
    {
        this(new File(root), baselake, name);
    }
    
    public Database(File root, File baselake, String name)
    {
        this.root = root;
        this.baselake = baselake;
        this.name = name;
        databaseFile = new File(root, "database.csv");
        tablesFile = new File(root, "tables.csv");
        tables = new DataObjectArray();
    }
    
    public String getName()
    {
        return name;
    }
    
    public DataObjectArray<DataObject> getTables()
    {
        return tables;
    }
    
    public void init() throws IOException
    {
        CSVExporter out;
        DataObject object;
        
        object = new DataObject();
        object.put("name", name);
        object.put("baselake", baselake.getPath());
        object.put("root", root.getPath());
        
        out = new CSVExporter(databaseFile);
        out.write(object);
        out.finish();
        
        tablesFile.createNewFile();
    }
    
    public void loadTables() throws IOException, DataObjectException
    {
        CSVImporter reader;
        
        reader = new CSVImporter(tablesFile);
        tables = reader.read();
        reader.finish();
    }
    
    public void createTable(String tableName) throws BaseLakeException, DataObjectException
    {
        DataObject object = new DataObject();
        UUID uuid = UUID.randomUUID();
        
        object.put("database", name);
        object.put("name", tableName);
        object.put("uuid", uuid);
        
        for (DataObject table : tables)
        {
            if (table.getString("name").equals(tableName))
            {
                throw new BaseLakeException("Table, "+ name +", already exists in Database "+ tableName);
            }
        }
        tables.add(object);        
    }
    
    public void saveTables() throws DataObjectException, IOException
    {
        CSVExporter out;
        File newTableFile;
        
        for (DataObject table : tables)
        {
            newTableFile = new File(root, table.getString("uuid"));
            newTableFile.mkdir();
        }
        
        out = new CSVExporter(tablesFile);
        out.write(tables);
        out.finish();
    }
    
    public String[] listTables() throws IOException, DataObjectException
    {
        String[] results;
        int index = 0;
        
        loadTables();
        
        results = new String[tables.size()];
        
        for (DataObject table : tables)
        {
            results[index] = table.getString("name");
            index++;
        }
        
        return results;
    }
}
