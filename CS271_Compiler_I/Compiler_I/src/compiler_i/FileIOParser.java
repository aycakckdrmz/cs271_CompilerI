/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler_i;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Ayca
 */
public class FileIOParser {   //parser handles file IO 
    private ArrayList <String> commandList = new ArrayList<String>();
    FileIOParser (String path ) throws FileNotFoundException{
        Scanner s = new Scanner(new File(path));
        ArrayList<String> list = new ArrayList<String>();
        while (s.hasNext()){
            list.add(s.nextLine());
        }
        s.close();
        commandList = list;
        System.out.println(list.size());
    }
    public ArrayList <String> getCommandList(){return commandList;}
    public void writeIntoFile(ArrayList <String> arr, String name) throws IOException{
        PrintWriter writer = new PrintWriter(name+".txt"); 
        for(String str: arr) {
          writer.println(str);
        }
        writer.close();
        
    }
    
    
}
