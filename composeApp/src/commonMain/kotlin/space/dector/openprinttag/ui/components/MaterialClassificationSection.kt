package space.dector.openprinttag.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.model.MaterialClass
import space.dector.openprinttag.model.MaterialType
import space.dector.openprinttag.ui.Theme


@Composable
fun MaterialClassificationSection(
    materialClass: MaterialClass,
    materialType: MaterialType?,
    onMaterialClassChange: (MaterialClass) -> Unit,
    onMaterialTypeChange: (MaterialType?) -> Unit,
) {
    SectionCard(title = "Material Classification") {
        // Material Class Dropdown
        var materialClassExpanded by remember { mutableStateOf(false) }
        OutlinedButton(
            onClick = { materialClassExpanded = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Material Class: ${materialClass.name}")
        }
        DropdownMenu(
            expanded = materialClassExpanded,
            onDismissRequest = { materialClassExpanded = false },
        ) {
            MaterialClass.entries.forEach { mc ->
                DropdownMenuItem(
                    text = { Text(mc.name) },
                    onClick = {
                        onMaterialClassChange(mc)
                        materialClassExpanded = false
                    },
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Material Type Dropdown (Required)
        var materialTypeExpanded by remember { mutableStateOf(false) }
        OutlinedButton(
            onClick = { materialTypeExpanded = true },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(materialType?.displayName ?: "Select Material Type *")
        }
        DropdownMenu(
            expanded = materialTypeExpanded,
            onDismissRequest = { materialTypeExpanded = false },
        ) {
            MaterialType.entries.forEach { mt ->
                DropdownMenuItem(
                    text = { Text("${mt.displayName} - ${mt.fullName}") },
                    onClick = {
                        onMaterialTypeChange(mt)
                        materialTypeExpanded = false
                    },
                )
            }
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
