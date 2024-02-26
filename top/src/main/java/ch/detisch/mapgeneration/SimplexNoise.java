package ch.detisch.mapgeneration;

public class SimplexNoise {

    public float noise(float x, float y) {
        int n = 0;
        n += 1619 * (int) x;
        n += 31337 * (int) y;
        n += 6971 * (int) x;
        n += 1013 * (int) y;
        n = (n << 13) ^ n;
        return (1.0f - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0f);
    }
    
}
