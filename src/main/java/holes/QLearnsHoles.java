package holes;

import java.util.List;

public class QLearnsHoles {

	double[][] qTable;
	double learningRate = 0.05;
	double discountFactor = 0.5;
	int currentState = 0;
	int chosenAction = 0;

	public QLearnsHoles(int gridSize, int startX, int startY) {
		qTable = new double[gridSize * gridSize][4];

		for (int i = 0; i < qTable.length; i++) {
			for (int j = 0; j < qTable[0].length; j++) {
				qTable[i][j] = Math.random();
			}
		}
		this.currentState = startX + startY * gridSize;
	}

	public void rewardAndUpdate(int nextState, int exitState, List<Integer> holes) {
		double reward = 0;
		if (nextState == currentState) {
			reward = -1;
		}
		if (nextState == exitState) {
			reward = 1;
		}
		for (Integer holeState : holes) {
			if (holeState == nextState) {
				reward = -1;
				break;
			}
		}

		qTable[currentState][chosenAction] = qTable[currentState][chosenAction] * (1 - learningRate)
				+ learningRate * (reward + discountFactor * maxAction(nextState));
		currentState = nextState;

	}

	public void getAction() {
		int action = 0;
		for (int i = 1; i < qTable[0].length; i++) {
			if (qTable[currentState][action] < qTable[currentState][i]) {
				action = i;
			}
		}
		chosenAction = action;
	}

	public double maxAction(int nextState) {
		double max = 0;
		for (int i = 0; i < qTable[0].length; i++) {
			if (qTable[nextState][i] > max) {
				max = qTable[nextState][i];
			}
		}
		return max;
	}

}
