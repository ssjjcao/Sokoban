package entity.mobile;

import controller.Main.Direction;
import entity.Map;
import entity.immobile.Immobile;

/**
 * Created by caojiajun on 2018/3/24.
 */
public class Player extends Mobile {
    public Player(int positionRow, int positionCol) {
        super(positionRow, positionCol);
    }

    @Override
    public boolean canMove(Direction direction) {
        int[] positionTo = getPositionAfterMove(direction);
        int positionRowTo = positionTo[0];
        int positionColTo = positionTo[1];
        Map map = Map.getMap();
        Immobile.ImmobileType type = map.getImmobileInstance(positionRowTo, positionColTo).getImmobileType();

        if (type == Immobile.ImmobileType.WALL || isOutOfBoundary(direction)) {
            return false;
        } else {
            return true;
        }
    }

    public Object getBeside(Direction direction){
        int[] positionTo = getPositionAfterMove(direction);
        int positionRowTo = positionTo[0];
        int positionColTo = positionTo[1];
        Map map = Map.getMap();
        return map.getImmobileInstance(positionRowTo,positionColTo).getMobileInstance();
    }

    @Override
    public String toString(){
        return "★ ";
    }
}
