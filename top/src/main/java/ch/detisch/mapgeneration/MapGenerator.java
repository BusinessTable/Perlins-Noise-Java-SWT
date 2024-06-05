package ch.detisch.mapgeneration;

import java.util.ArrayList;

public class MapGenerator {
    
    private ArrayList<ArrayList<Float>> map;

    private int width;
    private int height;

    public MapGenerator(int mapSize) {
        this.width = mapSize;
        this.height = mapSize;
    }
    
    // create Perlins noise map
    public void generateMap(int scale, int octaves, float persistance, float lacunarity, int seed) {
        // create a new map
        map = new ArrayList<ArrayList<Float>>();

        // create a new SimplexNoise object
        SimplexNoise noise = new SimplexNoise();

        // loop through the width
        for (int x = 0; x < width; x++) {
            // create a new row
            ArrayList<Float> row = new ArrayList<Float>();

            // loop through the height
            for (int y = 0; y < height; y++) {
                // create a new value
                float value = 0;

                // loop through the octaves
                for (int i = 0; i < octaves; i++) {
                    // calculate the frequency and the amplitude
                    float frequency = (float) Math.pow(2, i);
                    float amplitude = (float) Math.pow(persistance, i);

                    // calculate the sample coordinates
                    float sampleX = x / scale * frequency;
                    float sampleY = y / scale * frequency;

                    // add the noise to the value
                    value += noise.noise(sampleX, sampleY) * amplitude;
                }

                // add the value to the row
                row.add(value);
            }

            // add the row to the map
            map.add(row);
        }
    }

    // smooth map values to be closer to eachother to make the map look more natural
    public void smoothMap() {
        // loop through the width
        for (int x = 0; x < width; x++) {
            // loop through the height
            for (int y = 0; y < height; y++) {
                // get the value
                float value = map.get(x).get(y);

                // get the average value
                float average = 0;
                int count = 0;

                // loop through the surrounding tiles
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        // check if the tile is inside the map
                        if (x + i >= 0 && x + i < width && y + j >= 0 && y + j < height) {
                            // add the value to the average
                            average += map.get(x + i).get(y + j);
                            count++;
                        }
                    }
                }

                // calculate the average
                average /= count;

                // set the value to the average
                map.get(x).set(y, average);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public ArrayList<ArrayList<Float>> getMap() {
        return map;
    }

    public void setMap(ArrayList<ArrayList<Float>> map) {
        this.map = map;
    }
}
