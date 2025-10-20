package calculator.domain;

import java.util.regex.Pattern;

/**
 * 유효한 문자열 정의
 */
public enum RegexRule {
    NO_STRING("^$"), // 빈 문자열의 합은 0으로 간주합니다.
    CUSTOM_DELIMITER_NO_STRING("^//(\\D+)\\\\n$"), // 구분자만 지정한 문자열도 합을 0으로 간주합니다.
    NORMAL_DELIMITER("^(([0-9]+[:,])*([0-9]+)+)$"),
    CUSTOM_DELIMITER("^//(\\D+)\\\\n(.*[0-9]+)$");

    private final Pattern pattern;

    RegexRule(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public Pattern getPattern() {
        return pattern;
    }
}
