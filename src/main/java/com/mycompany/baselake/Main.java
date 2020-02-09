package com.mycompany.baselake;

import com.mycompany.baselake.dataprocessing.DataObjectException;
import java.io.IOException;

/**
 *
 * @author NicholasBocchini
 */
public class Main {
    
    public static void main(String... args) throws IOException, DataObjectException, BaseLakeException
    {
        BaseLake baselake;
        
        baselake = new BaseLake(args[0]);

        if (args[1].equals("init")) 
        {
            baselake.initBaseLake();
            System.out.println("New BaseLake initialized at " + baselake.getRoot().getPath());
        }
        else if (args[1].equals("create") && args[2].equals("database"))
        {
            baselake.loadBaseLake();
            baselake.createNewDatabase(args[3]);
            baselake.saveDatabases();
            System.out.println("Created new database " + args[3]);
        }
        else if (args[1].equals("drop") && args[2].equals("database"))
        {
            baselake.loadBaseLake();
            baselake.dropDatabase(args[3]);
            System.out.println("Database "+ args[3] +" was dropped");
        }
        else if (args[1].equals("list") && args.length == 2)
        {
            String[] databases;
            
            baselake.loadBaseLake();
            databases = baselake.listDatabases();
            
            System.out.println("All Databases in BaseLake...");
            for (String database : databases) System.out.println(database);
        }
        else if (args[1].equals("create") && args[2].equals("table"))
        {
            Database database;
            
            baselake.loadBaseLake();
            database = baselake.getDatabase(args[3]);
            database.loadTables();
            database.createTable(args[4]);
            database.saveTables();
            
            System.out.println("Created new table " + args[4] + " in database "+ args[3]);
        }
        else if (args[1].equals("list") && args.length == 3)
        {
            String[] tables;
            Database database;
            
            baselake.loadBaseLake();
            database = baselake.getDatabase(args[2]);
            tables = database.listTables();
            
            System.out.println("All Tables in Database "+ args[2] +"...");
            for (String table : tables) System.out.println(table);
        }
        else System.out.println("Unrecognized arguments passed. Pass either init or create database <name>");
    }
    
}
