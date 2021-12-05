package com.nidhin.vendingmachine;

import com.nidhin.vendingmachine.exception.ItemNotFoundException;

import java.util.Arrays;

public class VendingMachine {
    int[] allowedDenominations = new int[]{1, 2, 5, 10};

    /* Stores info about coins stored in the machine */
    RestrictedDenominationAmount balance = new RestrictedDenominationAmount(allowedDenominations);

    /* Stores info about coins inserted by the user recently, but not added to main coin storage */
    RestrictedDenominationAmount userBalance = new RestrictedDenominationAmount(allowedDenominations);
    Inventory inventory = new Inventory();

    private final int MAX_QUANTITY = 120;
    private final int MAX_COINS = 500;

    //-----------------Supplier functionalities---------------------//
    public void addItem(String id, String name, int price) throws Exception {
        if (inventory.hasItem(id)) {
            throw new Exception("Item with same id already exists: " + id);
        }
        inventory.addNewItem(new Item(id, name, price));
    }

    public void addItem(String id, String name, int price, int quantity) throws Exception {
        addItem(id, name, price);
        inventory.addQuantity(id, quantity);
    }

    public void addQuantity(String itemId, int quantity) throws Exception {
        if (inventory.totalQuantity() + quantity > MAX_QUANTITY) {
            throw new Exception("Max limit reached.");
        }
        inventory.addQuantity(itemId, quantity);
    }

    public void removeQuantity(String itemId, int quantity) throws Exception {
        inventory.removeQuantity(itemId, quantity);
    }

    public int getQuantity(String itemId) throws Exception {
        return inventory.getQuantity(itemId);
    }

    public void addCoins(Amount amount) throws Exception {
        if (balance.numCoins() + amount.numCoins() > MAX_COINS) {
            throw new Exception("Max limit of coins reached.");
        }
        balance.add(amount);
    }

    //-----------HARDWARE INTERFACE-----------//
    public void dispenseItem(String id) {
        /**
         * dispense the item to the Pick-up box
         **/
    }

    public void returnCoinFromUserBuffer() {
        /**
         * returns last coin available in userBalance through Coin Return
         **/
    }

    public void acceptUserBuffer() {
        /**
         * moves all coins that are available in userBalance to the Main Coin Storage
         **/
    }

    public void returnAmount(Amount amount) {
        /**
         * returns an amount of coins(Balance) from the Main Coin Storage
         **/
    }

    public void displayMessage(String msg) {
        /**
         * Display the message
         **/
    }

    //------------------------------//

    //-----------------User functionalities---------------------//
    public void insertCoin(int denomination) throws Exception {
        if (!Arrays.asList(allowedDenominations).contains(denomination)) {
            returnCoinFromUserBuffer();
        } else {
            userBalance.addCoin(denomination, 1);
        }
    }

    private void returnUserBalance() {
        for (int i = 0; i < userBalance.numCoins(); i++) {
            returnCoinFromUserBuffer();
        }
        userBalance.zero();
    }

    public void selectItem(String itemId) throws Exception {
        if (!inventory.hasItem(itemId)) {
            throw new ItemNotFoundException("" + itemId);
        }
        if (userBalance.sum() < inventory.getItem(itemId).getPrice()) {
            displayMessage("Amount not enough!");
            returnUserBalance();
        } else if (inventory.getQuantity(itemId) < 1) {
            displayMessage("Item not available!");
            returnUserBalance();
        } else {
            if (balance.numCoins() + userBalance.numCoins() > MAX_COINS) {
                displayMessage("Can't accept anymore coins!");
                returnUserBalance();
            } else {
                acceptUserBuffer();
                balance.add(userBalance);
                dispenseItem(itemId);
                Amount userBalanceCopy = userBalance.copy();
                userBalance.zero();
                userBalanceCopy.subtract(inventory.getItem(itemId).getPrice());
                returnAmount(userBalanceCopy);
            }
        }
    }

    public void cancel() {
        returnUserBalance();
    }

}
