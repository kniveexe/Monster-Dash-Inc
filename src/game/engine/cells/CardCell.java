package game.engine.cells;

import game.engine.monsters.Monster;
import game.engine.Board;
import game.engine.cards.Card;

public class CardCell extends Cell {

	public CardCell(String name) {
		super(name);
	}
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster); // Registers the monster on the cell
		Card card = Board.drawCard();
		card.performAction(landingMonster, opponentMonster);
	}
}