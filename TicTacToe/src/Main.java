import java.util.Arrays;
import java.util.Scanner;
public class Main {
    //Board in a 2-dimensional array
    public static String[][] board ={
            {"1","2","3"},
            {"4","5","6"},
            {"7","8","9"}};

    public static int numberOfMoves = 0;

    public static void main(String[] args)
    {
        int choice;
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            printBoard();
            choice = choiceInput();
            choiceMover(choice, "X");
            if(checkVictory())
            {
                break;
            }
            AIMove();
            if(checkVictory())
            {
                break;
            }
        }
        System.out.println("Press any key to exit");
        scanner.nextLine();

    }

    //Prints the board.
    public static void printBoard(){
        for (int y = 0; y < board.length; y++){
            for (int x = 0; x<board[y].length; x++)
            {
                System.out.print(board[y][x]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    //Checks if a choice is available.
    public static boolean choiceAvailable(int input){
        for (int y = 0; y < board.length; y++){
            for (int x = 0; x < board[y].length; x++)
            {
                if(board[y][x].equals(String.valueOf(input)))
                {
                    return true;
                }
            }

        }
        return false;
    }

    //Takes in a value from the player and checks if it is an available move.
    public static int choiceInput(){
        Scanner scanner = new Scanner(System.in);
        int yourChoice = 0;
        while(!choiceAvailable(yourChoice)) {
            System.out.println("Write corresponding number to place your x:");
            try
            {
                yourChoice = scanner.nextInt();
            }
            catch (Exception e)
            {
                System.out.println("Please write an integer");
                scanner.next();
            }
        }
        return yourChoice;

    }

    //Places the players move.
    public static void choiceMover(int choice, String player){
        board[(choice-1) / 3][((choice-1) % 3)] = player;
        numberOfMoves++;
    }

    //Checks if there is a 3 in a row somewhere.
    public static boolean checkVictory()
    {
        String victory = "";

        //Horizontal
        if(board[0][0] == board[0][1]&&  board[0][1] == board [0][2])
        {
            victory = board[0][0];
        }
        else if(board[1][0] == board[1][1]&&  board[1][1] == board [1][2])
        {
            victory = board[1][0];
        }
        else if(board[2][0] == board[2][1]&&  board[2][1] == board [2][2])
        {
            victory = board[2][0];
        }

        //Vertical
        else if(board[0][0] == board[1][0]&&  board[1][0] == board [2][0])
        {
            victory = board[0][0];
        }
        else if(board[0][1] == board[1][1]&&  board[1][1] == board [2][1])
        {
            victory = board[0][1];
        }
        else if(board[0][2] == board[1][2]&&  board[1][2] == board [2][2])
        {
            victory = board[0][2];
        }


        //Diagonal
        else if(board[0][0] == board[1][1]&&  board[1][1] == board [2][2]) {
            victory = board[0][0];
        }
        else if(board[2][0] == board[1][1]&&  board[1][1] == board [0][2])
        {
            victory = board[2][0];
        }

        //If there is a winner
        if(victory != "")
        {
            printBoard();
            System.out.println("Player: " + victory + " wins!");
            return true;
        }
        //If there is a draw
        else if (numberOfMoves == 9)
        {
            printBoard();
            System.out.println("Draw! No player wins!");
            return true;
        }
        else
        {
            return false;
        }
    }

    //Decides wich move the AI does
    public static void AIMove()
    {
        if (choiceAvailable(5)){
            choiceMover(5, "O");
        }
        else
        {
            choiceMover(pointChecker(), "O");
        }
    }

    //Returns the number of the move that gives the highest point.
    public static Integer pointChecker()
    {
        int maxPoints = 0;
        int maxNumber = 0;

        //Goes through all the options
        for (int choice = 1; choice < 10; choice++)
        {
            if(choiceAvailable(choice))
            {
                int points = 0;
                int numX = 0;
                int numO = 0;

                //Check horizontal
                for (int x = 0; x < 3; x++)
                {
                    if (board[(choice-1) / 3][x]== "O")
                    {
                        numO++;
                    }
                    else if (board[(choice-1) / 3][x]== "X")
                    {
                        numX++;
                    }
                }
                if (pointCalculator(numX, numO) > points)
                {
                    points = pointCalculator(numX, numO);
                }

                //Reset values
                numX = 0;
                numO = 0;

                //Check Vertical
                for (int y = 0; y < 3; y++)
                {
                    if (board[y][((choice-1) % 3)] == "O")
                    {
                        numO++;
                    }
                    else if (board[y][((choice-1) % 3)] == "X")
                    {
                        numX++;
                    }
                }
                if (pointCalculator(numX, numO) > points)
                {
                    points = pointCalculator(numX, numO);
                }

                //Reset values
                numX = 0;
                numO = 0;

                //Check diagonal
                if(choice == 1 || choice == 5 || choice == 9)
                {
                    for (int xy = 0; xy < 3; xy++)
                    {
                        if (board[xy][xy] == "O")
                        {
                            numO++;
                        } else if (board[xy][xy] == "X")
                        {
                            numX++;
                        }
                    }
                }
                else if(choice == 3 || choice == 5 || choice == 7)
                {
                    for (int xy = 0; xy < 3; xy++)
                    {
                        if (board[xy][2 - xy] == "O")
                        {
                            numO++;
                        } else if (board[xy][2 - xy] == "X")
                        {
                            numX++;
                        }
                    }
                }
                if (pointCalculator(numX, numO) > points)
                {
                    points = pointCalculator(numX, numO);
                }

                if (points > maxPoints)
                {
                    maxPoints = points;
                    maxNumber = choice;
                }
            }
        }

        //If all the moves are equal, pick the first move available.
        if (maxNumber == 0)
        {
            for (int firstChoice = 1; firstChoice < 10; firstChoice++)
            {
                if (choiceAvailable(firstChoice))
                {
                    return firstChoice;
                }
            }
        }
        else
        {
            return maxNumber;
        }
        return 0;
    }


    //Calculates points based on how manu X and O are in one line
    public static Integer pointCalculator(int numX, int numO)
    {
        if (numO == 1 && numX == 0)
        {
            return 2;
        }
        else if (numO == 2 && numX == 0)
        {
            return 4;
        }
        else if (numO == 0 && numX == 2)
        {
            return 3;
        }
        else
        {
            return 0;
        }
    }
}