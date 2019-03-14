package dev.m4tt72.plugins.colorthief.ntc;

import android.content.Context;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import dev.m4tt72.plugins.colorthief.R;
import dev.m4tt72.plugins.colorthief.utils.Utils;

public class NameThatColor {

    private static NameThatColor instance;

    private Context context;

    private List<ArrayList<String>> names;

    private NameThatColor(Context context) {
        this.context = context;
        names = parseNames();
    }

    public static NameThatColor getInstance(Context context) {
        if(instance == null) instance = new NameThatColor(context);
        return instance;
    }
    /**
     * Get the color name based on its hex value
     * @param color Color in hex eg: #12ff1f
     * @return color name
     */
    public String getName(String color) {
        if(color.length() != 7)
            return "Invalid Color " + color;

        int[] rgb = Utils.hexToRgb(color);
        int[] hsl = Utils.rgbToHsl(rgb);
        double df = -1.0;
        int cl = -1;
        for(int i = 0; i < names.size(); i++) {
            if(names.get(i).get(0).equals(color)) return names.get(i).get(1);
            double ndf1 = Math.pow(rgb[0] - Utils.hexToRgb(names.get(i).get(0))[0], 2) +
                            Math.pow(rgb[1] - Utils.hexToRgb(names.get(i).get(0))[1], 2) +
                            Math.pow(rgb[2] - Utils.hexToRgb(names.get(i).get(0))[2], 2);
            double ndf2 = Math.pow(hsl[0] - Utils.hexToHsl(names.get(i).get(0))[0], 2) +
                    Math.pow(hsl[1] - Utils.hexToHsl(names.get(i).get(0))[1], 2) +
                    Math.pow(hsl[2] - Utils.hexToHsl(names.get(i).get(0))[2], 2);
            double ndf = ndf1 + ndf2 * 2;
            if(df < 0 || df > ndf) {
                df = ndf;
                cl = i;
            }
        }
        return (cl < 0 ? "Invalid Color " + color : names.get(cl).get(1));
    }

    private ArrayList<ArrayList<String>> parseNames() {
        try (InputStream is = context.getApplicationContext().getResources().openRawResource(R.raw.names)) {
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            Reader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            String jsonString = writer.toString();
            Gson gson = new Gson();
            return gson.fromJson(jsonString, ArrayList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
