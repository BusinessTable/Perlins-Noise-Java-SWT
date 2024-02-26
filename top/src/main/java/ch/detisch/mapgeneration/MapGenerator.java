package ch.detisch.mapgeneration;

import java.util.ArrayList;

public class MapGenerator {
    
    private ArrayList<ArrayList<Float>> map;

    private int width;
    private int height;

    public MapGenerator(int width, int height) {
        this.width = width;
        this.height = height;
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
