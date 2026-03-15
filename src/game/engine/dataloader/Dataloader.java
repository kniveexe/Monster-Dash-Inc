package game.engine.dataloader;
	import java.io.*;
	import java.util.*;
	import game.engine.cards.*;
	import game.engine.cells.*;
	import game.engine.monsters.*;
	import game.engine.*;

	public class Dataloader {
	    public static final String CARDS_FILE_NAME = "cards.csv";
	    public static final String CELLS_FILE_NAME = "cells.csv";
	    public static final String MONSTERS_FILE_NAME = "monsters.csv";
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
	                case "DASHER" -> monsters.add(new Dasher(name, desc, role, energy));
	                case "DYNAMO" -> monsters.add(new Dynamo(name, desc, role, energy));
	                case "MULTITASKER" -> monsters.add(new MultiTasker(name, desc, role, energy));
	                case "SCHEMER" -> monsters.add(new Schemer(name, desc, role, energy));
	            }
	        }
	        br.close();
	        return monsters;
	    }
    
    // ... methods will go here
}


