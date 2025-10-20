package calculator.domain;

import java.util.ArrayList;

// ArrayList 내의 수의 합을 구합니다.
public class Calculator {
    // ArrayList 내의 모든 수(추출된 숫자들)의 합을 구하는 메서드
    public int calc(ArrayList<Integer> numbers) {
        return numbers.stream().mapToInt(i -> i).sum();
    }
}
