package calculator;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

class ApplicationTest extends NsTest {
    @Test
    void 빈_문자열() {
        assertSimpleTest(() -> {
            run("\n"); // 사용자가 엔터키만 눌렀을 때 아무 문자열이 없게 된다.
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    void 숫자_문자열() {
        assertSimpleTest(() -> {
            run("3434");
            assertThat(output()).contains("결과 : 3434");
        });
    }

    @Test
    void 콜론_구분자_사용() {
        assertSimpleTest(() -> {
            run("1:2:3:4");
            assertThat(output()).contains("결과 : 10");
        });
    }

    @Test
    void 쉼표_구분자_사용() {
        assertSimpleTest(() -> {
            run("1,2,3,4");
            assertThat(output()).contains("결과 : 10");
        });
    }

    @Test
    void 콜론_쉼표_구분자_사용() {
        assertSimpleTest(() -> {
            run("1,2:3,4:5");
            assertThat(output()).contains("결과 : 15");
        });
    }

    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 커스텀_구분자_사용2() {
        assertSimpleTest(() -> {
            run("//;\\n");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    void 커스텀_구분자_여러개_사용() {
        assertSimpleTest(() -> {
            run("//;[\\n1;2[3;4");
            assertThat(output()).contains("결과 : 10");
        });
    }

    @Test
    void 커스텀_구분자_여러개_사용2() {
        assertSimpleTest(() -> {
            run("//;[)\\n1;2[3;4)5");
            assertThat(output()).contains("결과 : 15");
        });
    }

    @Test
    void 음수_예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("-1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 음수_예외_테스트2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//;\\n1;-2;3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 덧셈_예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("a,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 덧셈_예외_테스트2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,a,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 덧셈_예외_테스트3() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,2,a"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 덧셈_예외_테스트4() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//{\\n1{a{3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 정의되지_않은_구분자_예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1.2.3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    // 커스텀 구분자를 정의했다면, 해당 구분자를 사용하도록 합니다.
    @Test
    void 커스텀_구분자_예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//&\\n1,2,3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    // 커스텀 구분자를 정의했다면, 해당 구분자를 사용하도록 합니다.
    @Test
    void 커스텀_구분자_예외_테스트2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//&\\n1,2&3"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 구분자로_끝나는_예외_테스트() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("1,2,3,"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 구분자로_끝나는_예외_테스트2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//&\\n1&2&3&"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자가_빈_문자열() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//\\n"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자가_빈_문자열2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//\\n1234"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 커스텀_구분자가_빈_문자열3() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("//\\n1234,23"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 완전히_유효하지_않은_문자열() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("..."))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 완전히_유효하지_않은_문자열2() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException(";*#%"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 완전히_유효하지_않은_문자열3() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("aabb"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 완전히_유효하지_않은_문자열4() {
        assertSimpleTest(() ->
                assertThatThrownBy(() -> runException("a12"))
                        .isInstanceOf(IllegalArgumentException.class)
        );
    }


    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
