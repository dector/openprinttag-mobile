import 'package:flutter/material.dart';
import 'package:flutter/widget_previews.dart';

import 'preview_wrapper.dart';

class EmptyTagView extends StatelessWidget {
  const EmptyTagView({super.key});

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(32),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          children: <Widget>[
            Icon(
              Icons.nfc,
              size: 72,
              color: Theme.of(context).colorScheme.primary,
            ),
            const SizedBox(height: 16),
            Text(
              'No OpenPrintTag data',
              style: Theme.of(context).textTheme.headlineSmall,
            ),
            const SizedBox(height: 8),
            const Text(
              'Scan or decode an OpenPrintTag NFC payload to display material, spool, and print settings here.',
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
    );
  }
}

@Preview(name: 'Empty tag view', wrapper: previewWrapper)
Widget emptyTagViewPreview() => const EmptyTagView();
