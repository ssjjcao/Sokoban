package controller;

import entity.immobile.Immobile;
import entity.mobile.Box;
import entity.mobile.Mobile;
import exception.CannotUndoException;

/**
 * Created by caojiajun on 2018/3/24.
 */
public class History {
    private Node current;
    private static History history;

    private History() {
        this.current = new Node(null, null);
    }

    public static History getHistory() {
        if (history == null) {
            history = new History();
        }
        return history;
    }

    public static void restart() {
        history = null;
    }

    public void addRecord(Immobile from, Immobile to, Mobile mobileInstance) {
        HistoryRecord newRecord = new HistoryRecord(from, to, mobileInstance);
        Node newNode = new Node(current, newRecord);
        current = newNode;
    }

    private void undoHelper() {
        HistoryRecord record = current.record;
        int rowFrom = record.from.getPositionRow();
        int colFrom = record.from.getPositionCol();
        int rowTo = record.to.getPositionRow();
        int colTo = record.to.getPositionCol();
        record.mobileInstance.move(rowTo, colTo, rowFrom, colFrom);
        current = current.pre;
    }

    public void undo() throws CannotUndoException {
        if (current == null || current.record == null) {
            throw new CannotUndoException("已经到了初始状态，不能进行回退！");
        }

        if (current.pre != null && current.pre.record.mobileInstance instanceof Box) {
            undoHelper();
            undoHelper();
        } else {
            undoHelper();
        }
    }

    private class HistoryRecord {
        final Immobile from;
        final Immobile to;
        final Mobile mobileInstance;

        protected HistoryRecord(Immobile from, Immobile to, Mobile mobileInstance) {
            this.from = from;
            this.to = to;
            this.mobileInstance = mobileInstance;
        }
    }

    private class Node {
        Node pre;
        final HistoryRecord record;

        protected Node(Node pre, HistoryRecord record) {
            this.pre = pre;
            this.record = record;
        }
    }
}
