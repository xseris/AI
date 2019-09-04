package holes3d;

import java.util.ArrayList;
import java.util.List;

public class Cube {
	List<Grid> grids;
	int gridNum;
	int gridSize;
	int holePerGrid;
	int upStairsPerGrid;

	public Cube(final int gridNum, final int gridSize, final int holePerGrid, final int upStairsPerGrid) {
		this.grids = new ArrayList<>();
		this.gridNum = gridNum;
		this.gridSize = gridSize;
		this.holePerGrid = holePerGrid;
		this.upStairsPerGrid = upStairsPerGrid;
		this.initialize();
	}

	private void initialize() {
		for (int i = 0; i < this.gridNum; ++i) {
			if (i == 0) {
				final Grid grid = new Grid(this.gridSize, this.holePerGrid, this.upStairsPerGrid, (Grid) null, false);
				this.grids.add(grid);
			} else if (i == this.gridNum - 1) {
				final Grid grid = new Grid(this.gridSize, this.holePerGrid, this.upStairsPerGrid,
						(Grid) this.grids.get(i - 1), true);
				this.grids.add(grid);
			} else {
				final Grid grid = new Grid(this.gridSize, this.holePerGrid, this.upStairsPerGrid,
						(Grid) this.grids.get(i - 1), false);
				this.grids.add(grid);
			}
		}
	}
}
