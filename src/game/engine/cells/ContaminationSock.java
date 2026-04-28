package game.engine.cells;

import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;
import game.engine.Constants;

public class ContaminationSock extends TransportCell implements CanisterModifier {

	public ContaminationSock(String name, int effect) {
		super(name, -Math.abs(effect));
	}

	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue); // Using alterEnergy respects the shield
	}

	@Override
	public void transport(Monster monster) {
	    super.transport(monster); // moves the position
	    if (monster.isShielded()) {
	        monster.setShielded(false); // consume shield, energy unchanged
	    } else {
	        modifyCanisterEnergy(monster, -Constants.SLIP_PENALTY); // lose energy
	    }
	}

	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
	    super.onLand(landingMonster, opponentMonster);
	    transport(landingMonster); // shield + energy logic now inside transport()
	}}