package canfield;

import ucb.gui.Pad;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;

import javax.imageio.ImageIO;

import java.io.InputStream;
import java.io.IOException;
import java.awt.geom.RoundRectangle2D;

/** A widget that displays a Pinball playfield.
 *  @author P. N. Hilfinger
 */
class GameDisplay extends Pad {

    /** Color of display field. */
    private static final Color BACKGROUND_COLOR = Color.white;

    /* Coordinates and lengths in pixels unless otherwise stated. */

    /** Preferred dimensions of the playing surface. */
    private static final int BOARD_WIDTH = 750, BOARD_HEIGHT = 600;

    /** Displayed dimensions of a card image. */
    private static final int CARD_HEIGHT = 125, CARD_WIDTH = 90;

    /** Displayed dimensions of a card image. */
    private final int[] tabStart = {200, 250};

    /** Displayed dimensions of a card image. */
    private final int[] fxy = {200, 50};

    /** Displayed dimensions of a card image. */
    private final int[] resxy = {50, 250};

    /** Displayed dimensions of a card image. */
    private final int[] stock = {50, 440};

    /** Displayed dimensions of a card image. */
    private final int[] waste = {150, 440};

    /** Displayed dimensions of a card image. */
    private final int casc = 25;

    /** Displayed dimensions of a card image. */
    private final int space = 100;

    /** A graphical representation of GAME. */
    public GameDisplay(Game game) {
        _game = game;
        setPreferredSize(BOARD_WIDTH, BOARD_HEIGHT);
    }

    /** Return an Image read from the resource named NAME. */
    private Image getImage(String name) {
        InputStream in =
            getClass().getResourceAsStream("/canfield/resources/" + name);
        try {
            return ImageIO.read(in);
        } catch (IOException excp) {
            return null;
        }
    }

    /** Return an Image of CARD. */
    private Image getCardImage(Card card) {
        return getImage("playing-cards/" + card + ".png");
    }

    /** Return an Image of the back of a card. */
    private Image getBackImage() {
        return getImage("playing-cards/blue-back.png");
    }

    /** Draw CARD at X, Y on G. */
    private void paintCard(Graphics2D g, Card card, int x, int y) {
        if (card != null) {
            g.drawImage(getCardImage(card), x, y,
                        CARD_WIDTH, CARD_HEIGHT, null);
        }
    }

    /** Draw card back at X, Y on G. */
    private void paintBack(Graphics2D g, int x, int y) {
        g.drawImage(getBackImage(), x, y, CARD_WIDTH, CARD_HEIGHT, null);
    }

    /** Rectangular place holder for cards for G. */
    private void drawRectangles(Graphics2D g) {
        g.setPaint(Color.BLACK);

        RoundRectangle2D wasteRec = new RoundRectangle2D.Float(waste[0],
                waste[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(wasteRec);
        RoundRectangle2D resRec = new RoundRectangle2D.Float(resxy[0],
                resxy[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(resRec);
        RoundRectangle2D stockRec = new RoundRectangle2D.Float(stock[0],
                stock[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(stockRec);
        RoundRectangle2D f1Rec = new RoundRectangle2D.Float(fxy[0] + space,
                fxy[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(f1Rec);
        RoundRectangle2D f2Rec = new RoundRectangle2D.Float(fxy[0] + space * 2,
                fxy[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(f2Rec);
        RoundRectangle2D f3Rec = new RoundRectangle2D.Float(fxy[0] + space * 3,
                fxy[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(f3Rec);
        RoundRectangle2D f4Rec = new RoundRectangle2D.Float(fxy[0] + space * 4,
                fxy[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(f4Rec);
    }

    @Override
    public synchronized void paintComponent(Graphics2D g) {
        g.setColor(BACKGROUND_COLOR);
        Rectangle b = g.getClipBounds();
        g.fillRect(0, 0, b.width, b.height);

        RoundRectangle2D t1Rec = new RoundRectangle2D.Float(tabStart[0] + space
                * 1, tabStart[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(t1Rec);
        RoundRectangle2D t2Rec = new RoundRectangle2D.Float(tabStart[0] + space
                * 2, tabStart[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(t2Rec);
        RoundRectangle2D t3Rec = new RoundRectangle2D.Float(tabStart[0] + space
                * 3, tabStart[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(t3Rec);
        RoundRectangle2D t4Rec = new RoundRectangle2D.Float(tabStart[0] + space
                * 4, tabStart[1], CARD_WIDTH, CARD_HEIGHT, 10, 10);
        g.draw(t4Rec);

        paintCard(g, _game.topReserve(), resxy[0], resxy[1]);
        for (int i = 1; i < 5; i++) {
            int tsize = _game.tableauSize(i);
            if (_game.topFoundation(i) != null) {
                paintCard(g, _game.topFoundation(i), fxy[0] + i
                    * space, fxy[1]);
            }
            for (int j = 0; j < tsize; j++) {
                paintCard(g, _game.getTableau(i, tsize - j - 1), tabStart[0]
                    + i * space, tabStart[1] + casc * j);
            }
        }

        if (!_game.stockEmpty()) {
            paintBack(g, stock[0], stock[1]);
        }

        if (_game.topWaste() != null) {
            paintCard(g, _game.topWaste(), waste[0], waste[1]);
        }
        drawRectangles(g);
    }

    /** Game I am displaying. */
    private final Game _game;

}
