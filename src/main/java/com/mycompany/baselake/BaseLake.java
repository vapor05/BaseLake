package com.mycompany.baselake;

import com.mycompany.baselake.dataprocessing.*;
import com.mycompany.baselake.exporter.CSVExporter;
import com.mycompany.baselake.importer.CSVImporter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author NicholasBocchini
 */
public class BaseLake {
    
    private File root;
    private File baseLakeFile;
    private File databaseFile;
    private DataObjectArray<DataObject> databases;
    
    public BaseLake(String root) throws IOException
    {
        this(new File(root));
    }
    
    public BaseLake(File root)
    {
        this.root = new File(root, "BaseLake");
        this.root.mkdirs();
        this.baseLakeFile = new File(this.root, "baselake.csv");
        this.databaseFile = new File(this.root, "databases.csv");
        this.databases = new DataObjectArray<DataObject>();
    }
    
    public DataObjectArray<DataObject> getDatabases()
    {
        return databases;
    }
    
    public File getRoot()
    {
        return root;
    }
    
    public void initBaseLake() throws IOException
    {
        CSVExporter out;
        
        out = new CSVExporter(baseLakeFile);
        out.write(new String[]{"root"}, new String[]{root.getPath()});
        out.finish();
        databaseFile.createNewFile();
    }
    
    public void createNewDatabase(String name) throws BaseLakeException, DataObjectException
    {
        DataObject object = new DataObject();
        UUID uuid = UUID.randomUUID();
        
        object.put("name", name);
        object.put("uuid", uuid);
        
        for (DataObject database : databases)
        {
            if (database.getString("name").equals(name)) 
            {
                throw new BaseLakeException("Database, "+ name +", already exists");
            } 
        }
        databases.add(object);
    }
    
    public void saveDatabases() throws IOException, DataObjectException
    {
        CSVExporter out;
        File newDatabaseFile;
        Database database;
        
        for (DataObject object : databases)
        {
            newDatabaseFile = new File(root, object.getString("uuid"));
            newDatabaseFile.mkdir();
            database = new Database(newDatabaseFile, root, object.getString("name"));
            database.init();
        }
        
        out = new CSVExporter(databaseFile);
        out.write(databases);
        out.finish();
    }
    
    public void loadBaseLake() throws IOException, DataObjectException
    {
        CSVImporter reader;
        
        reader = new CSVImporter(databaseFile);
        databases = reader.read();
        reader.finish();
    }
    
    public Database getDatabase(String name) throws BaseLakeException, DataObjectException, IOException
    {
        loadBaseLake();
        
        for (DataObject object : databases)
        {
            if (object.getString("name").equals(name)) 
            {
                return new Database(new File(root, object.getString("uuid")), root, name);
            }
        }
        throw new BaseLakeException("No Database with name, "+ name +", exists in this BaseLake");
    }
    
    public void dropDatabase(String name) throws IOException, DataObjectException, BaseLakeException
    {
        int index = 0;
        ArrayList<Integer> removeList = new ArrayList();
        
        loadBaseLake();
        
        for (DataObject object : databases)
        {
            if (object.getString("name").equals(name)) 
            {
                try
                {
                    delete(new File(root, object.getString("uuid")));
                }
                catch (Exception e)
                {
                    throw new BaseLakeException("Database, "+ name +", failed to be deleted");
                }
                
                removeList.add(index);
            }
            index++;
        }
        
        for (int i : removeList) databases.remove(i);
      
        saveDatabases();
    }
    
    public String[] listDatabases() throws IOException, DataObjectException
    {
        String [] databaseList;
        int index = 0;
        
        loadBaseLake();
        
        databaseList = new String[databases.size()];
        
        for (DataObject database : databases)
        {
            databaseList[index] = database.getString("name");
            index++;
        }
        
        return databaseList;
    }
    
    private void delete(File dir)
    {
        if (dir.exists())
        {
            for (File file : dir.listFiles())
            {
                if (file.isDirectory()) delete(file);
                file.delete();
            }
            dir.delete();
        }
    }
}
