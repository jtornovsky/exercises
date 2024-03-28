package snake;

public class Snake {

    private final int SNAKE_LENGTH = 156;
    private final int MAX_SNAKE_LENGTH_IN_ROW = 19;

    public static void main(String[] args) {

        Snake snake = new Snake();
        snake.doSnake();
    }

    private void doSnake() {

        String[] result = new String[MAX_SNAKE_LENGTH_IN_ROW];
        boolean isSnakeGoingForward = true;

        for (int idx = 0, numberOfCycles = 0; idx <= SNAKE_LENGTH; idx++, numberOfCycles++) {

            int arrayIndex = numberOfCycles % MAX_SNAKE_LENGTH_IN_ROW;

            if (isSnakeGoingForward) {
                result[arrayIndex] = String.valueOf(idx);
            } else {
                result[MAX_SNAKE_LENGTH_IN_ROW - arrayIndex - 1] = String.valueOf(idx);
            }

            if (arrayIndex == MAX_SNAKE_LENGTH_IN_ROW - 1) {
                printSnake(result);
                result = new String[MAX_SNAKE_LENGTH_IN_ROW];
                isSnakeGoingForward = !isSnakeGoingForward;
            }
        }
        printSnake(result);
    }

    private void printSnake(String[] snake) {

        final int maxLengthOfEachChar = 3;

        for (String s : snake) {
            if (s == null || s.length() == 0) {
                s = "";
            }
            System.out.printf("%-" + (maxLengthOfEachChar + 1) + "s", s); // Add padding based on maxLengthOfEachChar
        }
        System.out.println();
    }
}