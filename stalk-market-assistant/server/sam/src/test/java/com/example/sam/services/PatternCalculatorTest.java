package com.example.sam.services;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.DayOfWeek;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.Matchers.equalTo;
import com.example.sam.models.BuyingPrice;
import com.example.sam.models.TimeOfDay;
import com.example.sam.models.Pattern;
import com.example.sam.models.Match;
import java.util.Arrays;
import com.example.sam.services.PatternCalculator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PatternCalculatorTest {

    PatternCalculator patternCalculator;

    @Before
    public void setUp(){
        patternCalculator = new PatternCalculator();
    }

    /*
     * When given a list of prices that has the potential to match the following pattern: _ v v v v v v v (v)*  with
     * length < 12 the return array contains decreasing
     * */

    /*
     * When given a list of prices matching the following pattern: _ v v v v v v v (v)*  with length = 12 the
     * return array contains only decreasing
     * */


    /*
     * When given a list of prices matching the following pattern: _ (v)* ^ ^ ^ v v (v)* with length < 12 the return
     * array contains only big spike and random
     * */

    /*
     * When given a list of prices that has the potential to match the following pattern: _ (v)* ^ ^ ^ v v (v)*  with
     * length < 12 the return array contains big spike
     * */

    /*
     * When given a list of prices matching the following pattern:  _ (v)* ^ ^ ^ v v (v)*  with length = 12 the
     * return array contains only big spike
     * */

    /*
     * When given a list of prices matching the following pattern: _ (v)* ^ ^ ^ ^ v (v)* with length < 12 the return
     * array contains only small spike and random
     * */

    /*
     * When given a list of prices that has the potential to match the following pattern: _ (v)* ^ ^ ^ ^ v (v)*  with
     * length < 12 the return array contains small spike
     * */

    /*
     * When given a list of prices matching the following pattern:  _ (v)* ^ ^ ^ ^ v (v)*  with length = 12 the
     * return array contains only small spike
     * */

    /*
     * When given a list of prices with no potential to match the following patterns:
     *   decreasing: _ v v v v v v v (v)*
     *   big spike: _ (v)* ^ ^ ^ v v (v)*
     *   small spike: _ (v)* ^ ^ ^ ^ v (v)*
     * then the return array contains only random
     * */

    /*
     * When given a list of one price checkDecrease returns POTENTIAL
     * */
    @Test
    public void checkDecreasingTestPotentialOnePrice(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(90));
        Match m = patternCalculator.checkDecreasing(prices);
        assertThat(m, equalTo(Match.POTENTIAL));
    }

    /*
     * When given a list which contains an increase checkDecrease returns NO_MATCH
     * */
    @Test
    public void checkDecreasingTestNoMatch(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(90,95));
        Match m = patternCalculator.checkDecreasing(prices);
        assertThat(m, equalTo(Match.NO_MATCH));
    }

    /*
     * When given a list of prices with potential to match the following pattern: _ v v v v v v v (v)* (i.e. 7 or less
     * prices in decreasing order) checkDecrease returns POTENTIAL
     * */
    @Test
    public void checkDecreasingTestPotential(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(90,85,80,75,70,65,60));
        Match m = patternCalculator.checkDecreasing(prices);
        assertThat(m, equalTo(Match.POTENTIAL));
    }

    /*
     * When given a list of prices matching the following pattern: _ v v v v v v v (v)* (i.e. 8 or more prices in
     * decreasing order) checkDecrease returns MATCH
     * */
    @Test
    public void checkDecreasingTestMatch(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(90,85,80,75,70,65,60,55));
        Match m = patternCalculator.checkDecreasing(prices);
        assertThat(m, equalTo(Match.MATCH));
    }

    /*
     * When given a list of one price checkBigSpike returns POTENTIAL
     * */
    @Test
    public void checkBigSpikeTestOnePrice(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(100));
        Match m = patternCalculator.checkBigSpike(prices);
        assertThat(m, equalTo(Match.POTENTIAL));
    }

    /*
     * When given a list of prices matching the following pattern: _ (v)* with max length 7 (i.e. <= 7 prices in
     * decreasing order) checkBigSpike returns POTENTIAL
     * */
    @Test
    public void checkBigSpikeTestInitialDecrease(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(100,95,90,85,80,75,70));
        Match m = patternCalculator.checkBigSpike(prices);
        assertThat(m, equalTo(Match.POTENTIAL));
    }

    /*
     * When given a list of 8 or more decreasing prices, checkBigSpike returns NO_MATCH
     * */
    @Test
    public void checkBigSpikeTestDecreasing(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(100,95,90,85,80,75,70,65));
        Match m = patternCalculator.checkBigSpike(prices);
        assertThat(m, equalTo(Match.NO_MATCH));
    }

    /*
     * When given a list of prices with potential to match the following pattern: _ (v)* ^ ^ (i.e. less than 8 prices
     * in decreasing order followed by up to 2 increasing prices) checkBigSpike returns POTENTIAL
     * */
    @Test
    public void checkBigSpikeTestInitialIncrease(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(100,150,200));
        Match m = patternCalculator.checkBigSpike(prices);
        assertThat(m, equalTo(Match.POTENTIAL));
    }

    /*
     * When given a list of prices with potential to match the following pattern: _ (v)* ^ ^ ^ (i.e. less than 8 prices
     * in decreasing order followed by 3 increasing prices) and the 3rd price is >=250 checkBigSpike returns POTENTIAL
     * */
    @Test
    public void checkBigSpikeTestSpikeGreater250(){
        ArrayList<Integer> prices = new ArrayList<>(Arrays.asList(100,150,200,300));
        Match m = patternCalculator.checkBigSpike(prices);
        assertThat(m, equalTo(Match.POTENTIAL));
    }

    /*
     * When given a list of prices with potential to match the following pattern: _ (v)* ^ ^ ^ (i.e. less than 8 prices
     * in decreasing order followed by 3 increasing prices) and the 3rd price is <250 checkBigSpike returns NO_MATCH
     * */

    /*
     * When given a list of prices which begins with up to 7 decreasing prices followed by up to 3 prices which are not
     * increasing, checkBigSpike returns NO_MATCH
     * */

    /*
     * When given a list of prices with potential to match the following pattern: _ (v)* ^ ^ ^ v v (i.e. less than 8
     * prices in decreasing order followed by 3 increasing prices followed by less than 2 decreasing prices)
     * checkBigSpike returns POTENTIAL
     * */

    /*
     * When given a list of prices which begins with up to 7 decreasing prices followed by 3 increasing prices followed
     * by up to 2 prices which are not decreasing, checkBigSpike returns NO_MATCH
     * */

    /*
     * When given a list of prices matching the following pattern: _ (v)* ^ ^ ^ v v (v)* (i.e. less than 8 prices in
     * decreasing order followe by 3 increasing prices followed by 2 decreasing prices followed by 0 or more decreasing
     * prices) checkBigSpike returns MATCH
     * */

    /*
     * When given a list of prices which begins with up to 7 decreasing prices followed by 3 increasing prices followed
     * by 2 decreasing prices followed by prices which are not decreasing, checkBigSpike returns NO_MATCH
     * */



}