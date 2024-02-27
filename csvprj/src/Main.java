import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws Exception {

        Main main = new Main();
        main.doSnake();
    }

    private void doSnake() {

        int snakeLength = 17;
        int maxPositionsInRow = 7;
        String[] result = new String[maxPositionsInRow];
        boolean isSnakeGoingForward = true;

        for (int idx = 0, numberOfCycles = 0; idx <= snakeLength; idx++, numberOfCycles++) {

            int arrayIndex = numberOfCycles % maxPositionsInRow;

            if (isSnakeGoingForward) {
                result[arrayIndex] = String.valueOf(idx);
            } else {
                result[maxPositionsInRow-arrayIndex-1] = String.valueOf(idx);
            }

            if (arrayIndex == maxPositionsInRow-1) {
                System.out.println(Arrays.toString(result));
                result = new String[maxPositionsInRow];
                isSnakeGoingForward = !isSnakeGoingForward;
            }
        }
        System.out.println(Arrays.toString(result));
    }

    private void doAction() throws Exception {

        File f1 = new File(
                "C:\\Users\\Jonah\\Documents\\workspace\\csvprj\\files\\f1.txt");
        BufferedReader br1
                = new BufferedReader(new FileReader(f1));
        String st1 = br1.readLine();

        File f2 = new File(
                "C:\\Users\\Jonah\\Documents\\workspace\\csvprj\\files\\f2.txt");
        BufferedReader br2
                = new BufferedReader(new FileReader(f2));
        String st2 = br2.readLine();

        File diffResult = new File(
                "C:\\Users\\Jonah\\Documents\\workspace\\csvprj\\files\\diffResult.txt");
        diffResult.delete();
        BufferedWriter diffResultBW
                = new BufferedWriter(new FileWriter(diffResult));


        while (st1 != null || st2 != null) {

            int st1CommaPosition = -1;
            int st2CommaPosition = -1;
            int id1 = -1;
            int id2 = -1;
            String result = "";

            if (st1 != null) {
                st1CommaPosition = st1.indexOf(",");
                id1 = Integer.parseInt(st1.substring(0, st1CommaPosition).trim());
            }

            if (st2 != null) {
                st2CommaPosition = st2.indexOf(",");
                id2 = Integer.parseInt(st2.substring(0, st2CommaPosition).trim());
            }

            if (id1 == -1 || id2 == -1) {
                if (id1 == -1) {
                    result = String.format("id %d in the file f2 not found in the file f1", id2);
                    st2 = br2.readLine();
                }
                if (id2 == -1) {
                    result = String.format("id %d in the file f1 not found in the file f2", id1);
                    st1 = br1.readLine();
                }
                diffResultBW.append(result + "\r\n");
                continue;
            }

            if (id1 == id2) {
                if (st2.equalsIgnoreCase(st1)) {
                    result = String.format("id %d has identical lines in the both files", id1);
                } else {
                    result = String.format("id %d has different lines in the both files", id1);
                }

                st1 = br1.readLine();
                st2 = br2.readLine();
            }

            if (id1 < id2) {
                result = String.format("id %d in the file f1 not found in the file f2", id1);
                st1 = br1.readLine();
            }

            if (id1 > id2) {
                result = String.format("id %d in the file f2 not found in the file f1", id2);
                st2 = br2.readLine();
            }

            diffResultBW.append(result + "\r\n");
        }

        diffResultBW.flush();
        diffResultBW.close();

        BufferedReader diffResultBR
                = new BufferedReader(new FileReader(diffResult));
        String resultStr;
        while ((resultStr = diffResultBR.readLine()) != null) {
            System.out.println(resultStr);
        }

        br1.close();
        br2.close();
        diffResultBR.close();
    }
}


/*

a--
id, f1, f2, f3
1, fieldContent11, fieldContent12, fieldContent13
2, fieldContent21, fieldContent22, fieldContent23
3, fieldContent31, fieldContent32, fieldContent33
4, fieldContent41, fieldContent42, fieldContent43
5, fieldContent41, fieldContent42, fieldContent43


b-->
id, f1, f2, f3
1, fieldContent111, fieldContent12, fieldContent13
4, fieldContent41, fieldContent42, fieldContent43
6, fieldContent41, fieldContent42, fieldContent43
 */