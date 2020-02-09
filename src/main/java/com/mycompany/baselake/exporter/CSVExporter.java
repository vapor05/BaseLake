/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.baselake.exporter;

import com.mycompany.baselake.dataprocessing.DataObject;
import com.mycompany.baselake.dataprocessing.DataObjectArray;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @author NicholasBocchini
 */
public class CSVExporter {
    
    FileWriter out;
    boolean headerWritten;
    
    public CSVExporter(File out) throws IOException
    {
        this(out, false);
    }
    
    public CSVExporter(File out, boolean append) throws IOException
    {
        if (append) headerWritten = true;
        else 
        {
            headerWritten = false;
            out.getParentFile().mkdirs();
            out.createNewFile();
        }
        
        this.out = new FileWriter(out, append);
    }
    
    private void writeHeader(String[] keys) throws IOException
    {
        writeRow(keys);
        headerWritten = true;
    }
    
    public void write(String[] keys, Object[] values) throws IOException
    {
        if (!headerWritten) writeRow(keys);
        
        writeRow(values);
    }
    
    public void write(DataObject object) throws IOException
    {
        String[] keys;
        Object[] values;
        
        Set<String> keySet = object.keySet();
        Iterator<String> iter = keySet.iterator();
        
        keys = new String[keySet.size()];
        
        for (int i = 0; i < keys.length; i++) keys[i] = iter.next();
      
        values = new Object[keys.length];
        
        for (int i = 0; i < keys.length; i++) values[i] = object.get(keys[i]);
        
        if (!headerWritten) writeHeader(keys);
        
        writeRow(values);
        
    }
    
    private void writeRow(Object[] values) throws IOException
    {
        for (int i=0; i < values.length; i++)
        {
            if (i > 0) out.write(",");

            if (values[i] != null)
            {
                if (values[i] instanceof String)
                {
                    out.write("\"");
                    out.write(values[i].toString().replaceAll("\"", "\"\""));
                    out.write("\"");
                }
                else out.write(values[i].toString());
            }
        }
        out.write("\n");
    }
    
    public void write(DataObjectArray<DataObject> array) throws IOException
    {
        for (DataObject object : array)
        {
            write(object);
        }
    }
    
    public void finish() throws IOException
    {
        out.close();
    }
}
