import 'package:flutter/material.dart';
import 'package:flutter/widget_previews.dart';
import 'package:open_print_tag/open_print_tag.dart';

import 'preview_payload.dart' as previews;
import 'widgets/tag_payload_view.dart';

void main() {
  runApp(const OpenPrintTagApp());
}

class OpenPrintTagApp extends StatelessWidget {
  const OpenPrintTagApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'OpenPrintTag',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFFFF7A00)),
        useMaterial3: true,
      ),
      home: const TagOverviewScreen(),
    );
  }
}

class TagOverviewScreen extends StatelessWidget {
  @Preview(name: 'Tag overview')
  const TagOverviewScreen({super.key});

  static final OpenPrintTagPayload previewPayload = previews.previewPayload;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('OpenPrintTag')),
      body: SafeArea(child: TagPayloadView(payload: previewPayload)),
    );
  }
}
