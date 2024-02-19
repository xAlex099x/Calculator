import java.util.Scanner;

public class Main {
    static int romanInput = 0;

    public static void main(String[] args) {

        String userInput = userInput();
        System.out.println(calc(userInput));

    }

    static String calc(String input) {

        //Проверка на недопустимые символы в строке
        checkInput(input);

        //Разбиваем на операнды и записываем в переменные
        String[] operands = userInputToOperands(input);

        //Проверка наличия римских чисел и перевод в арабские
        romanToInt(operands);

        //Попытка парсинга строки в целое число, в случае неудачи, будет выброшено исключение.
        int operand1 = Integer.parseInt(operands[0]);
        int operand2 = Integer.parseInt(operands[1]);

        //Калькулятор может принимать операнды больше 10.
        //Но по условию задачи, должен выбрасывать исключение.
        if (operand1 > 10 | operand2 > 10) throw new RuntimeException("Операнд не должен быть больше 10.");
        if (operand1 < 1 | operand2 < 1) throw new RuntimeException("Операнд не должен быть меньше 1.");

        //Ищем оператор по индексу в массиве
        int operatorIndex = findOperatorIndex(input);

        //Выполняем операцию в зависимости от оператора
        int result = operation(operand1, operand2, operatorIndex);

        //Вывод результата и перевод арабского результата в римский, если нужно
        return showResult(result);
    }

    static String userInput() {
        System.out.println("Калькулятор может принимать числа от 1 до 10, с оператором '+', '-', '*', '/', в одну строку. \nОперанды могут быть написаны только римскими или только арабскими цифрами.");
        System.out.println("Введите задачу: ");

        String userInput = new Scanner(System.in).nextLine();
        userInput = userInput.replaceAll(" ", "").toUpperCase();
        return userInput;
    }

    static void checkInput(String userInput) {

        for (int i = 0; i < userInput.length(); i++) {
            if (Character.toString(userInput.charAt(i)).matches("[^0-9,IXV+\\-*/]")) {
                throw new RuntimeException("Некорректные значения.");
            }
        }
    }

    static String[] userInputToOperands(String userInput) {
        String[] operands = userInput.split("[+\\-*/]");
        if (operands.length > 2) throw new RuntimeException("Некорректный ввод. Больше 2х операндов.");
        return operands;
    }

    static int findOperatorIndex(String userInput) {

        final String[] symbol = {"+", "-", "*", "/"};

        int operatorIndex = -1;
        for (int i = 0; i <= 3; i++) {
            if (userInput.contains(symbol[i])) {
                operatorIndex = i;
            }
        }
        return operatorIndex;
    }

    static void romanToInt(String[] data) {
        String[] romanNumerals = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};

        for (int i = 0; i < data.length; i++) {
            for (int y = 0; y < romanNumerals.length; y++) {
                if (data[i].equals(romanNumerals[y])) {
                    data[i] = String.valueOf(y + 1);
                    romanInput++;
                }
            }
        }
        //Калькулятор работает если один операнд написан римскими цифрами, а другой арабскими.
        //Но по условиям задачи, должен выбрасывать исключение.
        if (romanInput == 1)
            throw new RuntimeException("Оба операнда, должны быть написаны римскими или арабским цифрами.");
    }

    static int operation(int operand1, int operand2, int operatorIndex) {
        return switch (operatorIndex) {
            case 0 -> operand1 + operand2;
            case 1 -> operand1 - operand2;
            case 2 -> operand1 * operand2;
            case 3 -> operand1 / operand2;
            default -> -1;
        };
    }

    static String IntToRoman(int num) {
        String[] keys = new String[]{"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] vals = new int[]{1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();
        int index = 0;

        while (index < keys.length) {
            while (num >= vals[index]) {
                int d = num / vals[index];
                num = num % vals[index];
                result.append(keys[index].repeat(d));
            }
            index++;
        }
        return result.toString();
    }

    static String showResult(int result) {
        if (romanInput > 0 & result < 1)
            throw new RuntimeException("Исключительная ситуация. Результат меньше единицы.");
        if (romanInput > 0) {
            return "Результат: " + IntToRoman(result);
        } else {
            return "Результат: " + result;
        }
    }

}
