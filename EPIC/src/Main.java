import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        displayMainMenu();

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                int input = sc.nextInt();

                switch (input) {
                    case 1:
                        displayLatexMenu();
                        break;
                    case 2:
                        displayMatrixMenu();
                        break;
                    case 3:
                        displayStatisticsMenu();
                        break;
                    case 4:
                        displayUnitConversionMenu();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Invalid input. Try again.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Try again.");
                sc.nextLine();
            }
        }
    }

    public static void displayMainMenu() {
        System.out.printf(
                """
                        ███╗   ███╗ █████╗ ██╗███╗   ██╗    ███╗   ███╗███████╗███╗   ██╗██╗   ██╗
                        ████╗ ████║██╔══██╗██║████╗  ██║    ████╗ ████║██╔════╝████╗  ██║██║   ██║
                        ██╔████╔██║███████║██║██╔██╗ ██║    ██╔████╔██║█████╗  ██╔██╗ ██║██║   ██║
                        ██║╚██╔╝██║██╔══██║██║██║╚██╗██║    ██║╚██╔╝██║██╔══╝  ██║╚██╗██║██║   ██║
                        ██║ ╚═╝ ██║██║  ██║██║██║ ╚████║    ██║ ╚═╝ ██║███████╗██║ ╚████║╚██████╔╝
                        ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝╚═╝  ╚═══╝    ╚═╝     ╚═╝╚══════╝╚═╝  ╚═══╝ ╚═════╝\s
                        """
        );
        System.out.printf("\n" + """
                ╔════════════════════════════════╗
                ║ 1. LaTeX Scientific Calculator ║
                ║ 2. Matrix Calculator           ║
                ║ 3. Statistics Calculator       ║
                ║ 4. Unit Conversions            ║
                ║ 5. Exit                        ║
                ╚════════════════════════════════╝
                """);
        System.out.println("\nSelect an option from the menu.");
        System.out.printf("\nChoice: ");
    }

    public static void displayLatexMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("LaTex Scientific Calculator\nEnter :q to quit.");

        try {
            while (true) {
                String input = sc.nextLine();

                if (input.equals(":q")) {
                    System.out.println("Exiting...");
                    displayMainMenu();
                    return;
                } else {
                    System.out.println(input + " = " + new LatexInterpreter(input).sum);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void displayMatrixMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Define row length.");
                int rowLength = sc.nextInt();
                System.out.println("Define column length.");
                int columnLength = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input, try again.");
                sc.nextLine();
            }
        }
    }

    public static void displayStatisticsMenu() {

    }

    public static void displayUnitConversionMenu() {

    }
}
