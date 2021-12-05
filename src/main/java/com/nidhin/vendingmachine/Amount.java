package com.nidhin.vendingmachine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Amount {
    /**
     * Class that represents an amount as coins of different denominations
     */

    //Maps each denomination to number of coins with that denomination
    private final Map<Integer, Integer> coins = new HashMap<>();

    public void addCoin(int denomination, int count) throws Exception {
        if (!coins.containsKey((denomination))) {
            coins.put(denomination, count);
        } else {
            coins.put(denomination, coins.get(denomination) + count);
        }
    }

    public void add(Amount amount) throws Exception {
        for (Map.Entry<Integer, Integer> e : amount.coins.entrySet()) {
            addCoin(e.getKey(), e.getValue());
        }
    }

    public int sum() {
        int ret = 0;
        for (Map.Entry<Integer, Integer> e : coins.entrySet()) {
            ret += e.getKey() * e.getValue();
        }
        return ret;
    }


    public void subtractCoin(int denomination, int count) throws Exception {
        if (!coins.containsKey(denomination)) {
            throw new Exception("Denomination not found!");
        }
        if (coins.get(denomination) < count) {
            throw new Exception("Not enough coins of denomination " + denomination);
        }
        coins.put(denomination, coins.get(denomination) - count);
    }

    public void subtract(Amount amount) throws Exception {
        for (Map.Entry<Integer, Integer> e : amount.coins.entrySet()) {
            subtractCoin(e.getKey(), e.getValue());
        }
    }

    public void subtract(int amount) throws Exception {
        if (sum() < amount) {
            throw new Exception("Not enough balance.");
        }
        Integer[] sorted = coins.keySet().toArray(new Integer[0]);
        Arrays.sort(sorted);
        int idx = sorted.length - 1;
        while (amount > 0) {
            if (amount >= sorted[idx]) {
                int ncoins = amount / sorted[idx];
                if (ncoins > coins.get(sorted[idx])) {
                    ncoins = coins.get(sorted[idx]);
                }
                subtractCoin(sorted[idx], ncoins);
                amount -= ncoins * sorted[idx];

            } else if (idx == 0) {
                throw new Exception("Smallest denomination available is: " + sorted[idx]);
            } else {
                idx -= 1;
            }
        }
    }

    public Amount copy() throws Exception {
        Amount ret = new Amount();
        for (Map.Entry<Integer, Integer> e : coins.entrySet()) {
            ret.addCoin(e.getKey(), e.getValue());
        }
        return ret;
    }

    public int numCoins() {
        int ret = 0;
        for (Map.Entry<Integer, Integer> e : coins.entrySet()) {
            ret += e.getValue();
        }
        return ret;
    }

    public void zero() {
        coins.clear();
    }
}
