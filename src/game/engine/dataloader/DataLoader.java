package game.engine.dataloader;

import java.io.*;
import java.util.*;
import game.engine.cards.*;
import game.engine.cells.*;
import game.engine.monsters.*;
import game.engine.*;

public class DataLoader {

    public static final String CARDS_FILE_NAME = "cards.csv";
    public static final String CELLS_FILE_NAME = "cells.csv";
    private static final String MONSTERS_FILE_NAME = "monsters.csv";

    public static ArrayList<Monster> readMonsters() throws IOException {
        ArrayList<Monster> monsters = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(MONSTERS_FILE_NAME));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            String type = data[0];
            String name = data[1];
            String desc = data[2];
            Role role = Role.valueOf(data[3]);
            int energy = Integer.parseInt(data[4]);
            switch (type) {
                case "DASHER"      -> monsters.add(new Dasher(name, desc, role, energy));
                case "DYNAMO"      -> monsters.add(new Dynamo(name, desc, role, energy));
                case "MULTITASKER" -> monsters.add(new MultiTasker(name, desc, role, energy));
                case "SCHEMER"     -> monsters.add(new Schemer(name, desc, role, energy));
            }
        }
        br.close();
        return monsters;
    }

    public static ArrayList<Card> readCards() throws IOException {
        ArrayList<Card> cards = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(CARDS_FILE_NAME));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            String type = data[0];
            String name = data[1];
            String desc = data[2];
            int rarity  = Integer.parseInt(data[3]);
            switch (type) {
                case "SWAPPER"     -> cards.add(new SwapperCard(name, desc, rarity));
                case "SHIELD"      -> cards.add(new ShieldCard(name, desc, rarity));
                case "ENERGYSTEAL" -> cards.add(new EnergyStealCard(name, desc, rarity, Integer.parseInt(data[4])));
                case "STARTOVER"   -> cards.add(new StartOverCard(name, desc, rarity, Boolean.parseBoolean(data[4])));
                case "CONFUSION"   -> cards.add(new ConfusionCard(name, desc, rarity, Integer.parseInt(data[4])));
            }
        }
        br.close();
        return cards;
    }

    public static ArrayList<Cell> readCells() throws IOException {
        ArrayList<Cell> cells = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(CELLS_FILE_NAME));
        String line;
        while ((line = br.readLine()) != null) {
            String[] data = line.split(",");
            String type = data[0];
            String name = data[1];
            switch (type) {
                case "DOORCELL"          -> cells.add(new DoorCell(name, Role.valueOf(data[2]), Integer.parseInt(data[3])));
                case "CONVEYORBELT"      -> cells.add(new ConveyorBelt(name, Integer.parseInt(data[2])));
                case "CONTAMINATIONSOCK" -> cells.add(new ContaminationSock(name, Integer.parseInt(data[2])));
            }
        }
        br.close();
        return cells;
    }
}