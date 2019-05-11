// David Gonzales
package woffortune;

import java.util.ArrayList;

/**
 * This class defines a Wheel for the Wheel of Fortune game
 * @author clatulip
 */
public class Wheel {
    // enumerated type, wheel wedges can be any of these
    public enum WedgeType {MONEY, BANKRUPT, LOSE_TURN, PRIZE}
    // the type for the current sping
    private WedgeType spinType;
    // if a money wedge, the amount
    private int spinDollarAmount;
    // list of wedges
    private ArrayList<Wedge> wedges = new ArrayList<Wedge>();
    
   

    /**
     * Constructor
     * Creates the wheel
     */
    public Wheel() {
        // put a bankrupt wedge on the wheel
        wedges.add(new Wedge(WedgeType.BANKRUPT));
        
        // put a lose-turn wedge on the wheel
        wedges.add(new Wedge(WedgeType.LOSE_TURN));
        
        // put 20 money wedges on the wheel
        for (int i = 1; i < 20; i++) {
            wedges.add(new Wedge(WedgeType.MONEY));
        }
        
        // 5 Put 6 prize wedges on the wheel
        for(int i =1; i< 6;i++){
        wedges.add(new Wedge(WedgeType.PRIZE));
        }

    }
    
    /**
     * Spins the wheel
     * @return WedgeType 
     */
    public WedgeType spin() {
        int index = (int)(Math.random()*wedges.size());
        spinType = wedges.get(index).getType();
        spinDollarAmount = wedges.get(index).getAmount();
        return spinType;
    }
    
    /**
     * Getter
     * For when the spin lands on a money wedge
     * @return int the amount of money on the wedge
     */
    public int getAmount() {
        return spinDollarAmount;
    }
    
}
