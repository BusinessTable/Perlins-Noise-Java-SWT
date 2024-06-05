package ch.detisch.mapgeneration;

import java.util.ArrayList;

import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Davinci {

    private Display display;
    private Shell shell;
    private Canvas canvas;
    private GC gc;
    private int boundry = 500;
    private int viewBoxSize = 50;
    private int mapSize = 1000;
    private Vector2 playerPos;

    private final static int OFFSET = 100;
    private final static int MAX_COLOR = 255;

    private final static int W = 119;
    private final static int A = 97;
    private final static int S = 115;
    private final static int D = 100;

    public Davinci(int boundry, int viewBoxSize, int mapSize) {
        this.viewBoxSize = viewBoxSize > 0 ? viewBoxSize : 50;
        this.mapSize = mapSize > 0 ? mapSize : 1000;
        this.boundry = boundry > 0 ? boundry : 500;
        this.playerPos = new Vector2((this.mapSize / 2) - (this.viewBoxSize / 2),
                (this.mapSize / 2) - (this.viewBoxSize / 2));
        setUp();
    }

    public void setUp() {
        display = new Display();
        shell = new Shell(display);
        shell.setLayout(new GridLayout(1, false));
        shell.setSize(boundry, boundry);

        canvas = new Canvas(shell, 0);
        GridData canvasGridData = new GridData();
        canvasGridData.horizontalAlignment = GridData.FILL;
        canvasGridData.verticalAlignment = GridData.FILL;
        canvasGridData.grabExcessHorizontalSpace = true;
        canvasGridData.grabExcessVerticalSpace = true;
        canvas.setLayoutData(canvasGridData);
        canvas.setBackground(new Color(display, 0, 0, 0));

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.keyCode) {
                    case W:
                        playerPos.setY(playerPos.getY() - 1);
                        break;
                    case A:
                        playerPos.setX(playerPos.getX() - 1);
                        break;
                    case S:
                        playerPos.setY(playerPos.getY() + 1);
                        break;
                    case D:
                        playerPos.setX(playerPos.getX() + 1);
                        break;
                }
            }
        });

        gc = new GC(canvas);
    }

    public void open() {
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    public void drawMap(ArrayList<ArrayList<Float>> map) {
        int tileWidth = canvas.getBounds().width / viewBoxSize;
        int tileHeight = canvas.getBounds().height / viewBoxSize;

        Image image = new Image(display, canvas.getBounds().width + OFFSET, canvas.getBounds().height + OFFSET);

        for (int i = 0; i < viewBoxSize; i++) {
            for (int j = 0; j < viewBoxSize; j++) {
                drawTile(map.get(i + playerPos.getX()).get(j + playerPos.getY()), tileWidth, tileHeight,
                        i * tileWidth, j * tileHeight, image);
            }
        }
        gc.drawImage(image, 0, 0);
    }

    private void drawTile(float value, int width, int height, int x, int y, Image image) {

        Color color = new Color(Display.getCurrent(), 0, 0, 0);

        int ColourValue = (int) (MAX_COLOR * value);
        if (value < 0) {
            ColourValue = (int) (MAX_COLOR * -value);
        }

        if (ColourValue > 255)
            ColourValue = 255;
        else if (ColourValue < 0)
            ColourValue = 0;

        if (value > 0) {
            color = new Color(Display.getCurrent(), 0, ColourValue, 0);
        } else if (value < 0) {
            color = new Color(Display.getCurrent(), 0, 0, ColourValue);
        } else {
            color = new Color(Display.getCurrent(), 0, 0, 0);
        }

        GC imageGc = new GC(image);
        imageGc.setBackground(color);
        imageGc.fillRectangle(x, y, width, height);
        imageGc.dispose();
    }

}
