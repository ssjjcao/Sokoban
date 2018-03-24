package entity;

import entity.immobile.Immobile;
import entity.mobile.Box;
import entity.mobile.Mobile;
import entity.mobile.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by caojiajun on 2018/3/24.
 */
public class Map {
    private final ArrayList<Mobile> mobiles = new ArrayList<Mobile>();
    private final Immobile[][] immobiles;
    private static Map map;

    private Map(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        int NumPerLine = scanner.nextInt();
        int LineNum = scanner.nextInt();
        scanner.nextLine();
        immobiles = new Immobile[LineNum][NumPerLine];
        for (int i = 0; i < LineNum; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < NumPerLine; j++) {
                int num = line.charAt(j) - '0';
                switch (num) {
                    case 0:
                    case 2:
                        immobiles[i][j] = new Immobile(i, j, Immobile.ImmobileType.BLANK);
                        break;
                    case 1:
                        immobiles[i][j] = new Immobile(i, j, Immobile.ImmobileType.WALL);
                        break;
                    case 3:
                        Mobile box = new Box(i, j);
                        mobiles.add(box);
                        immobiles[i][j] = new Immobile(i, j, Immobile.ImmobileType.BLANK);
                        immobiles[i][j].setMobileInstance(box);
                        break;
                    case 4:
                        immobiles[i][j] = new Immobile(i, j, Immobile.ImmobileType.TARGET);
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                        Mobile player = new Player(i, j);
                        mobiles.add(player);
                        immobiles[i][j] = new Immobile(i, j, Immobile.ImmobileType.BLANK);
                        immobiles[i][j].setMobileInstance(player);
                        break;
                    case 9:
                        Mobile boxInTarget = new Box(i, j);
                        mobiles.add(boxInTarget);
                        immobiles[i][j] = new Immobile(i, j, Immobile.ImmobileType.TARGET);
                        immobiles[i][j].setMobileInstance(boxInTarget);
                        break;
                }
            }
        }
        scanner.close();
    }

    public static Map initMap(File file) {
        try {
            map = new Map(file);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
        return map;
    }

    public static Map getMap() {
        return map;
    }

    public Immobile getImmobileInstance(int positionRow, int positionCol) {
        for (Immobile[] row : immobiles) {
            for (Immobile immobileInstance : row) {
                if (positionRow == immobileInstance.getPositionRow() &&
                        positionCol == immobileInstance.getPositionCol()) {
                    return immobileInstance;
                }
            }
        }
        return null;
    }

    public Player getPlayer() {
        for (Immobile[] row : immobiles) {
            for (Immobile immobileInstance : row) {
                if (immobileInstance.getMobileInstance() instanceof Player) {
                    return (Player) immobileInstance.getMobileInstance();
                }
            }
        }
        return null;
    }

    public boolean checkWin() {
        int mobileNum = mobiles.size();
        int boxNum = mobileNum - 1;
        int boxInTarget = 0;
        for (int i = 0; i < mobileNum; i++) {
            Mobile box = mobiles.get(i);
            if (box instanceof Box && ((Box) box).isInTarget()) {
                boxInTarget++;
            }
        }
        return boxInTarget == boxNum;
    }

    public void printMap() {
        for (Immobile[] row : immobiles) {
            for (Immobile instance : row) {
                if (instance.getMobileInstance() == null) {
                    System.out.print(instance);
                } else {
                    System.out.print(instance.getMobileInstance());
                }
            }
            System.out.print("\n");
        }
    }

    public int[] getSize() {
        int[] size = new int[2];
        size[0] = immobiles.length;
        size[1] = immobiles[0].length;
        return size;
    }
}
