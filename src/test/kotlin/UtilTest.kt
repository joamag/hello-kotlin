import kotlin.test.Test
import kotlin.test.assertEquals

internal class UtilTest {

    @Test
    fun testSum() {
        assertEquals(42, Util.sum(40, 2))
        assertEquals(80, Util.sum(40, 40))
    }
}
