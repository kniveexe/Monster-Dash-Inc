package game.engine;


import java.util.ArrayList;
import game.engine.cards.Card;
import game.engine.monsters.Monster;
import game.engine.cells.Cell;



public class Board {
	
	private final Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters;
	private static  ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	
	public Board(ArrayList<Card> readCards) {
		boardCells = new Cell [Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<>();
		cards = new ArrayList<>();
		originalCards = readCards;
		
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
	
    void initializeBoard(ArrayList<Cell> specialCells) {
    	
    }
    
    
	
	
}
	
	
	
	
	

