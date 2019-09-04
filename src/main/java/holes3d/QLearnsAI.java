package holes3d;

public class QLearnsAI {

	double[][][] qTable;
	double learningRate = 0.05;
	double discountFactor = 0.5;
	int currentState = 0;
	int chosenAction = 0;
	int currentZ = 0;
	long timesZ = 1;

	public QLearnsAI(int cubeSize, int gridSize, int startX, int startY) {
		qTable = new double[gridSize * gridSize][cubeSize][4];

		for (int i = 0; i < qTable.length; i++) {
			for (int j = 0; j < qTable[0].length; j++) {
				for (int k = 0; k < qTable[0][0].length; k++) {
					qTable[i][j][k] = Math.random();
				}
			}
		}
		this.currentState = startX + startY * gridSize;
	}

	public void rewardAndUpdate(int nextState, int exitState, Grid grid, int nextZ) {
		double reward = 0;
		if (nextState == currentState && currentZ == nextZ) {
			reward = -1;
		}
		if (nextState == exitState) {
			reward = 1;
		}
		if (nextZ == 0) {
			for (Integer holeState : grid.holeStates) {
				if (holeState == nextState) {
					reward = -1;
					break;
				}
			}
		}

		timesZ++;

		qTable[currentState][currentZ][chosenAction] = qTable[currentState][currentZ][chosenAction] * (1 - learningRate)
				+ learningRate * (reward + discountFactor * maxAction(nextState, nextZ));
		currentState = nextState;
		currentZ = nextZ;

	}

	public void getAction(int random) {
		if (random == 0 && timesZ % 5 == 0) {
			chosenAction = (int) (Math.random() * 4);
		} else {
			int action = 0;
			for (int i = 1; i < qTable[0][0].length; i++) {
				if (qTable[currentState][currentZ][action] < qTable[currentState][currentZ][i]) {
					action = i;
				}
			}
			chosenAction = action;
		}
	}

	public double maxAction(int nextState, int nextZ) {
		double max = 0;
		for (int i = 0; i < qTable[0][0].length; i++) {
			if (qTable[nextState][nextZ][i] > max) {
				max = qTable[nextState][nextZ][i];
			}
		}
		return max;
	}

}
