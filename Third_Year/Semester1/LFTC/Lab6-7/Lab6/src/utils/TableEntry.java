package utils;

import syntax_tree.Node;

public class TableEntry {
    public Node item;

    public Integer parentIndex;

    public Integer rightSiblingIndex;

    public TableEntry(Node item, int parentIndex, int rightSiblingIndex) {
        this.item = item;
        this.parentIndex = parentIndex;
        this.rightSiblingIndex = rightSiblingIndex;
    }

    public Node getItem() {
        return item;
    }

    public int getParentIndex() {
        return parentIndex;
    }

    public int getRightSiblingIndex() {
        return rightSiblingIndex;
    }

    @Override
    public String toString() {
        return "< " + item + "; " + parentIndex + "; "+ rightSiblingIndex + " >";
    }
}
