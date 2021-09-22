import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Write the length of the board: ");
        System.out.println("Write the number of generation: ");
        Game g = new Game(sc.nextInt(), sc.nextInt());
    }

}
