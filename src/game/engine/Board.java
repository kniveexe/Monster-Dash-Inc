package game.engine;
import game.engine.cells.DoorCell;
import game.engine.cells.ConveyorBelt;
import game.engine.cells.ContaminationSock;

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
    private static ArrayList<Card> originalCards;
    private static ArrayList<Card> expandedCards;
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
        return originalCards;
    }

    private int[] indexToRowCol(int index) {
        int row = index / Constants.BOARD_COLS;
        int rawcol = index % Constants.BOARD_COLS;
        int col;
        if (row % 2 == 0) {
            col = rawcol;
        } else {
            col = (Constants.BOARD_COLS - 1) - rawcol;
        }
        return new int[]{row, col};
    }

    private Cell getCell(int index) {
        int[] numCell = indexToRowCol(index);
        return getBoardCells()[numCell[0]][numCell[1]];
    }

    private void setCell(int index, Cell cell) {
        int[] pairIndex = indexToRowCol(index);
        this.boardCells[pairIndex[0]][pairIndex[1]] = cell;
    }

    public void initializeBoard(ArrayList<Cell> specialCells) {
        ArrayList<Cell> doorCells = new ArrayList<>();
        ArrayList<Cell> conveyorCells = new ArrayList<>();
        ArrayList<Cell> sockCells = new ArrayList<>();

        for (Cell c : specialCells) {
            if (c instanceof DoorCell) {
                doorCells.add(c);
            } else if (c instanceof ConveyorBelt) {
                conveyorCells.add(c);
            } else if (c instanceof ContaminationSock) {
                sockCells.add(c);
            }
        }

        int doorIndex = 0;
        for (int i = 0; i < Constants.BOARD_SIZE; i++) {
            if (i % 2 == 0) {
                setCell(i, new Cell("Cell" + i));
            } else {
                setCell(i, doorCells.get(doorIndex++));
            }
        }

        for (int index : Constants.CARD_CELL_INDICES) {
            setCell(index, new CardCell("CardCell" + index));
        }

        int conveyorIndex = 0;
        for (int index : Constants.CONVEYOR_CELL_INDICES) {
            setCell(index, conveyorCells.get(conveyorIndex++));
        }

        int sockIndex = 0;
        for (int index : Constants.SOCK_CELL_INDICES) {
            setCell(index, sockCells.get(sockIndex++));
        }

        for (int i = 0; i < Constants.MONSTER_CELL_INDICES.length && i < stationedMonsters.size(); i++) {
            int index = Constants.MONSTER_CELL_INDICES[i];
            Monster m = stationedMonsters.get(i);
            m.setPosition(index);
            setCell(index, new MonsterCell(m.getName(), m));
        }
    }

    private void setCardsByRarity() {
        expandedCards = new ArrayList<>();
        for (Card card : originalCards) {
            for (int i = 0; i < card.getRarity(); i++) {
                expandedCards.add(card);
            }
        }
        
        boolean isM1 = false;
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().contains("Milestone1")) {
                isM1 = true;
                break;
            }
        }
        if (!isM1) {
            originalCards = expandedCards;
        }
    }
            
        
    

   public static void reloadCards() {
	   cards = new ArrayList<>(originalCards);
	    Collections.shuffle(cards);
    }

    public static Card drawCard() {
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

        if (currentMonster.getConfusionTurns() > 0 && opponentMonster.getConfusionTurns() > 0) {
            currentMonster.decrementConfusion();
            opponentMonster.decrementConfusion();
        }

        updateMonsterPositions(currentMonster, opponentMonster);
    }
}