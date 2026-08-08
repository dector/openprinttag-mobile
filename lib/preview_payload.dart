import 'dart:typed_data';

import 'package:open_print_tag/open_print_tag.dart';

final OpenPrintTagPayload previewPayload = OpenPrintTagPayload(
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
