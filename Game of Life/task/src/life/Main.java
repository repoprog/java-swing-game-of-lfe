package life;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int generations = 14;
        LifeGame  game = new  LifeGame(size);
        int count = 1;
        if (generations != 0) {
            while (generations >= count) {
                System.out.println("Generation #" + count);
                System.out.println("Alive: " +  game.countAlive());
                game.showUniverse();
                game.createNewGeneration();
                game.changeCurrentGenToNew();
                count++;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }

            }
        }
    }
}

class  LifeGame {
    private int size;
    private char[][] universe;
    private char[][] newUniverse;
    private int aliveCells;

    public  LifeGame(int size) {
        this.size = size;
        fillUniverse();
    }

    public void fillUniverse() {
        universe = new char[size][size];
        Random random = new Random();
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                universe[i][j] = random.nextBoolean() ? 'O' : ' ';
            }
        }
    }

    public void createNewGeneration() {
        newUniverse = new char[universe.length][universe.length];
        for (int currentPosK = 0; currentPosK < universe.length; currentPosK++) {
            for (int currentPosL = 0; currentPosL < universe.length; currentPosL++) {  // choosing cell from universe
                // loop for checking neighbours of the current choosed cell
                int neighbour = 0;
                for (int i = currentPosK - 1; i <= currentPosK + 1; i++) {
                    for (int j = currentPosL - 1; j <= currentPosL + 1; j++) {
                        int cellI = i, cellJ = j; // żeby nie zmieniało i i j w pętli na 0 po zastosowaniu
                        // warunków niżej
                        boolean cellLeftSide = cellJ < 0;
                        boolean cellRightSide = cellJ > universe.length - 1;
                        boolean cellUpper = cellI < 0;
                        boolean cellDown = cellI > universe.length - 1;
                        boolean lefUpCorner = cellI < 0;
                        boolean leftDownCorner = cellI > universe.length - 1;
                        boolean rightUpperCorner = cellI < 0;
                        boolean rightDownCorner = cellI > universe.length - 1;

                        if (cellLeftSide) {
                            cellJ = size - 1;
                            if (lefUpCorner) {
                                cellI = size - 1;
                            } else if (leftDownCorner) {
                                cellI = 0;
                            }
                        }
                        if (cellRightSide) {
                            cellJ = 0;
                            if (rightUpperCorner) {
                                cellI = size - 1;
                            } else if (rightDownCorner) {
                                cellJ = 0;
                            }
                        }
                        if (cellUpper) {
                            cellI = size - 1;
                        }

                        if (cellDown) {
                            cellI = 0;
                        }
                        boolean currentCell = cellI == currentPosK && cellJ == currentPosL;
                        if (universe[cellI][cellJ] == 'O' && !currentCell) {
                            neighbour++;
                        }
                    }
                }
                boolean stayAlive = universe[currentPosK][currentPosL] == 'O' && neighbour >= 2 && neighbour <= 3;
                boolean reborn = universe[currentPosK][currentPosL] == ' ' && neighbour == 3;
                if (stayAlive) {
                    newUniverse[currentPosK][currentPosL] = 'O';
                } else if (reborn) {
                    newUniverse[currentPosK][currentPosL] = 'O';
                } else {
                    newUniverse[currentPosK][currentPosL] = ' ';
                }
            }
        }
    }

    public void changeCurrentGenToNew() {
        for (int i = 0; i < newUniverse.length; i++) {
            for (int j = 0; j < newUniverse.length; j++) {
                universe[i][j] = newUniverse[i][j];
            }
        }
    }

    public void showUniverse() {
        for (char[] characters : universe) {
            for (int j = 0; j < universe.length; j++) {
                System.out.print(characters[j]);
            }
            System.out.println();
        }
    }

    public int countAlive() {
        aliveCells = 0;
        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                if (universe[i][j] == 'O')
                    aliveCells++;
            }
        }
        return aliveCells;
    }
}