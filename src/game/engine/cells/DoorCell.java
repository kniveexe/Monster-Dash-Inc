package game.engine.cells;

import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;
import game.engine.Board;

public class DoorCell extends Cell implements CanisterModifier {
	private final Role role;
	private final int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);	
		this.role = role;
		this.energy = energy;
		this.activated = false;
	}

	public Role getRole() { return role; }
	public int getEnergy() { return energy; }
	public boolean isActivated() { return activated; }
	public void setActivated(boolean activated) { this.activated = activated; }

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue); // Respects the shield mechanic
	}

	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		
		if (!activated) {
		    int energyChange = (landingMonster.getRole() == this.role) ? this.energy : -this.energy;
		    boolean wasShielded = landingMonster.isShielded();

		    modifyCanisterEnergy(landingMonster, energyChange);

		    boolean wasBlocked = wasShielded && energyChange < 0;

		    if (!wasBlocked) {
		        for (Monster m : Board.getStationedMonsters()) {
		            if (m.getRole() == landingMonster.getRole()) {
		                modifyCanisterEnergy(m, energyChange);
		            }
		        }
		        this.activated = true;
		    }
		}}}