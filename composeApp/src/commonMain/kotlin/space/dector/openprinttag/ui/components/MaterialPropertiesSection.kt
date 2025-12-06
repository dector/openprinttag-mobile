package space.dector.openprinttag.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.model.MaterialTag
import space.dector.openprinttag.model.TagCategory
import space.dector.openprinttag.ui.Theme
import androidx.compose.foundation.layout.FlowRow
import space.dector.openprinttag.model.TagState


@Composable
fun MaterialPropertiesSection(
    materialTags: Set<MaterialTag>,
    hasGreenguardCertification: Boolean,
    hasFlameRetardantCertification: Boolean,
    onMaterialTagsChange: (Set<MaterialTag>) -> Unit,
    onGreenguardChange: (Boolean) -> Unit,
    onFlameRetardantChange: (Boolean) -> Unit,
) {
    SectionCard(title = "Material Properties") {
        Text(
            text = "Material Tags (${materialTags.size}/${TagState.MAX_MATERIAL_TAGS})",
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Group tags by category
        TagCategory.entries.forEach { category ->
            val categoryTags = MaterialTag.entries.filter { it.category == category }
            if (categoryTags.isNotEmpty()) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 8.dp),
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    categoryTags.forEach { tag ->
                        FilterChip(
                            selected = tag in materialTags,
                            onClick = {
                                onMaterialTagsChange(
                                    if (tag in materialTags) {
                                        materialTags - tag
                                    } else {
                                        if (materialTags.size < TagState.MAX_MATERIAL_TAGS) {
                                            materialTags + tag
                                        } else {
                                            materialTags
                                        }
                                    },
                                )
                            },
                            label = { Text(tag.name.replace("_", " ")) },
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Certifications
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = hasGreenguardCertification,
                onCheckedChange = onGreenguardChange,
            )
            Text("Greenguard Certified (UL 2818)")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = hasFlameRetardantCertification,
                onCheckedChange = onFlameRetardantChange,
            )
            Text("Flame Retardant (UL 94 V0)")
        }
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
