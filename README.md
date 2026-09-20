# Monster Dash Inc.

A Java 21 game-engine project built around a **Monsters, Inc.-inspired board game**. The current repository focuses on the engine and domain model rather than a standalone graphical application.

The code models monsters, roles, cards, board cells, CSV-based game data, and the initial game setup used by the included JUnit tests.

## Current scope

The repository currently contains the game engine and tests. There is **no `public static void main(...)` application entry point** yet.

The main engine responsibilities include:

- Creating a 10 × 10 board
- Loading monsters, cards, and cells from CSV files
- Managing Scarer and Laugher roles
- Selecting player and opponent monsters
- Tracking monster energy, position, status effects, and role
- Modeling special cards and board cells
- Providing custom game exceptions
- Supporting JUnit 4 milestone tests

## Project structure

```text
Monster-Dash-Inc/
├── src/
│   └── game/
│       ├── engine/
│       │   ├── cards/
│       │   ├── cells/
│       │   ├── dataloader/
│       │   ├── exceptions/
│       │   ├── interfaces/
│       │   ├── monsters/
│       │   ├── Board.java
│       │   ├── Constants.java
│       │   ├── Game.java
│       │   └── Role.java
│       └── tests/
├── cards.csv
├── cells.csv
├── monsters.csv
├── .classpath
├── .project
├── .settings/
├── .gitignore
└── README.md
```

Generated class files and the `bin/` build directory are intentionally excluded from version control.

## Requirements

- **Java 21**
- Eclipse or another Java IDE
- **JUnit 4** for the included tests

The Eclipse project is configured for Java 21 and includes JUnit 4 in its classpath.

## Data files

The engine reads these files from the repository root:

- `cards.csv`
- `cells.csv`
- `monsters.csv`

Keep them in the repository root unless `DataLoader.java` is updated, because the loader currently opens them using relative filenames.

## Main packages

### `game.engine`

Core game state and configuration.

- `Game` creates the board, loads monsters, selects roles, and tracks the current player.
- `Board` stores the 10 × 10 cell grid and card/monster collections.
- `Constants` defines board positions, energy limits, and gameplay constants.
- `Role` represents the available monster roles.

### `game.engine.monsters`

Contains the base `Monster` class and specialized monster types such as:

- `Dasher`
- `Dynamo`
- `MultiTasker`
- `Schemer`

### `game.engine.cards`

Contains the card hierarchy, including:

- `ConfusionCard`
- `EnergyStealCard`
- `ShieldCard`
- `StartOverCard`
- `SwapperCard`

### `game.engine.cells`

Models different board-cell behaviors, including:

- `CardCell`
- `ContaminationSock`
- `ConveyorBelt`
- `DoorCell`
- `MonsterCell`
- `TransportCell`

### `game.engine.dataloader`

`DataLoader` parses the three CSV files and converts each row into the corresponding domain object.

### `game.engine.exceptions`

Contains game-specific checked exceptions for invalid actions, turns, movement, CSV data, and energy constraints.

## Run the tests in Eclipse

1. Clone the repository.
2. Open Eclipse.
3. Choose **File → Import → Existing Projects into Workspace**.
4. Select the repository folder.
5. Make sure the project is using **Java 21**.
6. Keep the working directory at the repository root so the CSV files can be found.
7. Right-click a test class under `src/game/tests/`.
8. Choose **Run As → JUnit Test**.

## Development notes

- Do not commit generated `.class` files.
- Do not commit the Eclipse `bin/` directory.
- Keep source code under `src/`.
- Keep the CSV data files at the project root unless the loader paths are changed.
- The current repository represents an engine/milestone implementation, not a finished playable UI.
