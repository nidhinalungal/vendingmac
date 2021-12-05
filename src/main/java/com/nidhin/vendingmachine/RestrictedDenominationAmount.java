package com.nidhin.vendingmachine;

import java.util.HashSet;
import java.util.Set;

public class RestrictedDenominationAmount extends Amount {
    private final Set<Integer> allowedDenominations = new HashSet<>();
    public RestrictedDenominationAmount(int[] allowedDenominations){
        for (int d: allowedDenominations){
            this.allowedDenominations.add(d);
        }
    }
    private void validateDenomination(int denomination) throws Exception{
        if (!allowedDenominations.contains(denomination)){
            throw new Exception("Denomination not allowed: " + denomination);
        }
    }
    @Override
    public void addCoin(int denomination, int count) throws Exception{
        validateDenomination(denomination);
        super.addCoin(denomination, count);

    }

}
