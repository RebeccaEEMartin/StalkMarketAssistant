package com.example.sam.services;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.example.sam.models.BuyingPrice;
import com.example.sam.models.Pattern;
import com.example.sam.models.Match;

@Service
public class PatternCalculator {

    private ArrayList<BuyingPrice> buyingPrices;
    private ArrayList<Pattern> patterns;

    public PatternCalculator() {
        patterns = new ArrayList<Pattern>();
    }

    public ArrayList<BuyingPrice> getBuyingPrices() {
        return buyingPrices;
    }

    public void setBuyingPrices(ArrayList<BuyingPrice> buyingPrices) {
        this.buyingPrices = buyingPrices;
    }

    public ArrayList<Pattern> getPatterns() {
        return patterns;
    }

    public void calculate(){

    }

    // checks if a list of prices matches the decresing pattern
    // _ v v v v v v v (v)*
    public Match checkDecreasing(ArrayList<Integer> prices){
        //if only 1 price then return potential match
        if (prices.size() <= 1) return Match.POTENTIAL;

        //check for increase, if increase (or price stays same) return no match
        for (int i=1; i<prices.size(); i++){
            int prev = prices.get(i-1);
            int curr = prices.get(i);
            if (curr >= prev) return Match.NO_MATCH;
        }

        //if list length < 8 then there is a potential match
        //else there is a match
        if (prices.size() < 8) return Match.POTENTIAL;
        else return Match.MATCH;
    }

    public Match checkBigSpike (ArrayList<Integer> prices){
        int i = 0; //counter used to keep track of list element

        //if only 1 price return potential match
        if(prices.size()<=1) return Match.POTENTIAL;

        //count decreasing prices
        while(i<prices.size()-1){
            int prev = prices.get(i);
            int curr = prices.get(i+1);
            if(curr >= prev) break;
            i++;
        }

        //if no more prices and <=6 decreasing prices (excluding first price) then return potential match
        if (i==prices.size()-1 && i<=6) return Match.POTENTIAL;
        //if no more prices and >7 decreasing prices (excluding first price) then return no match
        if (i==prices.size()-1 && i>6) return Match.NO_MATCH;

        //count increasing prices
        int incCount = 0;
        while(i<prices.size()-1){
            int prev = prices.get(i);
            int curr = prices.get(i+1);
            if(curr <= prev) break;
            i++;
            incCount++;
        }

        //if no more prices and <=2 increasing prices then return potential
        if (i==prices.size()-1 && incCount<=2) return Match.POTENTIAL;
        //if no more prices and 3 increasing prices and 3rd price >= 250 then return potential
        if (i==prices.size()-1 && incCount==3 && prices.get(i)>=250) return Match.POTENTIAL;

        return null;
    }
}