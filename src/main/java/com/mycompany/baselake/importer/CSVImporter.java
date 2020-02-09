package com.mycompany.baselake.importer;

import com.mycompany.baselake.dataprocessing.DataObject;
import com.mycompany.baselake.dataprocessing.DataObjectArray;
import com.mycompany.baselake.dataprocessing.DataObjectException;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author NicholasBocchini
 */
public class CSVImporter {
    
    private BufferedReader reader;
    private DataObject columns;
    
    public CSVImporter(File csvFile) throws FileNotFoundException, IOException
    {
        Scanner parseLine;
        String line;
        int index = 0;
        
        columns = new DataObject();
        reader = new BufferedReader(new FileReader(csvFile));
        line = reader.readLine();
        
        if (line != null) 
        {
            parseLine = new Scanner(line).useDelimiter(",");
            while (parseLine.hasNext())
            {
                columns.put(Integer.toString(index), parseLine.next().replaceAll("\"", "").trim());
                index++;
            }

            parseLine.close();
        }
    }
    
    private DataObject read(String line) throws IOException, DataObjectException
    {
        Scanner parseLine;
        DataObject result = new DataObject();
        int index = 0;

        parseLine = new Scanner(line).useDelimiter(",");
        while (parseLine.hasNext())
        {
            result.put(columns.getString(Integer.toString(index)), parseLine.next().replaceAll("\"", "").trim());
            index++;
        }

        parseLine.close();
        return result;
    }
    
    public DataObjectArray<DataObject> read() throws IOException, DataObjectException
    {
        String line;
        DataObjectArray results = new DataObjectArray();
        
        while ((line = reader.readLine()) != null)
        {
            results.add(read(line));
        }
        return results;
    }
    
    public void finish() throws IOException
    {
        reader.close();
    }
}
