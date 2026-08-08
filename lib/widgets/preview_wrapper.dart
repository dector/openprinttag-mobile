import 'package:flutter/material.dart';

Widget previewWrapper(Widget child) {
  return MaterialApp(
    debugShowCheckedModeBanner: false,
    theme: ThemeData(
      colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFFFF7A00)),
      useMaterial3: true,
    ),
    home: Scaffold(body: SafeArea(child: child)),
  );
}
