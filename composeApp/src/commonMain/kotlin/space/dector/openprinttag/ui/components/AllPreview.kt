package space.dector.openprinttag.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.model.*
import space.dector.openprinttag.ui.Theme


@Composable
@Preview(showBackground = true)
private fun Preview_SectionCard() {
    Theme {
        SectionCard(title = "Sample Section") {
            Text("Section content goes here")
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview_MaterialClassificationSection() {
    Theme {
        MaterialClassificationSection(
            materialClass = MaterialClass.FFF,
            materialType = MaterialType.PLA,
            onMaterialClassChange = {},
            onMaterialTypeChange = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview_BasicInformationSection() {
    Theme {
        BasicInformationSection(
            brandName = "Acme Filaments",
            materialName = "Premium PLA",
            primaryColor = Color.fromHex("#FF5733"),
            density = 1.24f,
            gtin = "1234567890123",
            manufacturedDate = null,
            countryOfOrigin = null,
            onBrandNameChange = {},
            onMaterialNameChange = {},
            onPrimaryColorChange = {},
            onDensityChange = {},
            onGtinChange = {},
            onManufacturedDateChange = {},
            onCountryOfOriginChange = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview_MaterialPropertiesSection() {
    Theme {
        MaterialPropertiesSection(
            materialTags = setOf(MaterialTag.FOOD_SAFE, MaterialTag.RECYCLED),
            hasGreenguardCertification = true,
            hasFlameRetardantCertification = false,
            onMaterialTagsChange = {},
            onGreenguardChange = {},
            onFlameRetardantChange = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview_TemperatureSettingsSection() {
    Theme {
        TemperatureSettingsSection(
            printTemperature = TemperatureRange(190, 220),
            preheatTemperature = 160,
            bedTemperature = TemperatureRange(50, 60),
            onPrintTemperatureChange = {},
            onPreheatTemperatureChange = {},
            onBedTemperatureChange = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview_WeightInformationSection() {
    Theme {
        WeightInformationSection(
            weight = WeightInfo(
                nominal = 1000f,
                actual = 980f,
                emptyContainer = 50f,
            ),
            onWeightChange = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview_NfcUrlOptionsSection() {
    Theme {
        NfcUrlOptionsSection(
            nfcUrlOptions = NfcUrlOptions(
                includeUrlRecord = true,
                tagUrl = "https://openprinttag.org/tag/ABC123",
            ),
            onNfcUrlOptionsChange = {},
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview_AllComponents() {
    Theme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            MaterialClassificationSection(
                materialClass = MaterialClass.FFF,
                materialType = MaterialType.PLA,
                onMaterialClassChange = {},
                onMaterialTypeChange = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            BasicInformationSection(
                brandName = "Acme Filaments",
                materialName = "Premium PLA",
                primaryColor = Color.fromHex("#FF5733"),
                density = 1.24f,
                gtin = "1234567890123",
                manufacturedDate = null,
                countryOfOrigin = null,
                onBrandNameChange = {},
                onMaterialNameChange = {},
                onPrimaryColorChange = {},
                onDensityChange = {},
                onGtinChange = {},
                onManufacturedDateChange = {},
                onCountryOfOriginChange = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            MaterialPropertiesSection(
                materialTags = setOf(MaterialTag.FOOD_SAFE, MaterialTag.RECYCLED),
                hasGreenguardCertification = true,
                hasFlameRetardantCertification = false,
                onMaterialTagsChange = {},
                onGreenguardChange = {},
                onFlameRetardantChange = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            TemperatureSettingsSection(
                printTemperature = TemperatureRange(190, 220),
                preheatTemperature = 160,
                bedTemperature = TemperatureRange(50, 60),
                onPrintTemperatureChange = {},
                onPreheatTemperatureChange = {},
                onBedTemperatureChange = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            WeightInformationSection(
                weight = WeightInfo(
                    nominal = 1000f,
                    actual = 980f,
                    emptyContainer = 50f,
                ),
                onWeightChange = {},
            )

            Spacer(modifier = Modifier.height(16.dp))

            NfcUrlOptionsSection(
                nfcUrlOptions = NfcUrlOptions(
                    includeUrlRecord = true,
                    tagUrl = "https://openprinttag.org/tag/ABC123",
                ),
                onNfcUrlOptionsChange = {},
            )
        }
    }
}
