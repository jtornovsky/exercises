package maze;

import java.util.Objects;

public class Cell {
    CellType cellType;
    int lineIdx;
    int columnIdx;
    boolean isVisited;

    public Cell(CellType cellType, int lineIdx, int columnIdx, boolean isVisited) {
        this.cellType = cellType;
        this.lineIdx = lineIdx;
        this.columnIdx = columnIdx;
        this.isVisited = isVisited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cell cell = (Cell) o;
        return lineIdx == cell.lineIdx && columnIdx == cell.columnIdx && cellType == cell.cellType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cellType, lineIdx, columnIdx);
    }

    @Override
    public String toString() {
        return String.format(" (%d, %d) ", lineIdx, columnIdx);
    }
}
