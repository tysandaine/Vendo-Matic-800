package com.techelevator.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VenLog {

    private static PrintWriter logWriter = null;

    public static void log(String message){

        try  {
            //(PrintWriter logWriter = new PrintWriter(new FileOutputStream(file,true));
            if (logWriter==null){
                File file = new File(System.getProperty("user.dir") + "/log.txt ");
                logWriter = new PrintWriter(new FileOutputStream(file,true));
            }
            logWriter.println(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " " + message);
            logWriter.flush();

        } catch(FileNotFoundException e){
            System.out.println("File does not exist.");
        }
    }
}
