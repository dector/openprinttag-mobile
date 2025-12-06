package space.dector.openprinttag

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.ui.Theme
import space.dector.openprinttag.ui.components.BasicInformationSection
import space.dector.openprinttag.ui.components.MaterialClassificationSection
import space.dector.openprinttag.ui.components.MaterialPropertiesSection
import space.dector.openprinttag.ui.components.NfcUrlOptionsSection
import space.dector.openprinttag.ui.components.TemperatureSettingsSection
import space.dector.openprinttag.ui.components.WeightInformationSection
import space.dector.openprinttag.TagInfoIntent as Intent
import space.dector.openprinttag.TagInfoState as State


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
    state: State,
    onIntent: (Intent) -> Unit,
) {
    when (state) {
        is State.WithData -> StateWithData(state, onIntent)
    }
}

@Composable
fun StateWithData(state: State.WithData, onIntent: (Intent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
    ) {
        val data = state.data

        Text(
            text = "OpenPrintTag Configuration",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        // Material Classification Section
        MaterialClassificationSection(
            materialClass = data.materialClass,
            materialType = data.materialType,
            onMaterialClassChange = { onIntent(Intent.MaterialClassChange(it)) },
            onMaterialTypeChange = { onIntent(Intent.MaterialTypeChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Basic Information Section
        BasicInformationSection(
            brandName = data.brandName,
            materialName = data.materialName,
            primaryColor = data.primaryColor,
            density = data.density,
            gtin = data.gtin,
            manufacturedDate = data.manufacturedDate,
            countryOfOrigin = data.countryOfOrigin,
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
            materialTags = data.materialTags,
            hasGreenguardCertification = data.hasGreenguardCertification,
            hasFlameRetardantCertification = data.hasFlameRetardantCertification,
            onMaterialTagsChange = { onIntent(Intent.MaterialTagsChange(it)) },
            onGreenguardChange = { onIntent(Intent.GreenguardCertificationChange(it)) },
            onFlameRetardantChange = { onIntent(Intent.FlameRetardantCertificationChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Temperature Settings Section
        TemperatureSettingsSection(
            printTemperature = data.printTemperature,
            preheatTemperature = data.preheatTemperature,
            bedTemperature = data.bedTemperature,
            onPrintTemperatureChange = { onIntent(Intent.PrintTemperatureChange(it)) },
            onPreheatTemperatureChange = { onIntent(Intent.PreheatTemperatureChange(it)) },
            onBedTemperatureChange = { onIntent(Intent.BedTemperatureChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weight Information Section
        WeightInformationSection(
            weight = data.weight,
            onWeightChange = { onIntent(Intent.WeightChange(it)) },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // NFC URL Options Section
        NfcUrlOptionsSection(
            nfcUrlOptions = data.nfcUrlOptions,
            onNfcUrlOptionsChange = { onIntent(Intent.NfcUrlOptionsChange(it)) },
        )

        Spacer(modifier = Modifier.height(24.dp))

        VerificationSection(state)

        Spacer(modifier = Modifier.height(24.dp))

        // Save Button
        Button(
            onClick = { /* TODO: Handle save */ },
            modifier = Modifier.fillMaxWidth(),
            enabled = data.isValid && data.materialTagsValid && data.gtinValid,
        ) {
            Text("Write to NFC Tag")
        }
    }
}

@Composable
fun VerificationSection(state: State.WithData) {
    Column {
        state.verificationErrors.forEach { error ->
            Text(
                text = error.text(),
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
