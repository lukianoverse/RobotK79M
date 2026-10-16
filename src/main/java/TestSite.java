import robot.Robot;

import java.util.Scanner;

public class TestSite {
    private static final Scanner input = new Scanner(System.in);

    private static final String MENU = """
        ╔══════════════════════════════════════╗
        ║ доступные команды:                   ║
        ║ 1 = свободное управление             ║
        ║ 2 = прогнать последовательность      ║
        ║ 3 = демо-последовательность №1       ║
        ║ 4 = демо-последовательность №2       ║
        ║ X = выход                            ║
        ╚══════════════════════════════════════╝
        """;
    private static final String PLAY_PROMPT = """
           *  *  *  *  *  *  *  *  *  *  *  *  *
           *  даём роботу команды:              *
           * - - -                              *
           *  S = шаг вперёд                    *
           *  L = поворот направления налево    *
           *  R = поворот направления направо   *
           * - - -                              *
           *  H = показать записанный маршрут   *
           *  Х = конец испытания               *
           * - - -                              *
            *  *  *  *  *  *  *  *  *  *  *  *  *
           """;
    private static final String DEMO_1 = "SSLSLSLSSRSRS";
    private static final String DEMO_2 = "LSSSS";

    static void mainMenu(Robot robot) {
        boolean exit = false;
        while (!exit) {
            IO.println(MENU);
            String command = input.nextLine().strip().toUpperCase();
            switch (command) {
                case "1" -> play(robot);
                case "2" -> playSequence(robot);
                case "3" -> {
                    robot.runCommandSequence(DEMO_1);
                    finalizeTest(robot);
                }
                case "4" -> {
                    robot.runCommandSequence(DEMO_2);
                    finalizeTest(robot);
                }
                case "X", "Х" -> exit = true;
            }
        }
    }

    private static void fullReport(Robot robot) {
        IO.println(robot.commandsExecutedReport());
        IO.println(robot.travelReport());
        IO.println(robot.returnsReport());
    }

    private static void finalizeTest(Robot robot) {
        fullReport(robot);
        robot.reset();
    }

    private static void playSequence(Robot robot) {
        IO.println("Введите в строку последовательность команд, которые нужно прогнать:");
        robot.runCommandSequence(input.nextLine());
        finalizeTest(robot);
    }

    private static void play(Robot robot) {
        IO.println(PLAY_PROMPT);
        boolean end = false;
        while (!end) {
            IO.println("положение: %s направление: %s"
                    .formatted(robot.getPosition(), robot.getArrow()));
            String command = input.nextLine().strip().toUpperCase();
            switch (command) {
                case "X", "Х" -> end = true;
                case "H", "И" -> IO.println(robot.travelReport());
                default -> robot.runACommand(command.charAt(0));
            }
        }
        finalizeTest(robot);
    }

    public static void main() {
        IO.println("Добро пожаловать на испытательную площадку новой модели K79-M!");
        mainMenu(new Robot());
        IO.println("Спасибо за проведение испытаний!");
    }
}
