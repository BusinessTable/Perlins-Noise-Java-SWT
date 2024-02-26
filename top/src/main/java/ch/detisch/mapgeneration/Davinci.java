package ch.detisch.mapgeneration;

import java.util.ArrayList;

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

    private static int OFFSET = 100;
    private static int MAX_COLOR = 255;
    
    public Davinci() {
        setUp();
    }

    public void setUp() {
        display = new Display();
        shell = new Shell(display);
        shell.setLayout(new GridLayout(1, false));
        shell.setSize(500, 500);

        canvas = new Canvas(shell, 0);
        GridData canvasGridData = new GridData();
        canvasGridData.horizontalAlignment = GridData.FILL;
        canvasGridData.verticalAlignment = GridData.FILL;
        canvasGridData.grabExcessHorizontalSpace = true;
        canvasGridData.grabExcessVerticalSpace = true;
        canvas.setLayoutData(canvasGridData);
        canvas.setBackground(new Color(display, 0, 0, 0));

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
        int width = map.size();
        int height = map.get(0).size();

        int tileWidth = canvas.getBounds().width / width;
        int tileHeight = canvas.getBounds().height / height;

        Image image = new Image(display, canvas.getBounds().width + OFFSET, canvas.getBounds().height + OFFSET);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                drawTile(map.get(i).get(j), tileWidth, tileHeight, i * tileWidth, j * tileHeight, image);
            }
        }
        gc.drawImage(image, 0, 0);
    }

    private void drawTile(float value, int width, int height, int x, int y, Image image) 
    {
        float signedValue = 0.0f;
        if (value < 0) {
            signedValue = -value;
        } else {
            signedValue = value;
        }

        if (signedValue > 1.0f) {
            signedValue = 1.0f;
        }

        Color color = new Color(Display.getCurrent(), (int) (MAX_COLOR * signedValue) ,(int) (MAX_COLOR * signedValue),(int) (MAX_COLOR * signedValue));
        if (value > 0.3) {
            color = new Color(Display.getCurrent(), 0, 0, (int) (MAX_COLOR * signedValue));
        } else if (value < 0.5) {
            color = new Color(Display.getCurrent(), 0, (int) (MAX_COLOR * signedValue), 0);
        } else if (value < 0.7) {
            color = new Color(Display.getCurrent(), (int) (MAX_COLOR * signedValue), (int) (MAX_COLOR * signedValue), 0);
        } else {
            color = new Color(Display.getCurrent(), (int) (MAX_COLOR * signedValue), (int) (MAX_COLOR * signedValue), (int) (MAX_COLOR * signedValue));
        }
        GC imageGc = new GC(image);
        imageGc.setBackground(color);
        imageGc.fillRectangle(x, y, width, height);
        imageGc.dispose();
    }

}
