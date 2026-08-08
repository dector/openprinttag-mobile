import 'package:flutter_test/flutter_test.dart';

import 'package:openprinttag_mobile/main.dart';

void main() {
  testWidgets('shows OpenPrintTag material details', (
    WidgetTester tester,
  ) async {
    await tester.pumpWidget(const OpenPrintTagApp());

    expect(find.text('OpenPrintTag'), findsWidgets);
    expect(find.text('Galaxy Orange PLA'), findsWidgets);
    expect(find.text('Printing profile'), findsOneWidget);
    expect(find.text('205–225 °C'), findsOneWidget);
    expect(find.text('Material'), findsOneWidget);
  });
}
