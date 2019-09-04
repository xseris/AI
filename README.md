# AI

The AI project contains some examples of AI that learns to play games. All the code can be re-used for academic purposes.

## Reinforcement Learning AI

### Holes

The AI learns how to play Holes. The goal of the game is to go from the starting point (green) to the exit point (red), without falling in the black holes. The AI is rewarded with -1 if it fall in a hole and with +1 if it reaches the end.

Just run the Game class to see the AI in action. You can change the number of the holes in the floor, the floor size and the speed. On the right is showed the history of attempts (green the successfully attempts, red the failure attempts). The length of the attempt represent the number of steps taken to succeed or to fail.

### Holes 3D

The same game as the previous one, but with more layers. You can specify the number of floors. Each floor has a custom number of upstairs and downstairs that the AI can use to navigate between them. It gets +1 if it reaches the exit (on the last floor) and -1 if it fall in a hole when it is on floor 0. Falling in a hole in any other floors will project the AI in the same place one (or more) floor below.

+ pressing 'w' key will increase the game speed.
+ pressing 's' key will decrease the game speed.
+ pressing 'r' will switch to random mode: reinforced learning has usually the problem that when a good sequence of action has been found, the AI will not "explore" other alternatives in search of better solutions (can also be stuck on a bad previous decision). Switching to random mode, every 5 steps the AI will take a random decision, no matter if it will not be the best one.