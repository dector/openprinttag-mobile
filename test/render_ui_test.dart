import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';

import 'package:openprinttag_mobile/main.dart';
import 'package:openprinttag_mobile/widgets/tag_payload_view.dart';

Future<void> _loadFont(String family, String path) async {
  final bytes = await File(path).readAsBytes();
  final loader = FontLoader(family)
    ..addFont(Future<ByteData>.value(ByteData.sublistView(bytes)));
  await loader.load();
}

Widget _renderableUi(GlobalKey contentKey) {
  return MaterialApp(
    title: 'OpenPrintTag',
    debugShowCheckedModeBanner: false,
    theme: ThemeData(
      colorScheme: ColorScheme.fromSeed(seedColor: const Color(0xFFFF7A00)),
      useMaterial3: true,
    ),
    home: Material(
      child: Align(
        alignment: Alignment.topLeft,
        child: KeyedSubtree(
          key: contentKey,
          child: Column(
            mainAxisSize: MainAxisSize.min,
            crossAxisAlignment: CrossAxisAlignment.stretch,
            children: <Widget>[
              const Padding(
                padding: EdgeInsets.fromLTRB(16, 16, 16, 8),
                child: Text('OpenPrintTag', style: TextStyle(fontSize: 24)),
              ),
              TagPayloadView(
                payload: TagOverviewScreen.previewPayload,
                scrollable: false,
              ),
            ],
          ),
        ),
      ),
    ),
  );
}

void main() {
  setUpAll(() async {
    // Widget tests use the block-shaped Ahem font unless real fonts are loaded.
    await _loadFont(
      'Roboto',
      '/usr/share/fonts/google-noto-vf/NotoSans[wght].ttf',
    );
    await _loadFont(
      'MaterialIcons',
      '/home/dector/.local/share/mise/installs/flutter/3.41.9-stable/bin/cache/artifacts/material_fonts/MaterialIcons-Regular.otf',
    );
  });

  testWidgets('renders OpenPrintTag UI to a PNG file', (
    WidgetTester tester,
  ) async {
    final contentKey = GlobalKey();
    const width = 430.0;

    await tester.binding.setSurfaceSize(const Size(width, 2400));
    addTearDown(() => tester.binding.setSurfaceSize(null));

    await tester.pumpWidget(_renderableUi(contentKey));
    await tester.pump(const Duration(milliseconds: 100));

    final contentBox =
        contentKey.currentContext!.findRenderObject()! as RenderBox;
    final contentHeight = contentBox.size.height.ceilToDouble();

    await tester.binding.setSurfaceSize(Size(width, contentHeight));
    await tester.pumpWidget(_renderableUi(contentKey));
    await tester.pump(const Duration(milliseconds: 100));

    await expectLater(
      find.byType(MaterialApp),
      matchesGoldenFile('../build/openprinttag-ui.png'),
    );
  });
}
