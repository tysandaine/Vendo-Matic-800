package com.techelevator;

import com.techelevator.util.VenLog;
import com.techelevator.view.Logger;
import com.techelevator.view.Menu;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	private Menu menu;
	private Logger log = new Logger();

	public Map<String, List<Product>> getInventory() {
		return inventory;
	}

	private Map<String, List<Product>> inventory = log.readFile();

//	public VendingMachineCLI(){}



	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {
		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				for(Map.Entry<String, List<Product>> entry: inventory.entrySet()){
					Product item = entry.getValue().get(1);
					System.out.printf("%-5s%-20s%-5.2f%-8s%3d\n", entry.getKey(), item.getName(), item.getPrice(), item.getItem(), item.getCounter());
//					System.out.println(entry.getKey() + "\t " + item.getName() + "\t " + item.getPrice() + "\t " + item.getItem() + "\t " + item.getCounter());
				}
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				runPurchase();
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)){
				// exit code
				System.exit(1);
			}
		}
	}

	public void runPurchase(){
		double totalAmount = 0.0;
		Scanner userInput = new Scanner(System.in);
		while(true) {
			String choiceTwo = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

			if(choiceTwo.equals(PURCHASE_MENU_OPTION_FEED_MONEY)){
				//Customer is allowed to insert while dollar up to $10
				System.out.println("Please enter amount (Whole Numbers): ");
				double input = userInput.nextDouble();
				if(input <0 || input %1 != 0){
					System.out.println("Invalid Number");
				} else {
					totalAmount += input;
					VenLog.log("FEED MONEY: " + "$" + input + "\t$" + totalAmount);
				}
			} else if (choiceTwo.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)){
				for(Map.Entry<String, List<Product>> entry: inventory.entrySet()){
					Product item = entry.getValue().get(1);
					String output = (item.getCounter() == 0) ? " Sold Out " : "%-20s%-5.2f%-8s%3d\n";
					System.out.printf( entry.getKey() + output, " " + item.getName(), item.getPrice(), item.getItem(), item.getCounter());
				}
				// shows a list of products and allows customer to enter code to select item
				Scanner stringInput = new Scanner(System.in);
				System.out.println("\nSelect a product to purchase by slot ID (A1,A2, ETC...): ");
				String input = stringInput.nextLine();
				input = input.toUpperCase(Locale.ROOT);
				totalAmount = getTotalAmount(totalAmount, input);
				VenLog.log(input + " " + inventory.get(input).get(0).getName() + " $" + (inventory.get(input).get(0).getPrice() + totalAmount) +  " $" + totalAmount);
			} else if (choiceTwo.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)){
				// completes the transaction customer receives any change and is returned to main menu
				NumberFormat formatter = NumberFormat.getCurrencyInstance();
				String moneyString = formatter.format(totalAmount);
				Change getChange = new Change();
				int[] changeCount = getChange.getAmount(totalAmount);
				double refund = totalAmount;
				System.out.println("Change back: " + moneyString);
				System.out.println("Quarters: " + changeCount[0] + " Dimes: " + changeCount[1] + " Nickels: " + changeCount[2]);
				totalAmount -= refund;
				System.out.println("Remaining Balance: " + totalAmount);
//				totalAmount = Change.getAmount(totalAmount, moneyString);
				VenLog.log("GIVE CHANGE: " + moneyString + " " + totalAmount);
				run();
			}
			NumberFormat formatter = NumberFormat.getCurrencyInstance();
			String moneyString = formatter.format(totalAmount);
//			System.out.println(moneyString);
			System.out.println("\n\nCurrent Money Provided: " + moneyString);
		}
	}



	private double getTotalAmount(double totalAmount, String input) {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		String moneyString = formatter.format(totalAmount);
		if(inventory.containsKey(input)){
			if (inventory.get(input).get(0).getCounter() == 0){
				System.out.println("SOLD OUT");
			} else {
				if(totalAmount < inventory.get(input).get(0).getPrice()){
					System.out.println("Insufficient Funds");
				} else {
					inventory.get(input).get(0).decCounter();
					totalAmount -= (inventory.get(input).get(0).getPrice());
					if (inventory.get(input).get(0).getItem().equals("Chip")) {
						System.out.println("Crunch Crunch, Yum!");
					}
					if (inventory.get(input).get(0).getItem().equals("Candy")) {
						System.out.println("Munch Munch, Yum!");
					}
					if (inventory.get(input).get(0).getItem().equals("Drink")) {
						System.out.println("Glug Glug, Yum!");
					}
					if (inventory.get(input).get(0).getItem().equals("Gum")) {
						System.out.println("Chew Chew, Yum!");
					}
				}
			}
		} else {
			System.out.println("Product does not exist.");
			System.out.printf("Here is your refund: $%.2f\n", totalAmount);
			runPurchase();
		}
		return totalAmount;
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();

		Menu purchaseMenu = new Menu(System.in, System.out);
		VendingMachineCLI cliPurchase = new VendingMachineCLI(purchaseMenu);
		cliPurchase.runPurchase();

	}
}
