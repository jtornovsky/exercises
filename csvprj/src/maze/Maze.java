package maze;

import java.util.*;

public class Maze {

    /*
    function solveMaze(maze):
        initialize stack or queue
        mark start cell as visited and push it onto stack or enqueue it
        while stack or queue is not empty:
            currentCell = pop from stack or dequeue from queue
            if currentCell is the goal:
                return "Path found"
            for each neighbor of currentCell:
                if neighbor is not a wall and has not been visited:
                    mark neighbor as visited
                    push neighbor onto stack or enqueue it
            if no unvisited neighbors:
                backtrack by popping from stack or dequeueing from queue
        return "No path found"
     */

    public static void main(String[] args) {

        Maze mazeObj = new Maze();

        char[][] maze1 = {
                {'.', '.', '.', '.', '#'},
                {'S', '#', '.', '#', '#'},
                {'.', '.', '.', '#', '.'},
                {'#', '.', '#', '#', '.'},
                {'#', '.', 'E', '.', '.'}
        };  // S : (1, 0) -> (0, 0) -> (0, 1) -> (0, 2) -> (1, 2) -> (2, 2) -> (2, 1) -> (3, 1) -> (4, 1) -> (4, 2) : E

        char[][] maze2 = {
                {'.', 'S', '.', '.', '#'},
                {'.', '#', '.', '#', '#'},
                {'.', '.', '.', '#', '.'},
                {'#', '#', '.', '#', '.'},
                {'#', '.', 'E', '.', '.'}
        };  // S : (0, 1)  (0, 0)  (1, 0)  (2, 0)  (2, 1)  (2, 2)  (3, 2)  (4, 2) : E

        char[][] maze3 = {
                {'.', '#', '.', '.', '.'},
                {'S', '#', '.', '#', '.'},
                {'.', '.', '.', '#', '.'},
                {'#', '.', '#', '#', '.'},
                {'#', '.', '#', '#', 'E'}
        };  // S : (1, 0) -> (2, 0) -> (2, 1) -> (2, 2) -> (1, 2) -> (0, 2) -> (0, 3) -> (0, 4) -> (1, 4) -> (2, 4) -> (3, 4) -> (4, 4) : E

        char[][] maze4 = {
                {'.', 'S', '.', '.', '#'},
                {'.', '#', '.', '#', '#'},
                {'.', '#', '.', '#', '.'},
                {'#', '#', '.', '#', '.'},
                {'#', '.', 'E', '.', '.'}
        };  // S : (0, 1) -> (0, 2) -> (1, 2) -> (2, 2) -> (3, 2) -> (4, 2) : E

        char[][] maze5 = {
                {'.', 'S', '.', '.', '#'},
                {'.', '#', '.', '#', '#'},
                {'.', '#', '.', '#', '.'},
                {'#', '#', '#', '#', '.'},
                {'#', '.', 'E', '.', '.'}
        };  // no path

        char[][] maze6 = {
                {'#', 'S', '#', '#', '#', '#', '#', '#', '#'},
                {'#', '.', '.', '.', '#', '.', '.', '.', '#'},
                {'#', '.', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '.', '.', '.', '.', '.', '.', '.', '#'},
                {'#', '#', '#', '.', '#', '.', '#', '.', '#'},
                {'#', '#', '#', '#', '#', '#', '#', 'E', '#'}
        }; // S : (0, 1)  (1, 1)  (1, 2)  (1, 3)  (2, 3)  (3, 3)  (4, 3)  (5, 3)  (6, 3)  (6, 4)  (6, 5)  (5, 5)  (4, 5)  (3, 5)  (2, 5)  (1, 5)  (1, 6)  (1, 7)  (2, 7)  (3, 7)  (4, 7)  (5, 7)  (6, 7)  (7, 7)  (8, 7) : E

        mazeObj.solveMaze(maze1, new maze.Cell(maze.CellType.Entrance, 1, 0, true), new maze.Cell(maze.CellType.Exit, 4, 2, false));
        mazeObj.solveMaze(maze2, new maze.Cell(maze.CellType.Entrance, 0, 1, true), new maze.Cell(maze.CellType.Exit, 4, 2, false));
        mazeObj.solveMaze(maze3, new maze.Cell(maze.CellType.Entrance, 1, 0, true), new maze.Cell(maze.CellType.Exit, 4, 4, false));
        mazeObj.solveMaze(maze4, new maze.Cell(maze.CellType.Entrance, 0, 1, true), new maze.Cell(maze.CellType.Exit, 4, 2, false));
        mazeObj.solveMaze(maze5, new maze.Cell(maze.CellType.Entrance, 0, 1, true), new maze.Cell(maze.CellType.Exit, 4, 2, false));
        mazeObj.solveMaze(maze6, new maze.Cell(maze.CellType.Entrance, 0, 1, true), new maze.Cell(maze.CellType.Exit, 8, 7, false));

    }

    private void solveMaze(char[][] charMaze, Cell entrance, Cell exit) {

        Cell[][] maze = fillMaze(charMaze, entrance);

        Stack<Cell> path = new Stack<>();
        path.push(entrance);

        Cell nextCell = findNextCell(maze, entrance);
        boolean pathFound = exit.equals(nextCell);

        while (!pathFound) {

            if (path.empty()) {
                break;
            }

            if (nextCell != null) {
                path.push(nextCell);
                nextCell = findNextCell(maze, nextCell);
                pathFound = exit.equals(nextCell);
            } else {
                Cell previousCell = path.pop();
                nextCell = findNextCell(maze, previousCell);
                if (nextCell != null) {
                    // we don't remove the previous cell in case we found more options for the possible path
                    path.push(previousCell);
                }
            }
        }

        if (pathFound) {
            path.push(exit);
            path.forEach(System.out::print);
            System.out.println();
        } else {
            System.out.println(" No path found.");
        }
    }

    private Cell findNextCell(Cell[][] maze, Cell currentCell) {
        int[][] directions = {
                {-1, 0}, // up
                {0, -1}, // left
                {0, 1},  // right
                {1, 0}   // down
        };

        for (int[] dir : directions) {
            int line = currentCell.lineIdx + dir[0];
            int column = currentCell.columnIdx + dir[1];
            if (isValidPosition(line, column, maze)) {
                Cell nextCell = maze[line][column];
                if (!nextCell.isVisited && isPathableCell(nextCell)) {
                    nextCell.isVisited = true;
                    return nextCell;
                }
            }
        }
        return null;
    }

    private boolean isValidPosition(int line, int column, Cell[][] maze) {
        return line >= 0 && line < maze.length && column >= 0 && column < maze[0].length;
    }

    private boolean isPathableCell(Cell cell) {
        return !cell.isVisited && cell.cellType == CellType.Path || cell.cellType == CellType.Exit;
    }

    private Cell[][] fillMaze(char[][] charMaze, Cell entrance) {
        Cell[][] cellMaze = new Cell[charMaze.length][charMaze[0].length];
        for (int i = 0; i < charMaze.length; i++) {
            for (int j = 0; j < charMaze[0].length; j++) {
                Cell cell = new Cell(CellType.getCellType(charMaze[i][j]), i, j, false);
                cellMaze[i][j] = cell;
            }
        }
        cellMaze[entrance.lineIdx][entrance.columnIdx].isVisited = true;
        return cellMaze;
    }
}

