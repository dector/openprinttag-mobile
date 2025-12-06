package space.dector.openprinttag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.model.TemperatureRange
import space.dector.openprinttag.ui.Theme

@Composable
fun TemperatureSettingsSection(
    printTemperature: TemperatureRange?,
    preheatTemperature: Int?,
    bedTemperature: TemperatureRange?,
    onPrintTemperatureChange: (TemperatureRange?) -> Unit,
    onPreheatTemperatureChange: (Int?) -> Unit,
    onBedTemperatureChange: (TemperatureRange?) -> Unit,
) {
    SectionCard(title = "Temperature Settings (°C)") {
        // Print Temperature Range
        Text("Print Temperature", style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = printTemperature?.min?.toString() ?: "",
                onValueChange = {
                    val min = it.toIntOrNull()
                    if (min != null) {
                        onPrintTemperatureChange(
                            TemperatureRange(min, printTemperature?.max ?: min),
                        )
                    }
                },
                label = { Text("Min") },
                modifier = Modifier.weight(1f),
            )
            OutlinedTextField(
                value = printTemperature?.max?.toString() ?: "",
                onValueChange = {
                    val max = it.toIntOrNull()
                    if (max != null) {
                        onPrintTemperatureChange(
                            TemperatureRange(printTemperature?.min ?: max, max),
                        )
                    }
                },
                label = { Text("Max") },
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Preheat Temperature
        OutlinedTextField(
            value = preheatTemperature?.toString() ?: "",
            onValueChange = { onPreheatTemperatureChange(it.toIntOrNull()) },
            label = { Text("Preheat Temperature") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Bed Temperature Range
        Text("Bed Temperature", style = MaterialTheme.typography.labelMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = bedTemperature?.min?.toString() ?: "",
                onValueChange = {
                    val min = it.toIntOrNull()
                    if (min != null) {
                        onBedTemperatureChange(
                            TemperatureRange(min, bedTemperature?.max ?: min),
                        )
                    }
                },
                label = { Text("Min") },
                modifier = Modifier.weight(1f),
            )
            OutlinedTextField(
                value = bedTemperature?.max?.toString() ?: "",
                onValueChange = {
                    val max = it.toIntOrNull()
                    if (max != null) {
                        onBedTemperatureChange(
                            TemperatureRange(bedTemperature?.min ?: max, max),
                        )
                    }
                },
                label = { Text("Max") },
                modifier = Modifier.weight(1f),
            )
        }
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
