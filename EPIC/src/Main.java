import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insert equation: ");
        String input = sc.nextLine();
        //System.out.println("Hello world!");
        LatexInterpreter interpreter = new LatexInterpreter(input);
        System.out.println("Answer: " + interpreter);
    }
}
