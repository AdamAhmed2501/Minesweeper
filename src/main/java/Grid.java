import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.*;


public class Grid {
    private Tile[][] Tiles;
    private int width, height;
    private int mineCount, revealedTotal;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        Tiles = new Tile[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tiles[x][y] = new Tile();
            }
        }
        mineCount = 0;
        revealedTotal = 0;
    }

    public void printGrid() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                System.out.print(Tiles[x][y]+ "  ");
            }
            System.out.println(" |" + (y + 1));
        }
        for (int x = 0; x < width; x++) {
            System.out.print("_  ");
        }
        System.out.println();
        for (int x = 0; x < width; x++) {
            System.out.print((x + 1) + " ");
            if (x + 1 < 10) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    public void revealTile(Position position) {
        if (Tiles[position.x][position.y].getNeighbours() != 0) {
            revealedTotal++;
            Tiles[position.x][position.y].reveal();
        } else {
            List<Position> revealedTiles = floodFillReveal(position);
            for (Position p : revealedTiles) {
                revealAllAroundPoint(p);
            }
        }
    }

    public boolean validPosition(Position position) {
        return position.x >= 0 && position.y >= 0 && position.x < width && position.y < height;
    }

    public boolean isTileRevealed(Position position) {
        return Tiles[position.x][position.y].getIsRevealed();
    }

    public boolean isTileMine(Position position) {
        return Tiles[position.x][position.y].getIsBomb();
    }

    public boolean isTileFlagged(Position position) {
        return Tiles[position.x][position.y].getIsFlagged();
    }

    public void flagTile(Position position) {
        Tiles[position.x][position.y].toggleIsFlagged();
    }

    public boolean addMine(Position position) {
        if (isTileMine(position)) {
            return false;
        }

        int minX = Math.max(0, position.x - 1);
        int maxX = Math.min(width - 1, position.x + 1);
        int minY = Math.max(0, position.y - 1);
        int maxY = Math.min(height - 1, position.y + 1);
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                Tiles[x][y].addNeighbour();
            }
        }

        Tiles[position.x][position.y].setAsBomb();
        mineCount++;
        return true;
    }

    public void generateMines(int maxMines) {
        Random rand = new Random();
        for (int i = 0; i < maxMines; i++) {
            addMine(new Position(rand.nextInt(width), rand.nextInt(height)));
        }
    }

    public void revealAll() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tiles[x][y].reveal();
            }
        }
    }

    public boolean isWon() {
        return revealedTotal + mineCount == width * height;
    }

    public void printStatus() {
        System.out.println(revealedTotal + " revealed of " + (width * height)
                + " with " + mineCount + " bombs!");
    }

    private void revealAllAroundPoint(Position position) {
        int minX = Math.max(0, position.x - 1);
        int maxX = Math.min(width - 1, position.x + 1);
        int minY = Math.max(0, position.y - 1);
        int maxY = Math.min(height - 1, position.y + 1);
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (!Tiles[x][y].getIsRevealed() && Tiles[x][y].getNeighbours() != 0) {
                    Tiles[x][y].reveal();
                    revealedTotal++;
                }
            }
        }
    }

    private void checkFloodFillToTile(Position position, boolean[][] vis, Queue<Position> positionQueue) {
        if (validPosition(position)) {
            if (!vis[position.x][position.y] && !isTileRevealed(position)
                    && Tiles[position.x][position.y].getNeighbours() == 0) {
                positionQueue.add(position);
            }
            vis[position.x][position.y] = true;
        }
    }

    private List<Position> floodFillReveal(Position position) {
        boolean[][] vis = new boolean[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                vis[x][y] = false;
            }
        }

        List<Position> changedPoints = new ArrayList<>();
        Queue<Position> positionQueue = new LinkedList<>();
        positionQueue.add(position);
        vis[position.x][position.y] = true;

        while (!positionQueue.isEmpty()) {
            Position positionToReveal = positionQueue.remove();
            Tiles[positionToReveal.x][positionToReveal.y].reveal();
            revealedTotal++;
            changedPoints.add(positionToReveal);

            checkFloodFillToTile(new Position(positionToReveal.x + 1, positionToReveal.y), vis, positionQueue);
            checkFloodFillToTile(new Position(positionToReveal.x - 1, positionToReveal.y), vis, positionQueue);
            checkFloodFillToTile(new Position(positionToReveal.x, positionToReveal.y + 1), vis, positionQueue);
            checkFloodFillToTile(new Position(positionToReveal.x, positionToReveal.y - 1), vis, positionQueue);
        }
        return changedPoints;
    }
}



//  public class Grid {
//
//      // Attributes
//
//      int Rows = 8;
//
//      int Columns = 8;
//
//      int Mines = 5;
//
//      int Flags;
//
//      // 2-D Array for the game board and obstacles and
//      private int[][] ArrGrid;
//
//
//
//      // Constructors
//
//      /**
//       * Initialing the game's main board (grid).
//       */
//
//      public void createGrid() {
//          this.ArrGrid = new int[Rows][Columns];
//          for(int i = ArrGrid.length - 1; i >= 0; i--) {
//              System.out.println();
//              for(int j = 0; j < ArrGrid.length; j++) {
//                  ArrGrid[i][j] = i * Rows + j + 1;
//                   System.out.print(ArrGrid[i][j] + "\t");
//              }
//          }
//      }







    








    // Constructor


    // Methods




//    int length;
//    int height;
//
//    //Constructor
//
//    public Grid(int length, int height){
//        this.length = length;
//        this.height = height;
//
//    }
//
//
//    //Methods
//    public int getgridsize(){
//        return(this.length);
//    }
