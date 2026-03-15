package game.engine.interfaces;

import game.engine.monsters.Monster;

/**
 * Interface for any object that can modify a monster's energy canister.
 */
public interface CanisterModifier {

    /**
     * Modifies the energy of the given monster.
     *
     * @param monster the monster to apply the energy change to
     */
    void modifyEnergy(Monster monster);
}