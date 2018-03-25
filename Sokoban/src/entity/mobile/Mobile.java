package entity.mobile;

import controller.Main.Direction;
import entity.Map;

/**
 * Created by caojiajun on 2018/3/24.
 */
public abstract class Mobile {
    private int positionRow;
    private int positionCol;

    public Mobile(int positionRow, int positionCol) {
        this.positionRow = positionRow;
        this.positionCol = positionCol;
    }

    public abstract boolean canMove(Direction direction);

    public void move(Direction direction) {
        Map map = Map.getMap();
        map.getImmobileInstance(getPositionRow(), getPositionCol()).setMobileInstance(null);

        int[] positionTo = getPositionAfterMove(direction);
        int positionRowTo = positionTo[0];
        int positionColTo = positionTo[1];
        setPosition(positionRowTo, positionColTo);

        map.getImmobileInstance(positionRowTo, positionColTo).setMobileInstance(this);
    }

    public void move(int positionRowFrom, int positionColFrom, int positionRowTo, int positionColTo) {
        Map map = Map.getMap();
        map.getImmobileInstance(positionRowFrom, positionColFrom).setMobileInstance(null);

        setPosition(positionRowTo, positionColTo);

        map.getImmobileInstance(positionRowTo, positionColTo).setMobileInstance(this);
    }

    protected int[] getPositionAfterMove(Direction direction) {
        int[] position = new int[2];
        int positionRowFrom = this.getPositionRow();
        int positionColFrom = this.getPositionCol();

        switch (direction) {
            case UP:
                position[0] = positionRowFrom - 1;
                position[1] = positionColFrom;
                break;
            case DOWN:
                position[0] = positionRowFrom + 1;
                position[1] = positionColFrom;
                break;
            case LEFT:
                position[0] = positionRowFrom;
                position[1] = positionColFrom - 1;
                break;
            case RIGHT:
                position[0] = positionRowFrom;
                position[1] = positionColFrom + 1;
                break;
        }
        return position;
    }

    protected boolean isOutOfBoundary(Direction direction) {
        int[] positionTo = getPositionAfterMove(direction);
        int positionRowTo = positionTo[0];
        int positionColTo = positionTo[1];

        Map map = Map.getMap();
        int[] size = map.getSize();
        int rowNum = size[0];
        int colNum = size[1];

        if (positionRowTo <= 0 || positionRowTo >= rowNum ||
                positionColTo <= 0 || positionColTo >= colNum) {
            return true;
        } else {
            return false;
        }
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    private void setPosition(int positionRow, int positionCol) {
        this.positionRow = positionRow;
        this.positionCol = positionCol;
    }
}
