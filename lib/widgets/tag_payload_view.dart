import 'package:flutter/material.dart';
import 'package:flutter/widget_previews.dart';
import 'package:open_print_tag/open_print_tag.dart';

import '../preview_payload.dart';
import 'empty_tag_view.dart';
import 'info_row.dart';
import 'info_section.dart';
import 'material_header.dart';
import 'preview_wrapper.dart';

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
      return const EmptyTagView();
    }

    final children = <Widget>[
      MaterialHeader(main: main),
      const SizedBox(height: 16),
      InfoSection(
        title: 'Material',
        icon: Icons.category_outlined,
        rows: <InfoRow>[
          InfoRow('Name', main?.materialName),
          InfoRow('Brand', main?.brandName),
          InfoRow('Class', main?.materialClass?.name),
          InfoRow('Type', _materialTypeLabel(main?.materialType)),
          InfoRow('Abbreviation', main?.materialAbbreviation),
          InfoRow('Density', _withUnit(main?.density, 'g/cm³')),
          InfoRow('Certifications', _enumList(main?.certifications)),
          InfoRow('Tags', _enumList(main?.tags)),
        ],
      ),
      InfoSection(
        title: 'Printing profile',
        icon: Icons.thermostat_outlined,
        rows: <InfoRow>[
          InfoRow(
            'Nozzle temperature',
            _range(main?.minPrintTemperature, main?.maxPrintTemperature, '°C'),
          ),
          InfoRow(
            'Bed temperature',
            _range(main?.minBedTemperature, main?.maxBedTemperature, '°C'),
          ),
          InfoRow(
            'Chamber temperature',
            _range(
              main?.minChamberTemperature,
              main?.maxChamberTemperature,
              '°C',
            ),
          ),
          InfoRow(
            'Preheat temperature',
            _withUnit(main?.preheatTemperature, '°C'),
          ),
          InfoRow('Drying', _drying(main?.dryingTemperature, main?.dryingTime)),
          InfoRow('Filament diameter', _withUnit(main?.filamentDiameter, 'mm')),
          InfoRow('Minimum nozzle', _withUnit(main?.minNozzleDiameter, 'mm')),
        ],
      ),
      InfoSection(
        title: 'Spool and package',
        icon: Icons.inventory_2_outlined,
        rows: <InfoRow>[
          InfoRow(
            'Nominal net weight',
            _withUnit(main?.nominalNettoFullWeight, 'g'),
          ),
          InfoRow(
            'Actual net weight',
            _withUnit(main?.actualNettoFullWeight, 'g'),
          ),
          InfoRow('Consumed weight', _withUnit(aux?.consumedWeight, 'g')),
          InfoRow('Nominal length', _withUnit(main?.nominalFullLength, 'm')),
          InfoRow('Actual length', _withUnit(main?.actualFullLength, 'm')),
          InfoRow(
            'Empty container weight',
            _withUnit(main?.emptyContainerWeight, 'g'),
          ),
          InfoRow('Container width', _withUnit(main?.containerWidth, 'mm')),
          InfoRow(
            'Outer diameter',
            _withUnit(main?.containerOuterDiameter, 'mm'),
          ),
          InfoRow(
            'Inner diameter',
            _withUnit(main?.containerInnerDiameter, 'mm'),
          ),
          InfoRow(
            'Hole diameter',
            _withUnit(main?.containerHoleDiameter, 'mm'),
          ),
        ],
      ),
      InfoSection(
        title: 'Identification',
        icon: Icons.qr_code_2_outlined,
        rows: <InfoRow>[
          InfoRow('GTIN', main?.gtin?.toString()),
          InfoRow('Instance UUID', main?.instanceUuid),
          InfoRow('Package UUID', main?.packageUuid),
          InfoRow('Material UUID', main?.materialUuid),
          InfoRow('Brand UUID', main?.brandUuid),
          InfoRow('Instance ID', main?.brandSpecificInstanceId),
          InfoRow('Package ID', main?.brandSpecificPackageId),
          InfoRow('Material ID', main?.brandSpecificMaterialId),
        ],
      ),
      InfoSection(
        title: 'Lifecycle and storage',
        icon: Icons.event_note_outlined,
        rows: <InfoRow>[
          InfoRow('Manufactured', _dateOrRaw(main?.manufacturedDate)),
          InfoRow('Expires', _dateOrRaw(main?.expirationDate)),
          InfoRow('Origin', main?.countryOfOrigin),
          InfoRow('Storage location', aux?.storageLocation),
          InfoRow('Workgroup', aux?.workgroup),
          InfoRow('Last stir time', _dateOrRaw(aux?.lastStirTime)),
          InfoRow('Purchase time', _dateOrRaw(aux?.purchaseTime)),
          InfoRow(
            'Purchase price',
            _price(aux?.purchasePrice, aux?.purchaseCurrency),
          ),
        ],
      ),
      InfoSection(
        title: 'Tag layout',
        icon: Icons.nfc_outlined,
        rows: <InfoRow>[
          InfoRow(
            'Main region offset',
            _withUnit(meta?.mainRegionOffset, 'bytes'),
          ),
          InfoRow('Main region size', _withUnit(meta?.mainRegionSize, 'bytes')),
          InfoRow(
            'Aux region offset',
            _withUnit(meta?.auxRegionOffset, 'bytes'),
          ),
          InfoRow('Aux region size', _withUnit(meta?.auxRegionSize, 'bytes')),
          InfoRow(
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

@Preview(name: 'Tag payload view', wrapper: previewWrapper)
Widget tagPayloadViewPreview() => TagPayloadView(payload: previewPayload);

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
