package calculator.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// 문자열을 입력받아 유효성을 검사하는 클래스
public class Regex {

    public Regex() {
    }

    public ArrayList<Integer> analyze_string(String input) {
        //
        return Arrays.stream(RegexRule.values())
                .map(rule -> new Object[]{rule, rule.getPattern().matcher(input)})
                .filter(data -> ((Matcher) data[1]).matches())
                .findFirst()
                .map(data -> parseNumbers((RegexRule) data[0], (Matcher) data[1]))
                .orElseThrow(IllegalArgumentException::new);
    }

    public ArrayList<Integer> parseNumbers(RegexRule rule, Matcher matcher) {
        if (rule == RegexRule.NO_STRING || rule == RegexRule.CUSTOM_DELIMITER_NO_STRING) {
            return new ArrayList<>();
        }

        String numberString = "";
        String delimiter = "";

        if (rule == RegexRule.NORMAL_DELIMITER) {
            delimiter = "[,:]";
            numberString = matcher.group(1);
        } else if (rule == RegexRule.CUSTOM_DELIMITER) {
            delimiter = Pattern.quote(matcher.group(1));
            numberString = matcher.group(2);
        }

        try {
            return Arrays.stream(numberString.split(delimiter))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "\"잘못된 값을 입력했습니다.\\n쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열이어야 합니다.\\n커스텀 구분자를 사용하려면 문자열 맨 앞부분의 //와 \\\\n 사이에 원하는 구분자를 위치하게 해주세요.\"",
                    e);
        }
    }
}
