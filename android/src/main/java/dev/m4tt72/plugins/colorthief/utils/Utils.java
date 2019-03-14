package dev.m4tt72.plugins.colorthief.utils;

public class Utils {

    public static int[] hexToRgb(String color) {
        return new int[] {
                Integer.parseInt(String.format("%s", color.substring(1, 3)), 16),
                Integer.parseInt(String.format("%s", color.substring(3, 5)), 16),
                Integer.parseInt(String.format("%s", color.substring(5, 7)), 16)
        };
    }

    public static int[] hexToHsl(String hex) {
        return rgbToHsl(hexToRgb(hex));
    }

    public static int[] rgbToHsl(int[] rgb) {
        double r = ((double) rgb[0])/255,
                g = ((double) rgb[1])/255,
                b = ((double) rgb[2])/255;

        double min, max, delta, h, s, l;
        min = Math.min(r, Math.min(g, b));
        max = Math.max(r, Math.max(g, b));
        delta = max - min;
        l = (min + max) / 2;
        s = 0;
        if(l > 0 && l < 2) {
            s = delta / (l < 0.5 ? (2 * l) : (2 - 2 * l));
        }

        h = 0;
        if (delta > 0) {
            if (max == r && max != g) h += (g - b) / delta;
            if (max == g && max != b) h += (2 + (b - r) / delta);
            if (max == b && max != r) h += (4 + (r - g) / delta);
            h /= 6;
        }
        return new int[] {
                (int) Math.round(h * 255),
                (int) Math.round(s * 255),
                (int) Math.round(l * 255)
        };
    }
}
