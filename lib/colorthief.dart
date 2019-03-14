import 'dart:async';
import 'dart:io';
import 'dart:convert';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

const MethodChannel _channel = MethodChannel("plugins.m4tt72.com/colorthief");

// Returns a Color object that has the name, hex value, rgb value and hsl value of the color
Future<List<Color>> getPalette(
    {@required File image, @required int size}) async {
  final String result = await _channel.invokeMethod(
      "getPalette", <String, dynamic>{"src": image.path, "size": size});
  if (result == null) return [];
  return (json.decode(result) as List)
      .map((color) => Color.fromJson(color))
      .toList();
}

class Color {
  final String hex;
  final String name;
  final List<dynamic> rgb;
  final List<dynamic> hsl;

  Color({this.hex, this.name, this.rgb, this.hsl});

  factory Color.fromJson(Map<String, dynamic> json) {
    return Color(
        hex: json['hex'],
        name: json['name'],
        rgb: json['rgb'],
        hsl: json['hsl']);
  }
}
