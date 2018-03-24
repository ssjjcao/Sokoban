package controller;

import entity.Map;
import entity.mobile.Box;
import entity.mobile.Player;

import java.io.File;
import java.util.Scanner;

/**
 * Created by caojiajun on 2018/3/24.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("请输入地图文件：");
        Scanner input = new Scanner(System.in);
        String file = input.nextLine();
        Map.initMap(new File(file));

        Map map = Map.getMap();
        map.printMap();
        System.out.println("指令介绍：w、a、s、d为向上、向左、向下、向右，q为退出，u为撤销一次移动，'new 文件名'为重新开始该文件名下的游戏");

        while (true) {
            String instruction = input.nextLine();
            if (instruction.equals("w") || instruction.equals("s") ||
                    instruction.equals("a") || instruction.equals("d")) {
                Direction direction = Direction.parseDirection(instruction);
                Player player = map.getPlayer();

                if (player.getBeside(direction) instanceof Box){
                    Box box = (Box) player.getBeside(direction);
                    if (box.canMove(direction)){
                        box.move(direction);
                        player.move(direction);
                    }else {
                        System.out.println("不能移动！");
                    }
                }else {
                    if (player.canMove(direction)){
                        player.move(direction);
                    }else {
                        System.out.println("不能移动！");
                    }
                }

                map.printMap();

                if (map.checkWin()){
                    System.out.println("游戏胜利！输入'q'可以退出，输入'new 文件名'可以进行新一轮游戏");
                }
            }else if(instruction.equals("q")){
                System.exit(0);
            }else if (instruction.equals("u")){

            }else if (instruction.equals("new")){

            }else {
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
