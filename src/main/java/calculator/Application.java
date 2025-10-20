package calculator;

import calculator.domain.Calculator;
import calculator.domain.Regex;
import camp.nextstep.edu.missionutils.Console;
import java.util.ArrayList;

public class Application {
    public static void main(String[] args) throws IllegalArgumentException {
        // 입력받기
        System.out.println("덧셈할 문자열을 입력해 주세요.");
        String input = Console.readLine();

        int result = 0;

        // 메인 로직
        Regex regex = new Regex();
        Calculator calculator = new Calculator();
        ArrayList<Integer> numbers = regex.analyzeString(input);
        result = calculator.calc(numbers);

        // 출력하기
        System.out.println("결과 : " + result);
    }
}
