package game.engine;



import java.util.ArrayList;
import game.engine.cards.Card;
import game.engine.monsters.Monster;
import game.engine.cells.Cell;



public class Board {
	
	private final Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters;
	private static  ArrayList<Card> originalCards;
	private static ArrayList<Card> cards;
	
	
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


	public static ArrayList<Card> getOriginalcards() {
		return originalCards;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}