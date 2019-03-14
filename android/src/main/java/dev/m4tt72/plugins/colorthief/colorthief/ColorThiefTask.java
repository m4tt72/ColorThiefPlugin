package dev.m4tt72.plugins.colorthief.colorthief;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.androidpit.colorthief.ColorThief;
import dev.m4tt72.plugins.colorthief.models.Color;
import io.flutter.plugin.common.MethodChannel;

public class ColorThiefTask extends AsyncTask<Integer, Void, List<Color>> {

    private Context context;
    private Bitmap image;
    private MethodChannel.Result result;

    public ColorThiefTask(Context context, Bitmap image, MethodChannel.Result result) {
        this.context = context;
        this.image = image;
        this.result = result;
    }

    @Override
    protected List<Color> doInBackground(Integer... params) {
        Integer size = Objects.requireNonNull(params[0]);
        List<Color> palette = new ArrayList<>();
        int[][] colors = ColorThief.getPalette(image, size);
        for(int[] color: Objects.requireNonNull(colors)) {
            palette.add(new Color(context, String.format("#%02x%02x%02x", color[0], color[1], color[2])));
        }
        return palette;
    }

    @Override
    protected void onPostExecute(List<Color> palette) {
        Gson gson = new Gson();
        result.success(gson.toJson(palette));
    }
}
