package ch.detisch.main;

import org.eclipse.swt.widgets.Display;

import ch.detisch.mapgeneration.Davinci;
import ch.detisch.mapgeneration.MapGenerator;

public class Main {

    private static int width = 10;
    private static int height = 10;


    public static void main(String[] args) {

        MapGenerator mapGenerator = new MapGenerator(width, height);
        mapGenerator.generateMap(1, 5, 0.01f,0.7f, 1);
        
        mapGenerator.getMap().forEach(row -> {
            System.out.println(row + "\n");
        });

        Davinci davinci = new Davinci();
        

        Display.getDefault().timerExec(100, new Runnable() {
            public void run() {
                davinci.drawMap(mapGenerator.getMap());
                Display.getDefault().timerExec(10, this);
            }
        });

        davinci.open();
    }
}