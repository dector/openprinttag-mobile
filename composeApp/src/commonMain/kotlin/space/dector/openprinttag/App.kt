package space.dector.openprinttag

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.model.TagState
import space.dector.openprinttag.ui.Theme
import space.dector.openprinttag.ui.components.*
import space.dector.openprinttag.TagInfoIntent as Intent


@Composable
fun App() {
    Theme {
        TagInfoScreen()
    }
}

@Composable
fun TagInfoScreen(
    vm: TagInfoViewModel = viewModel(),
) {
    val state by vm.state.collectAsState()

    TagInfoScreenUi(state, vm::onIntent)
}

@Composable
fun TagInfoScreenUi(
    state: TagState,
    onIntent: (Intent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        Text(
            text = "OpenPrintTag Configuration",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        // Material Classification Section
        MaterialClassificationSection(
            materialClass = state.materialClass,
            materialType = state.materialType,
            onMaterialClassChange = { onIntent(Intent.MaterialClassChange(it)) },
            onMaterialTypeChange = { onIntent(Intent.MaterialTypeChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Basic Information Section
        BasicInformationSection(
            brandName = state.brandName,
            materialName = state.materialName,
            primaryColor = state.primaryColor,
            density = state.density,
            gtin = state.gtin,
            manufacturedDate = state.manufacturedDate,
            countryOfOrigin = state.countryOfOrigin,
            onBrandNameChange = { onIntent(Intent.BrandNameChange(it)) },
            onMaterialNameChange = { onIntent(Intent.MaterialNameChange(it)) },
            onPrimaryColorChange = { onIntent(Intent.PrimaryColorChange(it)) },
            onDensityChange = { onIntent(Intent.DensityChange(it)) },
            onGtinChange = { onIntent(Intent.GtinChange(it)) },
            onManufacturedDateChange = { onIntent(Intent.ManufacturedDateChange(it)) },
            onCountryOfOriginChange = { onIntent(Intent.CountryOfOriginChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Material Properties Section
        MaterialPropertiesSection(
            materialTags = state.materialTags,
            hasGreenguardCertification = state.hasGreenguardCertification,
            hasFlameRetardantCertification = state.hasFlameRetardantCertification,
            onMaterialTagsChange = { onIntent(Intent.MaterialTagsChange(it)) },
            onGreenguardChange = { onIntent(Intent.GreenguardCertificationChange(it)) },
            onFlameRetardantChange = { onIntent(Intent.FlameRetardantCertificationChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Temperature Settings Section
        TemperatureSettingsSection(
            printTemperature = state.printTemperature,
            preheatTemperature = state.preheatTemperature,
            bedTemperature = state.bedTemperature,
            onPrintTemperatureChange = { onIntent(Intent.PrintTemperatureChange(it)) },
            onPreheatTemperatureChange = { onIntent(Intent.PreheatTemperatureChange(it)) },
            onBedTemperatureChange = { onIntent(Intent.BedTemperatureChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weight Information Section
        WeightInformationSection(
            weight = state.weight,
            onWeightChange = { onIntent(Intent.WeightChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // NFC URL Options Section
        NfcUrlOptionsSection(
            nfcUrlOptions = state.nfcUrlOptions,
            onNfcUrlOptionsChange = { onIntent(Intent.NfcUrlOptionsChange(it)) },
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button
        Button(
            onClick = { /* TODO: Handle save */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isValid && state.materialTagsValid && state.gtinValid,
        ) {
            Text("Write to NFC Tag")
        }

        // Validation messages
        if (!state.isValid) {
            Text(
                text = "Material type is required",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
        if (!state.materialTagsValid) {
            Text(
                text = "Maximum ${TagState.MAX_MATERIAL_TAGS} material tags allowed",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
        if (!state.gtinValid) {
            Text(
                text = "GTIN must be 8, 12, 13, or 14 digits",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun Preview_App() {
    App()
}
