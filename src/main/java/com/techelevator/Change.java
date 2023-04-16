package com.techelevator;

public class Change {

    public Change(){}


    public int[] getAmount(double totalAmount) {
        int[] changeCount = new int[3];
        int quarter = 25;
        int dime = 10;
        int nickel = 5;
        int change = (int)(totalAmount * 100);
        int quarterCount = 0;
        int dimeCount = 0;
        int nickelCount = 0;


        if(change >= 0){
            quarterCount = change / 25;
            change = change % 25;
            dimeCount = change / 10;
            change = change % 10;
            nickelCount = change / 5;
            change = change % 5;
        }
        changeCount[0] = quarterCount;
        changeCount[1] = dimeCount;
        changeCount[2] = nickelCount;
        return changeCount;
    }

}
