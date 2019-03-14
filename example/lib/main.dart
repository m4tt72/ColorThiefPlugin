import 'package:flutter/material.dart';
import 'package:image_picker/image_picker.dart';
import 'package:flutter/foundation.dart';
import 'dart:io';
import 'package:colorthief/colorthief.dart' as ColorThiefProvider;

void main() {
  runApp(App());
}

class App extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: "Color Thief Example",
      home: HomePage(),
    );
  }
}

class HomePage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return _HomePageState();
  }
}

class _HomePageState extends State<HomePage> {

  Future <void> _chooseImageFromGallery() async {
    try {
      var image = await ImagePicker.pickImage(source: ImageSource.gallery);
      if (image != null) {
        await Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => PalettePage(
              image: image,
            ))
        );
      }
    } catch (error) {
      print(error);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Color Thief Example"),
      ),
      body: Center(
        child: RaisedButton(
          child: Text("Choose an image"),
          onPressed: _chooseImageFromGallery,
        ),
      ),
    );
  }
}

class PalettePage extends StatefulWidget {
  
  PalettePage({
    @required this.image
  });

  final File image;
  
  @override
  State<StatefulWidget> createState() {
    return _PalettePageState();
  }
}

class _PalettePageState extends State<PalettePage> {

  List<ColorThiefProvider.Color> _palette = [];
  bool _loading = true;

  @override
  void initState() {
    super.initState();
    _getColors();
  }

  _getColors() async {
    _palette = await ColorThiefProvider.getPalette(image: widget.image, size: 12);
    print(_palette);
    setState(() {
      _loading = false;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Palette Page"),
      ),
      body: Stack(
        children: <Widget>[
          AnimatedOpacity(
            opacity: _loading ? 1.0 : 0.0,
            duration: Duration(milliseconds: 250),
            child: Center(
              child: CircularProgressIndicator(),
            ),
          ),
          AnimatedOpacity(
            opacity: _loading ? 0.0 : 1.0,
            duration: Duration(milliseconds: 250),
            child: ListView.builder(
              itemCount: _palette.length,
              itemBuilder: (BuildContext context, int index) => Padding(
                padding: EdgeInsets.all(20),
                child: Container(
                  child: Text("hex: ${_palette[index].hex} Name:${_palette[index].name}"),
                ),
              ),
            ),
          )
        ],
      ),
    );
  }
}