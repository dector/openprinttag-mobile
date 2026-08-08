import 'package:flutter/material.dart';
import 'package:flutter/widget_previews.dart';

import 'info_row.dart';
import 'preview_wrapper.dart';

class InfoTile extends StatelessWidget {
  const InfoTile({super.key, required this.row});

  final InfoRow row;

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 6),
      child: Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Expanded(
            flex: 2,
            child: Text(
              row.label,
              style: Theme.of(context).textTheme.bodyMedium?.copyWith(
                color: Theme.of(context).colorScheme.onSurfaceVariant,
              ),
            ),
          ),
          const SizedBox(width: 12),
          Expanded(
            flex: 3,
            child: SelectableText(row.value!, textAlign: TextAlign.end),
          ),
        ],
      ),
    );
  }
}

@Preview(name: 'Info tile', wrapper: previewWrapper)
Widget infoTilePreview() {
  return const Padding(
    padding: EdgeInsets.all(16),
    child: InfoTile(row: InfoRow('Nozzle temperature', '205–225 °C')),
  );
}
