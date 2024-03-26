package maze;

public enum CellType {
    Entrance('S'),
    Exit('E'),
    Path('.'),
    Wall('#');

    private final char type;

    CellType(char type) {
        this.type = type;
    }

    static CellType getCellType(char type) {
        for (CellType t : CellType.values()) {
            if (t.type == type) {
                return t;
            }
        }
        return null;
    }
}
