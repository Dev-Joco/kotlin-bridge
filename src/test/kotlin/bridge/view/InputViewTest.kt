package bridge.view

import org.junit.jupiter.api.BeforeEach
import camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest
import camp.nextstep.edu.missionutils.test.NsTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

internal class InputViewTest : NsTest() {

    private lateinit var inputView: InputView

    @BeforeEach
    fun beforeEach() {
        inputView = InputView()
    }

    @ParameterizedTest
    @ValueSource(strings = ["3", "10", "20"])
    fun `다리길이입력_3to20숫자_정상`(input: String) {
        assertSimpleTest {
            run(input)
            inputView.readBridgeSize()
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["3a", ".5", "1,3", "1.2"])
    fun `다리길이입력_문자포함_에러`(input: String) {
        assertSimpleTest {
            val thrown = assertThrows<IllegalArgumentException> {
                runException(input)
                inputView.readBridgeSize()
            }
            assertThat(thrown.message).contains(ERROR_MESSAGE)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["-1", "0", "2", "21", "100"])
    fun `다리길이입력_벗어난범위_에러`(input: String) {
        assertSimpleTest {
            val thrown = assertThrows<IllegalArgumentException> {
                runException(input)
                inputView.readBridgeSize()
            }
            assertThat(thrown.message).contains(ERROR_MESSAGE)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["U", "D"])
    fun `이동할칸입력_UorD_정상`(input: String) {
        assertSimpleTest {
            run(input)
            inputView.readMoving()
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["u", "d", "R", "Q", "1", "UD"])
    fun `이동할칸입력_notUorD_에러`(input: String) {
        assertSimpleTest {
            val thrown = assertThrows<IllegalArgumentException> {
                runException(input)
                inputView.readMoving()
            }
            assertThat(thrown.message).contains(ERROR_MESSAGE)
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["R", "Q"])
    fun `게임재시작종료입력_RorQ_정상`(input: String) {
        assertSimpleTest {
            run(input)
            inputView.askRetryGame()
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["U", "D", "r", "q", "RQ"])
    fun `게임재시작종료입력_notRorQ_에러`(input: String) {
        assertSimpleTest {
            val thrown = assertThrows<IllegalArgumentException> {
                runException(input)
                inputView.askRetryGame()
            }
            assertThat(thrown.message).contains(ERROR_MESSAGE)
        }
    }

    override fun runMain() {}

    companion object {
        private const val ERROR_MESSAGE = "[ERROR]"
    }
}
