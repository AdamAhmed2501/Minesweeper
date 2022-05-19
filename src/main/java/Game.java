import java.util.Scanner;

public class Game {
    private Scanner scan;
    private Grid Grid;

    public Game() {
        scan = new Scanner(System.in);
        Grid = new Grid(10,10);
        Grid.generateMines(10);
    }

    public void startGame() {
        boolean isFlagging;
        Position inputPosition;
        do {
            Grid.printGrid();
            Grid.printStatus();
            inputPosition = getPositionInput();
            isFlagging = getStringOrQuit().equalsIgnoreCase("flag");
            if(isFlagging) {
                Grid.flagTile(inputPosition);
            } else if(Grid.isTileFlagged(inputPosition)) {
                System.out.println("You need to un-flag that tile first.");
            } else {
                Grid.revealTile(inputPosition);
            }
        } while(!Grid.isWon() && (isFlagging || !Grid.isTileMine(inputPosition)));
        Grid.revealAll();
        Grid.printGrid();
        if(Grid.isWon()) {
            System.out.println("Victory! You revealed all the safe tiles!");
        } else {
            System.out.println("Boom! You hit a mine! :(");
        }
    }

    public boolean isPositionInputValid(Position position) {
        if (!Grid.validPosition(position)) {
            System.out.println("Coordinate not inside the play space!");
            return false;
        }
        if(Grid.isTileRevealed(position)) {
            System.out.println("That cell is already revealed!");
            return false;
        }
        return true;
    }

    public Position getPositionInput() {
        Position input = new Position(0,0);
        do {
            System.out.println("Enter space separated X (bottom) then Y (right) coordinate: ");
            if(!scan.hasNextInt()) {
                getStringOrQuit();
                System.out.println("Invalid X coordinate.");
                continue;
            }
            input.x = scan.nextInt();
            if(!scan.hasNextInt()) {
                getStringOrQuit();
                System.out.println("Invalid Y coordinate.");
                continue;
            }
            input.y = scan.nextInt();
            input.x--;
            input.y--;
        } while(!isPositionInputValid(input));
        return input;
    }

    public String getStringOrQuit() {
        String input = scan.nextLine().trim();
        if(input.equalsIgnoreCase("quit")) {
            System.out.println("Thanks for playing!");
            System.exit(0);
        }
        return input;
    }
}
