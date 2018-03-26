package controller;

import entity.Map;
import entity.mobile.Box;
import entity.mobile.Player;
import exception.CannotUndoException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by caojiajun on 2018/3/24.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("请输入地图文件：");
        Scanner input = new Scanner(System.in);
        String file = input.nextLine();
        Map.initMap(new File(file));
        History history = History.getHistory();

        Map map = Map.getMap();
        map.printMap();
        System.out.println("指令介绍：w、a、s、d为向上、向左、向下、向右，q为退出，u为撤销上一次的移动，输入new后根据提示可以进行新一轮游戏");

        while (true) {
            String instruction = input.nextLine();
            if (instruction.equals("w") || instruction.equals("s") ||
                    instruction.equals("a") || instruction.equals("d")) {
                Direction direction = Direction.parseDirection(instruction);
                Player player = map.getPlayer();

                if (player.getBeside(direction) instanceof Box) {
                    Box box = (Box) player.getBeside(direction);
                    if (box.canMove(direction)) {
                        int playerRowFrom = player.getPositionRow();
                        int playerColFrom = player.getPositionCol();
                        int boxRowFrom = box.getPositionRow();
                        int boxColFrom = box.getPositionCol();
                        box.move(direction);
                        player.move(direction);
                        history.addRecord(map.getImmobileInstance(boxRowFrom, boxColFrom), map.getImmobileInstance(box.getPositionRow(), box.getPositionCol()), box);
                        history.addRecord(map.getImmobileInstance(playerRowFrom, playerColFrom), map.getImmobileInstance(player.getPositionRow(), player.getPositionCol()), player);
                    } else {
                        System.out.println("不能移动！");
                    }
                } else {
                    if (player.canMove(direction)) {
                        int playerRowFrom = player.getPositionRow();
                        int playerColFrom = player.getPositionCol();
                        player.move(direction);
                        history.addRecord(map.getImmobileInstance(playerRowFrom, playerColFrom), map.getImmobileInstance(player.getPositionRow(), player.getPositionCol()), player);
                    } else {
                        System.out.println("不能移动！");
                    }
                }
                map.printMap();

                if (map.checkWin()) {
                    System.out.println("游戏胜利！输入'q'可以退出，输入new后根据提示可以进行新一轮游戏");
                }
            } else if (instruction.equals("q")) {
                System.exit(0);
            } else if (instruction.equals("u")) {
                try {
                    history.undo();
                    System.out.println("回退成功");
                } catch (CannotUndoException e) {
                    System.out.println(e.getMessage());
                } finally {
                    map.printMap();
                }
            } else if (instruction.equals("new")) {
                System.out.println("请输入地图文件：");
                String newFile = input.nextLine();
                History.restart();
                history = History.getHistory();
                Map.initMap(new File(newFile));
                map = Map.getMap();
                map.printMap();
            } else {
                System.out.println("输入了无效的指令！");
            }
        }
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT;

        public static Direction parseDirection(String direction) {
            switch (direction) {
                case "w":
                    return UP;
                case "s":
                    return DOWN;
                case "a":
                    return LEFT;
                case "d":
                    return RIGHT;
                default:
                    return null;
            }
        }
    }
}
