# ColorThief plugin for Flutter

A Flutter plugin that grabs the dominant color or a representative color palette from an image.

*Note*: This plugin is still under development. [Feedback welcome](https://github.com/m4tt72/ColorThiefPlugin/issues) and [Pull Requests](https://github.com/m4tt72/ColorThiefPlugin/pulls) are most welcome!

## Example

```dart
import 'package:colorthief/colorthief.dart' as ColorThiefProvider;
...

getColors() async {
    File image; // From image_picker or somewhere else
    List<ColorThiefProvider.Color> palette = await ColorThiefProvider.Colorthief.getPalette(image: image, size: 12);
    print(_palette);
}

```

### Thanks to

* Sven Woltmann - For the java version, available at <https://github.com/SvenWoltmann/color-thief-java/>
* Lokesh Dhakar - for the original Color Thief JavaScript version, available at <http://lokeshdhakar.com/projects/color-thief/>