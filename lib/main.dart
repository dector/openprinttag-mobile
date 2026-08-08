import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:open_print_tag/open_print_tag.dart';

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
  const TagOverviewScreen({super.key});

  static final OpenPrintTagPayload previewPayload = OpenPrintTagPayload(
    data: OpenPrintTagData(
      main: OpenPrintTagMainData(
        materialClass: MaterialClassEnum.FFF,
        materialType: MaterialTypeEnum.PLA,
        materialName: 'Galaxy Orange PLA',
        materialAbbreviation: 'PLA',
        brandName: 'OpenPrintTag',
        nominalNettoFullWeight: 1000,
        actualNettoFullWeight: 932,
        nominalFullLength: 330,
        actualFullLength: 307,
        filamentDiameter: 1.75,
        minNozzleDiameter: 0.4,
        minPrintTemperature: 205,
        maxPrintTemperature: 225,
        minBedTemperature: 55,
        maxBedTemperature: 65,
        dryingTemperature: 45,
        dryingTime: 6,
        primaryColor: Uint8List.fromList(<int>[255, 122, 0]),
        primaryColorRal: '2008',
        tags: <TagsEnum>[TagsEnum.glitter],
      ),
      aux: OpenPrintTagAuxData(
        consumedWeight: 68,
        storageLocation: 'Dry box A2',
        purchasePrice: 24.99,
        purchaseCurrency: 'EUR',
      ),
      meta: OpenPrintTagMetaData(
        mainRegionOffset: 0,
        mainRegionSize: 256,
        auxRegionOffset: 256,
        auxRegionSize: 64,
      ),
    ),
  );

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('OpenPrintTag')),
      body: SafeArea(child: TagPayloadView(payload: previewPayload)),
    );
  }
}

class TagPayloadView extends StatelessWidget {
  const TagPayloadView({
    super.key,
    required this.payload,
    this.scrollable = true,
  });

  final OpenPrintTagPayload payload;
  final bool scrollable;

  @override
  Widget build(BuildContext context) {
    final main = payload.data.main;
    final aux = payload.data.aux;
    final meta = payload.data.meta;

    if (main == null && aux == null && meta == null) {
      return const _EmptyTagView();
    }

    final children = <Widget>[
      _MaterialHeader(main: main),
      const SizedBox(height: 16),
      _InfoSection(
        title: 'Material',
        icon: Icons.category_outlined,
        rows: <_InfoRow>[
          _InfoRow('Name', main?.materialName),
          _InfoRow('Brand', main?.brandName),
          _InfoRow('Class', main?.materialClass?.name),
          _InfoRow('Type', _materialTypeLabel(main?.materialType)),
          _InfoRow('Abbreviation', main?.materialAbbreviation),
          _InfoRow('Density', _withUnit(main?.density, 'g/cm³')),
          _InfoRow('Certifications', _enumList(main?.certifications)),
          _InfoRow('Tags', _enumList(main?.tags)),
        ],
      ),
      _InfoSection(
        title: 'Printing profile',
        icon: Icons.thermostat_outlined,
        rows: <_InfoRow>[
          _InfoRow(
            'Nozzle temperature',
            _range(main?.minPrintTemperature, main?.maxPrintTemperature, '°C'),
          ),
          _InfoRow(
            'Bed temperature',
            _range(main?.minBedTemperature, main?.maxBedTemperature, '°C'),
          ),
          _InfoRow(
            'Chamber temperature',
            _range(
              main?.minChamberTemperature,
              main?.maxChamberTemperature,
              '°C',
            ),
          ),
          _InfoRow(
            'Preheat temperature',
            _withUnit(main?.preheatTemperature, '°C'),
          ),
          _InfoRow(
            'Drying',
            _drying(main?.dryingTemperature, main?.dryingTime),
          ),
          _InfoRow(
            'Filament diameter',
            _withUnit(main?.filamentDiameter, 'mm'),
          ),
          _InfoRow('Minimum nozzle', _withUnit(main?.minNozzleDiameter, 'mm')),
        ],
      ),
      _InfoSection(
        title: 'Spool and package',
        icon: Icons.inventory_2_outlined,
        rows: <_InfoRow>[
          _InfoRow(
            'Nominal net weight',
            _withUnit(main?.nominalNettoFullWeight, 'g'),
          ),
          _InfoRow(
            'Actual net weight',
            _withUnit(main?.actualNettoFullWeight, 'g'),
          ),
          _InfoRow('Consumed weight', _withUnit(aux?.consumedWeight, 'g')),
          _InfoRow('Nominal length', _withUnit(main?.nominalFullLength, 'm')),
          _InfoRow('Actual length', _withUnit(main?.actualFullLength, 'm')),
          _InfoRow(
            'Empty container weight',
            _withUnit(main?.emptyContainerWeight, 'g'),
          ),
          _InfoRow('Container width', _withUnit(main?.containerWidth, 'mm')),
          _InfoRow(
            'Outer diameter',
            _withUnit(main?.containerOuterDiameter, 'mm'),
          ),
          _InfoRow(
            'Inner diameter',
            _withUnit(main?.containerInnerDiameter, 'mm'),
          ),
          _InfoRow(
            'Hole diameter',
            _withUnit(main?.containerHoleDiameter, 'mm'),
          ),
        ],
      ),
      _InfoSection(
        title: 'Identification',
        icon: Icons.qr_code_2_outlined,
        rows: <_InfoRow>[
          _InfoRow('GTIN', main?.gtin?.toString()),
          _InfoRow('Instance UUID', main?.instanceUuid),
          _InfoRow('Package UUID', main?.packageUuid),
          _InfoRow('Material UUID', main?.materialUuid),
          _InfoRow('Brand UUID', main?.brandUuid),
          _InfoRow('Instance ID', main?.brandSpecificInstanceId),
          _InfoRow('Package ID', main?.brandSpecificPackageId),
          _InfoRow('Material ID', main?.brandSpecificMaterialId),
        ],
      ),
      _InfoSection(
        title: 'Lifecycle and storage',
        icon: Icons.event_note_outlined,
        rows: <_InfoRow>[
          _InfoRow('Manufactured', _dateOrRaw(main?.manufacturedDate)),
          _InfoRow('Expires', _dateOrRaw(main?.expirationDate)),
          _InfoRow('Origin', main?.countryOfOrigin),
          _InfoRow('Storage location', aux?.storageLocation),
          _InfoRow('Workgroup', aux?.workgroup),
          _InfoRow('Last stir time', _dateOrRaw(aux?.lastStirTime)),
          _InfoRow('Purchase time', _dateOrRaw(aux?.purchaseTime)),
          _InfoRow(
            'Purchase price',
            _price(aux?.purchasePrice, aux?.purchaseCurrency),
          ),
        ],
      ),
      _InfoSection(
        title: 'Tag layout',
        icon: Icons.nfc_outlined,
        rows: <_InfoRow>[
          _InfoRow(
            'Main region offset',
            _withUnit(meta?.mainRegionOffset, 'bytes'),
          ),
          _InfoRow(
            'Main region size',
            _withUnit(meta?.mainRegionSize, 'bytes'),
          ),
          _InfoRow(
            'Aux region offset',
            _withUnit(meta?.auxRegionOffset, 'bytes'),
          ),
          _InfoRow('Aux region size', _withUnit(meta?.auxRegionSize, 'bytes')),
          _InfoRow(
            'Unknown fields',
            _unknownFieldsSummary(payload.unknownFields),
          ),
        ],
      ),
    ];

    if (!scrollable) {
      return Padding(
        padding: const EdgeInsets.all(16),
        child: Column(
          mainAxisSize: MainAxisSize.min,
          crossAxisAlignment: CrossAxisAlignment.stretch,
          children: children,
        ),
      );
    }

    return ListView(padding: const EdgeInsets.all(16), children: children);
  }
}

class _MaterialHeader extends StatelessWidget {
  const _MaterialHeader({required this.main});

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

class _InfoSection extends StatelessWidget {
  const _InfoSection({
    required this.title,
    required this.icon,
    required this.rows,
  });

  final String title;
  final IconData icon;
  final List<_InfoRow> rows;

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
            for (final row in visibleRows) _InfoTile(row: row),
          ],
        ),
      ),
    );
  }
}

class _InfoTile extends StatelessWidget {
  const _InfoTile({required this.row});

  final _InfoRow row;

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

class _EmptyTagView extends StatelessWidget {
  const _EmptyTagView();

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

class _InfoRow {
  const _InfoRow(this.label, this.value);

  final String label;
  final String? value;
}

String? _materialTypeLabel(MaterialTypeEnum? type) {
  if (type == null) return null;
  return '${type.name} (${type.key})';
}

String? _enumList(List<Enum>? values) {
  if (values == null || values.isEmpty) return null;
  return values.map((value) => value.name).join(', ');
}

String? _withUnit(num? value, String unit) {
  if (value == null) return null;
  return '$value $unit';
}

String? _range(num? min, num? max, String unit) {
  if (min == null && max == null) return null;
  if (min != null && max != null) return '$min–$max $unit';
  return _withUnit(min ?? max, unit);
}

String? _drying(num? temperature, num? hours) {
  if (temperature == null && hours == null) return null;
  final parts = <String>[
    if (temperature != null) '$temperature °C',
    if (hours != null) '$hours h',
  ];
  return parts.join(' for ');
}

String? _price(num? value, String? currency) {
  if (value == null) return null;
  return currency == null || currency.isEmpty ? '$value' : '$value $currency';
}

String? _dateOrRaw(int? value) {
  if (value == null) return null;
  if (value > 946684800 && value < 4102444800) {
    return DateTime.fromMillisecondsSinceEpoch(
      value * 1000,
      isUtc: true,
    ).toLocal().toString().split('.').first;
  }
  return value.toString();
}

String? _unknownFieldsSummary(UnknownFields? fields) {
  if (fields == null) return null;
  final count =
      (fields.meta?.length ?? 0) +
      (fields.main?.length ?? 0) +
      (fields.aux?.length ?? 0);
  return count == 0 ? null : '$count fields';
}

Color? _tagColor(Uint8List? bytes) {
  if (bytes == null || bytes.length < 3) return null;
  return Color.fromARGB(255, bytes[0], bytes[1], bytes[2]);
}
