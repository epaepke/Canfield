package canfield;

import static org.junit.Assert.*;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;

/** Tests of the Game class.
 *  @author
 */

public class GameTest {

    /** Example. */
    @Test
    public void testInitialScore() {
        Game g = new Game();
        g.deal();
        assertEquals(5, g.getScore());
    }

    @Test
    public void testUndo() {
        Game g = new Game();
        g.deal();
        g.stockToWaste();
        Card initWaste = g.topWaste();
        g.stockToWaste();
        Card secWaste = g.topWaste();
        g.undo();
        assertEquals(initWaste, g.topWaste());
        assertThat(secWaste, not(g.topWaste()));
    }
}
