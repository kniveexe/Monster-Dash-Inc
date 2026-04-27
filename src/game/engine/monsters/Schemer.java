package game.engine.monsters;

import game.engine.Role;
import game.engine.Constants;
import game.engine.Board;

public class Schemer extends Monster {

    public Schemer(String name, String description, Role originalRole, int energy) {
        super(name, description, originalRole, energy);
    }

    private int stealEnergyFrom(Monster target) {
        int stealAmount = Constants.SCHEMER_STEAL;
        if (target.getEnergy() < stealAmount) {
            stealAmount = target.getEnergy();
        }
        
        target.setEnergy(target.getEnergy() - stealAmount);
        return stealAmount;
    }

    @Override
    public void setEnergy(int newEnergy) {
        int change = newEnergy - getEnergy();
        if (change != 0) {
            // Gains +10 energy on every incoming energy change
            super.setEnergy(newEnergy + 10);
        } else {
            super.setEnergy(newEnergy);
        }
    }

    @Override
    public void executePowerupEffect(Monster opponentMonster) {
        int totalStolen = 0;
        
        // Steals from opponent
        totalStolen += stealEnergyFrom(opponentMonster);
        
        // Steals from all stationed monsters
        for (Monster m : Board.getStationedMonsters()) {
            totalStolen += stealEnergyFrom(m);
        }
        
        // Gains a single total steal bonus at the end
        setEnergy(getEnergy() + totalStolen);
    }
}