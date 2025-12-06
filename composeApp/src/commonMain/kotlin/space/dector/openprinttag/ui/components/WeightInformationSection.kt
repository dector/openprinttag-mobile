package space.dector.openprinttag.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.model.WeightInfo
import space.dector.openprinttag.ui.Theme

@Composable
fun WeightInformationSection(
    weight: WeightInfo?,
    onWeightChange: (WeightInfo?) -> Unit,
) {
    SectionCard(title = "Weight Information (g)") {
        OutlinedTextField(
            value = weight?.nominal?.toString() ?: "",
            onValueChange = {
                val nominal = it.toFloatOrNull()
                onWeightChange(
                    WeightInfo(
                        nominal = nominal,
                        actual = weight?.actual,
                        emptyContainer = weight?.emptyContainer,
                    ),
                )
            },
            label = { Text("Nominal Weight") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = weight?.actual?.toString() ?: "",
            onValueChange = {
                val actual = it.toFloatOrNull()
                onWeightChange(
                    WeightInfo(
                        nominal = weight?.nominal,
                        actual = actual,
                        emptyContainer = weight?.emptyContainer,
                    ),
                )
            },
            label = { Text("Actual Weight") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = weight?.emptyContainer?.toString() ?: "",
            onValueChange = {
                val container = it.toFloatOrNull()
                onWeightChange(
                    WeightInfo(
                        nominal = weight?.nominal,
                        actual = weight?.actual,
                        emptyContainer = container,
                    ),
                )
            },
            label = { Text("Empty Container Weight") },
            modifier = Modifier.fillMaxWidth(),
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
