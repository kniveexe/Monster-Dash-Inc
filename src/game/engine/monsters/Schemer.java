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
        // Always call setEnergy so passives (like Schemer's +10) fire
        target.setEnergy(target.getEnergy() - stealAmount);
        return stealAmount;
    }

   
    @Override
    public void setEnergy(int newEnergy) {
        super.setEnergy(newEnergy + 10);
    }

    @Override
    public void executePowerupEffect(Monster opponentMonster) {
        int totalStolen = 0;

        totalStolen += stealEnergyFrom(opponentMonster);

        for (Monster m : Board.getStationedMonsters()) {
            totalStolen += stealEnergyFrom(m);
        }

        setEnergy(getEnergy() + totalStolen);
    }
}