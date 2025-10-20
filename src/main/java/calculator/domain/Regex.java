package calculator.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// 문자열을 입력받아 유효성을 검사하는 클래스
public class Regex {
    public ArrayList<Integer> analyze_string(String input) {
        return Arrays.stream(RegexRule.values())
                .map(rule -> new Object[]{rule, rule.getPattern().matcher(input)})
                .filter(data -> ((Matcher) data[1]).matches())
                .findFirst()
                .map(data -> parseNumbers((RegexRule) data[0], (Matcher) data[1]))
                .orElseThrow(() -> new IllegalArgumentException(
                        "지원하지 않는 입력 형식이거나 구분자가 연속/마지막에 사용되었습니다."));
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
            String delimiter_list = matcher.group(1);
            delimiter = Arrays.stream(delimiter_list.split(""))
                    .map(Pattern::quote)
                    .collect(Collectors.joining("|"));
            numberString = matcher.group(2);
        }

        try {
            return Arrays.stream(numberString.split(delimiter))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(
                    "입력값에 숫자가 아닌 문자가 포함되었습니다.",
                    e);
        }
    }
}
