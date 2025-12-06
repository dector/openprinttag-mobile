package space.dector.openprinttag.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.model.Color
import space.dector.openprinttag.model.CountryCode
import space.dector.openprinttag.ui.Theme


@Composable
fun BasicInformationSection(
    brandName: String,
    materialName: String,
    primaryColor: Color?,
    density: Float?,
    gtin: String,
    manufacturedDate: LocalDate?,
    countryOfOrigin: CountryCode?,
    onBrandNameChange: (String) -> Unit,
    onMaterialNameChange: (String) -> Unit,
    onPrimaryColorChange: (Color?) -> Unit,
    onDensityChange: (Float?) -> Unit,
    onGtinChange: (String) -> Unit,
    onManufacturedDateChange: (LocalDate?) -> Unit,
    onCountryOfOriginChange: (CountryCode?) -> Unit,
) {
    SectionCard(title = "Basic Information") {
        OutlinedTextField(
            value = brandName,
            onValueChange = onBrandNameChange,
            label = { Text("Brand Name") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = materialName,
            onValueChange = onMaterialNameChange,
            label = { Text("Material Name") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = primaryColor?.toHexString() ?: "",
            onValueChange = {
                try {
                    onPrimaryColorChange(if (it.isBlank()) null else Color.fromHex(it))
                } catch (e: Exception) {
                    // Invalid color, ignore
                }
            },
            label = { Text("Primary Color (Hex)") },
            placeholder = { Text("#FF5733") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = density?.toString() ?: "",
            onValueChange = { onDensityChange(it.toFloatOrNull()) },
            label = { Text("Density (g/cm³)") },
            placeholder = { Text("1.24") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = gtin,
            onValueChange = onGtinChange,
            label = { Text("GTIN") },
            placeholder = { Text("8, 12, 13, or 14 digits") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Manufactured Date (simplified for now)
        Text(
            text = "Manufactured Date: ${manufacturedDate?.toString() ?: "Not set"}",
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Country of Origin (simplified for now)
        Text(
            text = "Country of Origin: ${countryOfOrigin?.toString() ?: "Not set"}",
            style = MaterialTheme.typography.bodyMedium,
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
