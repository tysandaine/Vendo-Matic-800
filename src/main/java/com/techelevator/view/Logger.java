package com.techelevator.view;

import com.techelevator.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Logger {

    public Map<String, List<Product>> readFile(){
        Map<String, List<Product>> inventory = new TreeMap<>();
        try{
            Scanner vending = new Scanner(new File (System.getProperty("user.dir") + "/vendingmachine.csv"));
            while (vending.hasNextLine()){
                String[] line = vending.nextLine().split("\\|");
                Product product = new Product(line[1], Double.parseDouble(line[2]), line[3], 5);
                List<Product> list = new ArrayList<>();
                list.add(product);
                list.add(product);
                list.add(product);
                list.add(product);
                list.add(product);
                inventory.put(line[0], list);
            }
        } catch(FileNotFoundException e){
            System.out.println("File was not found.");
        }
        return inventory;
    }
}
