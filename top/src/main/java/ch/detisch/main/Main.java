package ch.detisch.main;

import org.eclipse.swt.widgets.Display;

import ch.detisch.mapgeneration.Davinci;
import ch.detisch.mapgeneration.MapGenerator;

public class Main {

    private final static int MAP_SIZE = 1000;

    public static void main(String[] args) {

        MapGenerator mapGenerator = new MapGenerator(MAP_SIZE);
        mapGenerator.generateMap(1, 5, 0.01f,0.7f, 1);

        mapGenerator.smoothMap();
        mapGenerator.smoothMap();
        mapGenerator.smoothMap();
        mapGenerator.smoothMap();

        // mapGenerator.getMap().forEach(row -> {
        // System.out.println(row + "\n");
        // });

        Davinci davinci = new Davinci(1000, 50, MAP_SIZE);
        

        Display.getDefault().timerExec(100, new Runnable() {
            public void run() {
                davinci.drawMap(mapGenerator.getMap());
                Display.getDefault().timerExec(10, this);
            }
        });

        davinci.open();
    }
}