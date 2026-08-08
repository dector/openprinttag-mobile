import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/widget_previews.dart';
import 'package:open_print_tag/open_print_tag.dart';

import '../preview_payload.dart';
import 'preview_wrapper.dart';

class MaterialHeader extends StatelessWidget {
  const MaterialHeader({super.key, required this.main});

  final OpenPrintTagMainData? main;

  @override
  Widget build(BuildContext context) {
    final color =
        _tagColor(main?.primaryColor) ?? Theme.of(context).colorScheme.primary;
    final foreground =
        ThemeData.estimateBrightnessForColor(color) == Brightness.dark
        ? Colors.white
        : Colors.black;

    return Card(
      clipBehavior: Clip.antiAlias,
      child: Container(
        decoration: BoxDecoration(
          gradient: LinearGradient(
            colors: <Color>[color, Color.lerp(color, Colors.black, 0.18)!],
          ),
        ),
        padding: const EdgeInsets.all(20),
        child: DefaultTextStyle.merge(
          style: TextStyle(color: foreground),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Text(
                main?.materialName ?? 'Unknown material',
                style: Theme.of(context).textTheme.headlineSmall?.copyWith(
                  color: foreground,
                  fontWeight: FontWeight.w700,
                ),
              ),
              const SizedBox(height: 8),
              Text(
                <String?>[
                      main?.brandName,
                      _materialTypeLabel(main?.materialType),
                      main?.materialClass?.name,
                    ]
                    .whereType<String>()
                    .where((value) => value.isNotEmpty)
                    .join(' • '),
              ),
              if (main?.primaryColorRal != null) ...<Widget>[
                const SizedBox(height: 12),
                Chip(
                  label: Text('RAL ${main!.primaryColorRal}'),
                  backgroundColor: Colors.white.withValues(alpha: 0.75),
                ),
              ],
            ],
          ),
        ),
      ),
    );
  }
}

@Preview(name: 'Material header', wrapper: previewWrapper)
Widget materialHeaderPreview() {
  return Padding(
    padding: const EdgeInsets.all(16),
    child: MaterialHeader(main: previewPayload.data.main),
  );
}

String? _materialTypeLabel(MaterialTypeEnum? type) {
  if (type == null) return null;
  return '${type.name} (${type.key})';
}

Color? _tagColor(Uint8List? bytes) {
  if (bytes == null || bytes.length < 3) return null;
  return Color.fromARGB(255, bytes[0], bytes[1], bytes[2]);
}
