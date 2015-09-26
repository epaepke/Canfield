package canfield;

import ucb.gui.TopLevel;
import ucb.gui.LayoutSpec;

import java.awt.event.MouseEvent;



/** A top-level GUI for Canfield solitaire.
 *  @author E. D. Paepke
 */
class CanfieldGUI extends TopLevel {
    /** The game I am consulting. */
    private int xDrag = 0;
    /** The game I am consulting. */
    private int yDrag = 0;
    /** The game I am consulting. */
    private int sxDrag;
    /** The game I am consulting. */
    private int syDrag;
    /** The game I am consulting. */
    private int endxDrag;
    /** The game I am consulting. */
    private int endyDrag;
    /** The game I am consulting. */
    private int[][] t;
    /** The game I am consulting. */
    private int[][] f;
    /** The game I am consulting. */
    private int[] s1;
    /** The game I am consulting. */
    private int[] r1;
    /** The game I am consulting. */
    private final int[] stock = {50, 140, 440, 560};
    /** The game I am consulting. */
    private final int[] waste = {150, 240, 440, 560};
    /** The game I am consulting. */
    private final int[] res = {50, 140, 250, 370};
    /** The game I am consulting. */
    private final int[] f1 = {300, 390, 50, 170};
    /** The game I am consulting. */
    private final int[] f2 = {400, 490, 50, 170};
    /** The game I am consulting. */
    private final int[] f3 = {500, 590, 50, 170};
    /** The game I am consulting. */
    private final int[] f4 = {600, 690, 50, 170};
    /** The game I am consulting. */
    private final int[] t1 = {300, 390, 250, 370};
    /** The game I am consulting. */
    private final int[] t2 = {400, 490, 250, 370};
    /** The game I am consulting. */
    private final int[] t3 = {500, 590, 250, 370};
    /** The game I am consulting. */
    private final int[] t4 = {600, 690, 250, 370};


    /** A new window with given TITLE and displaying GAME. */
    CanfieldGUI(String title, Game game) {
        super(title, true);
        _game = game;
        //addLabel("Stock",
          //       new LayoutSpec("y", 0, "x", 0));
        addButton("Quit", "quit", new LayoutSpec("y", 0, "x", 0));
        addButton("New", "newGame", new LayoutSpec("y", 0, "x", 1));
        addButton("Undo", "undoMove", new LayoutSpec("y", 0, "x", 2));

        _display = new GameDisplay(game);
        add(_display, new LayoutSpec("y", 2, "width", 2));
        _display.setMouseHandler("click", this, "mouseClicked");
        _display.setMouseHandler("release", this, "mouseReleased");
        _display.setMouseHandler("drag", this, "mouseDragged");

        display(true);
    }

    /** Determines if card at X and Y is in the waste pile
     *  returns BOOLEAN. */
    public boolean isWaste(int x, int y) {
        if (x > waste[0] && x < waste[1] && y > waste[2] && y < waste[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isStock(int x, int y) {
        if (x > stock[0] && x < stock[1] && y > stock[2] && y < stock[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isFoundation(int x, int y) {
        if (isFound1(x, y) || isFound2(x, y) || isFound3(x, y)
                || isFound4(x, y)) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isReserve(int x, int y) {
        if (x > res[0] && x < res[1] && y > res[2] && y < res[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isTab1(int x, int y) {
        if (x > t1[0] && x < t1[1] && y > t1[2] && y < t1[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isTab2(int x, int y) {
        if (x > t2[0] && x < t2[1] && y > t2[2] && y < t2[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile and
     *  returns BOOLEAN. */
    public boolean isTab3(int x, int y) {
        if (x > t3[0] && x < t3[1] && y > t3[2] && y < t3[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile and
     *  returns BOOLEAN. */
    public boolean isTab4(int x, int y) {
        if (x > t4[0] && x < t4[1] && y > t4[2] && y < t4[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isFound1(int x, int y) {
        if (x > f1[0] && x < f1[1] && y > f1[2] && y < f1[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isFound2(int x, int y) {
        if (x > f2[0] && x < f2[1] && y > f2[2] && y < f2[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isFound3(int x, int y) {
        if (x > f3[0] && x < f3[1] && y > f3[2] && y < f3[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile.
     *  returns BOOLEAN. */
    public boolean isFound4(int x, int y) {
        if (x > f4[0] && x < f4[1] && y > f4[2] && y < f4[3]) {
            return true;
        }
        return false;
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveWaste(int x, int y) {
        if (isFoundation(x, y)) {
            _game.wasteToFoundation();
        } else if (isTab1(x, y)) {
            _game.wasteToTableau(1);
        } else if (isTab2(x, y)) {
            _game.wasteToTableau(2);
        } else if (isTab3(x, y)) {
            _game.wasteToTableau(3);
        } else if (isTab4(x, y)) {
            _game.wasteToTableau(4);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveReserve(int x, int y) {
        if (isFoundation(x, y)) {
            _game.reserveToFoundation();
        } else if (isTab1(x, y)) {
            _game.reserveToTableau(1);
        } else if (isTab2(x, y)) {
            _game.reserveToTableau(2);
        } else if (isTab3(x, y)) {
            _game.reserveToTableau(3);
        } else if (isTab1(x, y)) {
            _game.reserveToTableau(4);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveTab1(int x, int y) {
        if (isFoundation(x, y)) {
            _game.tableauToFoundation(1);
        } else if (isTab2(x, y)) {
            _game.tableauToTableau(1, 2);
        } else if (isTab3(x, y)) {
            _game.tableauToTableau(1, 3);
        } else if (isTab4(x, y)) {
            _game.tableauToTableau(1, 4);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveTab2(int x, int y) {
        if (isFoundation(x, y)) {
            _game.tableauToFoundation(2);
        } else if (isTab1(x, y)) {
            _game.tableauToTableau(2, 1);
        } else if (isTab3(x, y)) {
            _game.tableauToTableau(2, 3);
        } else if (isTab4(x, y)) {
            _game.tableauToTableau(2, 4);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveTab3(int x, int y) {
        if (isFoundation(x, y)) {
            _game.tableauToFoundation(3);
        } else if (isTab1(x, y)) {
            _game.tableauToTableau(3, 1);
        } else if (isTab2(x, y)) {
            _game.tableauToTableau(3, 2);
        } else if (isTab4(x, y)) {
            _game.tableauToTableau(3, 4);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveTab4(int x, int y) {
        if (isFoundation(x, y)) {
            _game.tableauToFoundation(4);
        } else if (isTab1(x, y)) {
            _game.tableauToTableau(4, 1);
        } else if (isTab2(x, y)) {
            _game.tableauToTableau(4, 2);
        } else if (isTab3(x, y)) {
            _game.tableauToTableau(4, 3);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveFound1(int x, int y) {
        if (isTab1(x, y)) {
            _game.foundationToTableau(1, 1);
        } else if (isTab2(x, y)) {
            _game.foundationToTableau(1, 2);
        } else if (isTab3(x, y)) {
            _game.foundationToTableau(1, 3);
        } else if (isTab4(x, y)) {
            _game.foundationToTableau(1, 4);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveFound2(int x, int y) {
        if (isTab1(x, y)) {
            _game.foundationToTableau(2, 1);
        } else if (isTab2(x, y)) {
            _game.foundationToTableau(2, 2);
        } else if (isTab3(x, y)) {
            _game.foundationToTableau(2, 3);
        } else if (isTab4(x, y)) {
            _game.foundationToTableau(2, 4);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveFound3(int x, int y) {
        if (isTab1(x, y)) {
            _game.foundationToTableau(3, 1);
        } else if (isTab2(x, y)) {
            _game.foundationToTableau(3, 2);
        } else if (isTab3(x, y)) {
            _game.foundationToTableau(3, 3);
        } else if (isTab4(x, y)) {
            _game.foundationToTableau(3, 4);
        }
    }

    /** Determines if card at X and Y is in the waste pile. */
    public void moveFound4(int x, int y) {
        if (isTab1(x, y)) {
            _game.foundationToTableau(4, 1);
        } else if (isTab2(x, y)) {
            _game.foundationToTableau(4, 2);
        } else if (isTab3(x, y)) {
            _game.foundationToTableau(4, 3);
        } else if (isTab4(x, y)) {
            _game.foundationToTableau(4, 4);
        }
    }

    /** Respond to "Quit" button. */
    public void quit(String dummy) {
        System.exit(1);
    }

    /** Announce a win if there is one, ask if user wants another. */
    public void newGame(String dummy) {
        _game.deal();
        _display.repaint();
    }

    /** Announce a win if there is one, ask if user wants another. */
    public void undoMove(String dummy) {
        _game.undo();
        _display.repaint();
    }


    /** Action in response to mouse-clicking event EVENT. */
    public synchronized void mouseClicked(MouseEvent event) {
        sxDrag = event.getX();
        syDrag = event.getY();
        if (isStock(sxDrag, syDrag)) {
            _game.stockToWaste();
        }
        xDrag = 0;
        yDrag = 0;
        _display.repaint();
    }

    /** Action in response to mouse-released event EVENT. */
    public synchronized void mouseReleased(MouseEvent event) {
        endxDrag = event.getX();
        endyDrag = event.getY();

        if (isWaste(xDrag, yDrag)) {
            moveWaste(endxDrag, endyDrag);
        } else if (isReserve(xDrag, yDrag)) {
            moveReserve(endxDrag, endyDrag);
        } else if (isTab1(xDrag, yDrag)) {
            moveTab1(endxDrag, endyDrag);
        } else if (isTab2(xDrag, yDrag)) {
            moveTab2(endxDrag, endyDrag);
        } else if (isTab3(xDrag, yDrag)) {
            moveTab3(endxDrag, endyDrag);
        } else if (isTab4(xDrag, yDrag)) {
            moveTab4(endxDrag, endyDrag);
        } else if (isFound1(xDrag, yDrag)) {
            moveFound1(endxDrag, endyDrag);
        } else if (isFound2(xDrag, yDrag)) {
            moveFound2(endxDrag, endyDrag);
        } else if (isFound3(xDrag, yDrag)) {
            moveFound3(endxDrag, endyDrag);
        } else if (isFound4(xDrag, yDrag)) {
            moveFound4(endxDrag, endyDrag);
        }
        xDrag = 0;
        yDrag = 0;
        endxDrag = 0;
        endyDrag = 0;
        _display.repaint();
    }

    /** Action in response to mouse-dragging event EVENT. */
    public synchronized void mouseDragged(MouseEvent event) {
        if (xDrag == 0 && yDrag == 0) {
            xDrag = event.getX();
            yDrag = event.getY();
        }
    }

    /** The board widget. */
    private final GameDisplay _display;

    /** The game I am consulting. */
    private final Game _game;

}
