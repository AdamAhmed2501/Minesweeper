import java.awt.*;

public class Tile {
    private boolean isRevealed;
    private boolean isMine;
    private int neighbours;
    private boolean isFlagged;

//  private static final String ANSI_RESET = "\u001B[0m";
//  private static final String ANSI_RED = "\u001B[31m";
//  private static final String ANSI_GREEN = "\u001B[32m";
//  private static final String ANSI_YELLOW = "\u001B[33m";
//  private static final String ANSI_BLUE = "\u001B[34m";
//  private static final String ANSI_PURPLE = "\u001B[35m";

    public Tile() {
        resetTile();
    }

    public void resetTile() {
        isRevealed = false;
        isMine = false;
        neighbours = 0;
        isFlagged = false;
    }

    public void reveal() {
        isRevealed = true;
    }

    public boolean getIsRevealed() {
        return isRevealed;
    }

    public void setAsBomb() {
        isMine = true;
    }

    public boolean getIsBomb() {
        return isMine;
    }

    public void addNeighbour() {
        neighbours++;
    }

    public int getNeighbours() {
        return neighbours;
    }

    public boolean getIsFlagged() {
        return isFlagged;
    }

    public void toggleIsFlagged() {
        isFlagged = !isFlagged;
    }

    public String toString() {
        if(isRevealed) {
            if(isMine) {
                return "B";
            } else {
                return ""+neighbours;
            }
        } else if(isFlagged) {
            return "F";
        } else {
            return "*";
        }
    }






















    public void drawTile(Graphics g, int x, int y, int cellSize) {
        if(isRevealed) {
            if(neighbours == 0) {
                return;
            }
            g.setColor(getColourForCell());
            g.drawString(toString(), x+10, y+21);
        } else {
            if(isFlagged) {
                g.setColor(Color.yellow);
            } else {
                g.setColor(Color.darkGray);
            }
            g.fillRect(x+3, y+3, cellSize - 5, cellSize - 5);
        }
    }

    private Color getColourForCell() {
        if(isMine) {
            if(isFlagged) return Color.yellow;
            else return Color.red;
        } else if(neighbours == 1) return Color.blue;
        else if(neighbours == 2) return Color.green;
        else if(neighbours == 3) return Color.red;
        else if(neighbours == 4) return new Color(128, 0, 128);
        else if(neighbours == 5) return new Color(128, 0, 0);
        else if(neighbours == 6) return new Color(0,0,128);
        else if(neighbours == 7) return Color.pink;
        else return Color.black;
    }
}








//  public class Tile {
//
//      //Attributes
//
//      boolean[][] ArrFlag = {{false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false}};
//
//      boolean[][] ArrMine = {{false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false},
//                             {false, false, false, false, false, false, false, false}};
//
//      int[][] ArrTileMineContacts = {{0, 0, 0, 0, 0, 0, 0, 0},
//                                     {0, 0, 0, 0, 0, 0, 0, 0},
//                                     {0, 0, 0, 0, 0, 0, 0, 0},
//                                     {0, 0, 0, 0, 0, 0, 0, 0},
//                                     {0, 0, 0, 0, 0, 0, 0, 0},
//                                     {0, 0, 0, 0, 0, 0, 0, 0},
//                                     {0, 0, 0, 0, 0, 0, 0, 0},
//                                     {0, 0, 0, 0, 0, 0, 0, 0}};
//      int x;
//      int y;
//      boolean Flag = ArrFlag [x][y];
//      boolean Mine = ArrMine [x][y];
//      int TileMineContacts = ArrTileMineContacts [x][y];
//
//
//
//      //Constructor
//      public Tile(int x, int y){
//          this.x = x;
//          this.y = y;
//      }
//
//      //Methods
//      public int countMineContacts(int x, int y){
//
//          int runningTotal = ArrTileMineContacts[x][y];
//
//          if (getMine(x, y + 1) == true){ runningTotal = runningTotal + 1;};
//
//          if (getMine(x + 1, y + 1) == true){ runningTotal = runningTotal + 1;};
//
//          if (getMine(x + 1, y) == true){ runningTotal = runningTotal + 1;};
//
//          if (getMine(x + 1, y - 1) == true){ runningTotal = runningTotal + 1;};
//
//          if (getMine(x, y - 1) == true){ runningTotal = runningTotal + 1;};
//
//          if (getMine(x - 1, y - 1) == true){ runningTotal = runningTotal + 1;};
//
//          if (getMine(x - 1, y) == true){ runningTotal = runningTotal + 1;};
//
//          if (getMine(x - 1, y + 1) == true){ runningTotal = runningTotal + 1;};
//
//             return runningTotal;
//
//      }
//
//      public void setTileContacts(){
//         for (int i = 0; i < ArrTileMineContacts.length; ++i) {
//             for (int j = 0; j < ArrTileMineContacts[i].length; ++j) {
//                      TileMineContacts = countMineContacts(i,j);
//             }
//         }
//      }
//
//      public boolean getFlag(int x, int y){
//          return ArrFlag[x][y];
//      }
//
//      public boolean getMine(int x, int y){
//          return ArrMine[x][y];
//      }
//
//      public void setFlag(int x, int y){
//          ArrFlag[x][y] = !ArrFlag[x][y];
//      }
//
//      public void setMine(int x, int y){
//          ArrMine[x][y] = !ArrMine[x][y];
//      }
//  }
