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
			String[] parts = line.split(",");
			String monsterType = parts[0];
			String name = parts[1];
			String description = parts[2];
			Role role = Role.valueOf(parts[3]);
			int energy = Integer.parseInt(parts[4]);

			if (monsterType.equals("DASHER")) {
				monsters.add(new Dasher(name, description, role, energy));
			} else if (monsterType.equals("DYNAMO")) {
				monsters.add(new Dynamo(name, description, role, energy));
			} else if (monsterType.equals("MULTITASKER")) {
				monsters.add(new MultiTasker(name, description, role, energy));
			} else if (monsterType.equals("SCHEMER")) {
				monsters.add(new Schemer(name, description, role, energy));
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
			String[] parts = line.split(",");
			String cardType = parts[0];
			String name = parts[1];
			String description = parts[2];
			int rarity = Integer.parseInt(parts[3]);

			if (cardType.equals("SWAPPER")) {
				cards.add(new SwapperCard(name, description, rarity));
			} else if (cardType.equals("SHIELD")) {
				cards.add(new ShieldCard(name, description, rarity));
			} else if (cardType.equals("ENERGYSTEAL")) {
				int energy = Integer.parseInt(parts[4]);
				cards.add(new EnergyStealCard(name, description, rarity, energy));
			} else if (cardType.equals("STARTOVER")) {
				boolean lucky = Boolean.parseBoolean(parts[4]);
				cards.add(new StartOverCard(name, description, rarity, lucky));
			} else if (cardType.equals("CONFUSION")) {
				int duration = Integer.parseInt(parts[4]);
				cards.add(new ConfusionCard(name, description, rarity, duration));
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
			String[] parts = line.split(",");
			String name = parts[0];

			if (parts.length == 3) {
				Role role = Role.valueOf(parts[1]);
				int energy = Integer.parseInt(parts[2]);
				cells.add(new DoorCell(name, role, energy));
			} else if (parts.length == 2) {
				int effect = Integer.parseInt(parts[1]);
				if (effect > 0) {
					cells.add(new ConveyorBelt(name, effect));
				} else {
					cells.add(new ContaminationSock(name, effect));
				}
			}
		}
		br.close();
		return cells;
	}
}