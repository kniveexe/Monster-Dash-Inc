package game.engine;


import java.util.ArrayList;
import game.engine.cards.Card;
import game.engine.monsters.Monster;
import game.engine.cells.Cell;
import game.engine.cells.CardCell;
import game.engine.cells.MonsterCell;
import java.util.Collections;
import game.engine.exceptions.InvalidMoveException;


public class Board {
	
	private final Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters;
	private static  ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	
	public Board(ArrayList<Card> readCards) {
	    boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
	    stationedMonsters = new ArrayList<>();
	    cards = new ArrayList<>();
	    originalCards = readCards;
	    setCardsByRarity();
	    reloadCards();
	}


	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}


	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}


	public static ArrayList<Card> getCards() {
		return cards;
	}


	public static void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}


	public Cell[][] getBoardCells() {
		return boardCells;
	}


	public static ArrayList<Card> getOriginalCards() {
		return originalCards;}
	

    private int[] indexToRowCol(int index) {
    	int row = index/Constants.BOARD_COLS;
    	int rawcol = index%Constants.BOARD_COLS;
    	int col = 0;
    	if(row%2 == 0) {
    	col  = rawcol;
    		
    	}else {
    		
    		col = (Constants.BOARD_COLS-1)-rawcol;
    	}
    	
    	int [] pair = {row,col};
    	
    	
    	return pair;
    	
    }
	
    private Cell getCell(int index) {
    	
    int [] numCell = indexToRowCol(index);
    int row = numCell[0];
    int col = numCell[1];
    return getBoardCells()[row][col];
    
    	
    }
	
    private void setCell(int index, Cell cell) {
    	int [] pairIndex = indexToRowCol(index);
    	int row = pairIndex[0];
    	int col = pairIndex[1];
    	this.boardCells[row][col] = cell;
    	
    	
    	
    }
	
    public void initializeBoard(ArrayList<Cell> specialCells) {
        int specialIndex = 0;

        // Step 1: Fill even = plain Cell, odd = DoorCell
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            if (i % 2 == 0) {
                setCell(i, new Cell("Cell" + i));
            } else {
                setCell(i, specialCells.get(specialIndex++));
            }
        }

        // Step 2: Place CardCells
        for (int index : Constants.CARD_CELL_INDICES) {
            setCell(index, new CardCell("CardCell" + index));
        }

        // Step 3: Place ConveyorBelts
        for (int index : Constants.CONVEYOR_CELL_INDICES) {
            setCell(index, specialCells.get(specialIndex++));
        }

        // Step 4: Place ContaminationSocks
        for (int index : Constants.SOCK_CELL_INDICES) {
            setCell(index, specialCells.get(specialIndex++));
        }

        // Step 5: Place MonsterCells + assign monster positions
        for (int i = 0; i < Constants.MONSTER_CELL_INDICES.length; i++) {
            int index = Constants.MONSTER_CELL_INDICES[i];
            Monster m = stationedMonsters.get(i);
            m.setPosition(index);
            setCell(index, new MonsterCell("MonsterCell" + index, m));
        }
    }
	
    static void reloadCards() {
        cards = new ArrayList<>(originalCards);
        Collections.shuffle(cards);
    }

    static Card drawCard() {
        if (cards.isEmpty()) {
            reloadCards();
        }
        return cards.remove(0);
    }

    private void updateMonsterPositions(Monster player, Monster opponent) {
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            getCell(i).setMonster(null);
        }
        getCell(player.getPosition()).setMonster(player);
        getCell(opponent.getPosition()).setMonster(opponent);
    }

    public void moveMonster(Monster currentMonster, int roll, Monster opponentMonster) throws InvalidMoveException {
        int oldPosition = currentMonster.getPosition();
        currentMonster.move(roll);
        int newPosition = currentMonster.getPosition();

        if (newPosition == opponentMonster.getPosition()) {
            currentMonster.setPosition(oldPosition);
            throw new InvalidMoveException();
        }

        getCell(newPosition).onLand(currentMonster, opponentMonster);

        if (currentMonster.getPosition() == opponentMonster.getPosition()) {
            currentMonster.setPosition(oldPosition);
            throw new InvalidMoveException();
        }

        if (currentMonster.getConfusionTurns() > 0 || opponentMonster.getConfusionTurns() > 0) {
            currentMonster.decrementConfusion();
            opponentMonster.decrementConfusion();
        }

        updateMonsterPositions(currentMonster, opponentMonster);
    }
    
    private void setCardsByRarity() {
        ArrayList<Card> expanded = new ArrayList<>();
        for (Card card : originalCards) {
            for (int i = 0; i < card.getRarity(); i++) {
                expanded.add(card);
            }
        }
        originalCards = expanded;
    }
    
    
    
    
    
    
    
}
	
	
	
	
	

