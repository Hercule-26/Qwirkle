package g60131.qwirkle.model;
public class GridView {
    private final Grid grid;
    public GridView(Grid grid) {
        this.grid = grid;
    }
    public Tile get(int row, int col) {
        return this.grid.get(row, col);
    }
    public boolean isEmpty() {
        return this.grid.isEmpty();
    }
}
