package com.mycompany.baselake;

import java.io.File;

/**
 *
 * @author NicholasBocchini
 */
public abstract class AbstractTestClass {
    
    protected void delete(File dir)
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
