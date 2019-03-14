package dev.m4tt72.plugins.colorthief;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import de.androidpit.colorthief.ColorThief;
import dev.m4tt72.plugins.colorthief.colorthief.ColorThiefTask;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** ColorthiefPlugin */
public class ColorthiefPlugin implements MethodCallHandler {

  private Activity activity;

  ColorthiefPlugin(Activity activity) {
    this.activity = activity;
  }

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "plugins.m4tt72.com/colorthief");
    channel.setMethodCallHandler(new ColorthiefPlugin(registrar.activity()));
  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    if (call.method.equals("getPalette")) {
      String path = call.argument("src");
      Integer size = call.argument("size");
      if(path == null || size == null) {
        result.error("Unavailable", "Missing Params", null);
        return;
      }
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      Bitmap image = BitmapFactory.decodeFile(path, options);
      ColorThiefTask colorThief = new ColorThiefTask(activity, image, result);
      colorThief.execute(size);
    } else {
      result.notImplemented();
    }
  }
}
