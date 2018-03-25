package entity.mobile;

import controller.Main.Direction;
import entity.Map;
import entity.immobile.Immobile;

/**
 * Created by caojiajun on 2018/3/24.
 */
public class Box extends Mobile {
    public Box(int positionRow, int positionCol) {
        super(positionRow, positionCol);
    }

    @Override
    public boolean canMove(Direction direction) {
        int[] positionTo = getPositionAfterMove(direction);
        int positionRowTo = positionTo[0];
        int positionColTo = positionTo[1];
        Map map = Map.getMap();
        Immobile immobileInstance = map.getImmobileInstance(positionRowTo, positionColTo);
        Immobile.ImmobileType type = immobileInstance.getImmobileType();

        if (type == Immobile.ImmobileType.WALL || isOutOfBoundary(direction) ||
                immobileInstance.getMobileInstance() instanceof Box) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isInTarget() {
        Map map = Map.getMap();
        return map.getImmobileInstance(getPositionRow(), getPositionCol()).getImmobileType() == Immobile.ImmobileType.TARGET;
    }

    @Override
    public String toString() {
        if (isInTarget()) {
            return "√ ";
        } else {
            return "□ ";
        }
    }
}
