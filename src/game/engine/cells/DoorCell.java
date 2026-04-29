package game.engine.cells;

import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;
import game.engine.Board;

public class DoorCell extends Cell implements CanisterModifier {

    private final Role role;
    private final int canisterEnergy;
    private boolean activated;

    public DoorCell(String name, Role role, int energy) {
        super(name);
        this.role = role;
        this.canisterEnergy = energy;
        this.activated = false;
    }

    public Role getRole() { return role; }
    public int getEnergy() { return canisterEnergy; }
    public boolean isActivated() { return activated; }
    public void setActivated(boolean activated) { this.activated = activated; }

    @Override
    public void modifyCanisterEnergy(Monster monster, int canisterValue) {
        // Force absolute value so we don't accidentally double-negate
        int amount = Math.abs(canisterValue); 
        
        if (monster.getRole() == this.role) {
            monster.alterEnergy(amount); // Roles match: heal
        } else {
            // Roles do not match: penalty
            if (monster.isShielded()) {
                monster.setShielded(false); // Break shield, take no damage
            } else {
                monster.alterEnergy(-amount); // Take damage
            }
        }
    }

    @Override
    public void onLand(Monster landingMonster, Monster opponentMonster) {
        super.onLand(landingMonster, opponentMonster);

        if (!this.activated) {
            boolean isTrap = (landingMonster.getRole() != this.role);
            boolean wasShielded = landingMonster.isShielded();

            // 1. Apply the effect to the landing monster.
            // If it's a trap and they are shielded, modifyCanisterEnergy breaks the shield here.
            modifyCanisterEnergy(landingMonster, this.canisterEnergy);

            // 2. If it was a trap, AND the landing monster absorbed it with a shield, 
            // we skip the team damage loop AND we leave the door UNACTIVATED.
            if (isTrap && wasShielded) {
                // The door cell MUST NOT be activated here according to the test.
                return; 
            }

            // 3. Otherwise, the effect ripples to the rest of the team
            if (Board.getStationedMonsters() != null) {
                for (Monster m : Board.getStationedMonsters()) {
                    // Ensure we don't accidentally hit the landing monster a second time
                    if (m != landingMonster && m.getRole() == landingMonster.getRole()) {
                        modifyCanisterEnergy(m, this.canisterEnergy);
                    }
                }
            }

            // 4. Mark as activated only if a shield didn't stop it
            this.activated = true;
        }
    }
}