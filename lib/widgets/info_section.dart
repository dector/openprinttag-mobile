import 'package:flutter/material.dart';
import 'package:flutter/widget_previews.dart';

import 'info_row.dart';
import 'info_tile.dart';
import 'preview_wrapper.dart';

class InfoSection extends StatelessWidget {
  const InfoSection({
    super.key,
    required this.title,
    required this.icon,
    required this.rows,
  });

  final String title;
  final IconData icon;
  final List<InfoRow> rows;

  @override
  Widget build(BuildContext context) {
    final visibleRows = rows
        .where((row) => row.value != null && row.value!.isNotEmpty)
        .toList();
    if (visibleRows.isEmpty) {
      return const SizedBox.shrink();
    }

    return Card(
      margin: const EdgeInsets.only(bottom: 12),
      child: Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Row(
              children: <Widget>[
                Icon(icon),
                const SizedBox(width: 8),
                Text(title, style: Theme.of(context).textTheme.titleMedium),
              ],
            ),
            const SizedBox(height: 12),
            for (final row in visibleRows) InfoTile(row: row),
          ],
        ),
      ),
    );
  }
}

@Preview(name: 'Info section', wrapper: previewWrapper)
Widget infoSectionPreview() {
  return const Padding(
    padding: EdgeInsets.all(16),
    child: InfoSection(
      title: 'Printing profile',
      icon: Icons.thermostat_outlined,
      rows: <InfoRow>[
        InfoRow('Nozzle temperature', '205–225 °C'),
        InfoRow('Bed temperature', '55–65 °C'),
        InfoRow('Filament diameter', '1.75 mm'),
      ],
    ),
  );
}
