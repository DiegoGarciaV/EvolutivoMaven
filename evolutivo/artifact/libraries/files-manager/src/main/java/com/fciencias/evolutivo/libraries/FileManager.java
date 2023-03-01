package com.fciencias.evolutivo.libraries;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class FileManager {

    private Map<String,BufferedOutputStream> filesOutputBuffer;
    private Map<Long,String> filesIndex;
    private long currentKey = 0;

    public FileManager()
    {
        filesIndex = new HashMap<>();
        filesOutputBuffer = new HashMap<>();
    }

    public Long openFile(String file, boolean append)
    {
        if(filesIndex.containsValue(file))
        {
            for(Map.Entry<Long,String> fileTuple : filesIndex.entrySet())
            {
                if(fileTuple.getValue().equals(file))
                    return fileTuple.getKey();
            }
        }
        else
        {
            try 
            {
                BufferedOutputStream newBufferedOutStream = new BufferedOutputStream(new FileOutputStream(file,append));
                filesOutputBuffer.put(file, newBufferedOutStream);
                filesIndex.put(currentKey, file);
                currentKey++;
                return currentKey - 1;

            }
            catch (FileNotFoundException e) 
            {
                e.printStackTrace();
            } 
           
        }
        return -1L;
    }

    public void closeFile(long index)
    {
        try 
        {
            filesOutputBuffer.get(filesIndex.get(index)).close();
        } 
        catch (IOException e) 
        {    
            e.printStackTrace();
        }
    }

    public void writeFile(long index, String newText,boolean appendFile)
    {

        BufferedOutputStream bufferedWriter = null;
        if(appendFile)
            bufferedWriter = filesOutputBuffer.get(filesIndex.get(index));
        else
            try {
                bufferedWriter = new BufferedOutputStream(new FileOutputStream(filesIndex.get(index),appendFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        
        try 
        {
            if(bufferedWriter!= null)
            {
                bufferedWriter.write(newText.getBytes());
                bufferedWriter.flush();
            }
                
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        finally
        {
            if(!appendFile && bufferedWriter!= null)
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        
       
    }

    public void writeLine(long index, String newText)
    {
        writeFile(index,"\n" + newText,true);
    }

    public String readFile(long index)
    {
        int bufferSize = 1024<<4;
        byte[] newLine = new byte[bufferSize];
        int lineLength = 0;

        try(BufferedInputStream bufferedInputStream =  new BufferedInputStream(new FileInputStream((filesIndex.get(index))),bufferSize))
        {    
            
            lineLength = bufferedInputStream.read(newLine);

        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }

        byte[] readedLine = Arrays.copyOf(newLine, lineLength > 0 ? lineLength : 0);
        return new String(readedLine).replace("\r\n","\n");

    }

    public String readFileLine(long index, int line)
    {
        String[] lines =  readFile(index).split("\n");
        return lines[line];
    }
    
}
