package entity.immobile;

import entity.mobile.Mobile;

/**
 * Created by caojiajun on 2018/3/24.
 */
public class Immobile {
    private final int positionRow;
    private final int positionCol;
    private final ImmobileType immobileType;
    private Mobile mobileInstance;

    public Immobile(int positionRow, int positionCol, ImmobileType immobileType) {
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.immobileType = immobileType;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public ImmobileType getImmobileType() {
        return immobileType;
    }

    public Mobile getMobileInstance() {
        return mobileInstance;
    }

    public void setMobileInstance(Mobile mobileInstance) {
        this.mobileInstance = mobileInstance;
    }

    @Override
    public String toString() {
        return getImmobileType().getPrint();
    }

    public enum ImmobileType {
        BLANK("  "), WALL("■ "), TARGET("× ");

        private final String print;

        ImmobileType(String print) {
            this.print = print;
        }

        public String getPrint() {
            return print;
        }
    }
}
