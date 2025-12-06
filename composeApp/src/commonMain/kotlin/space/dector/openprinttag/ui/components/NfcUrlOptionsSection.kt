package space.dector.openprinttag.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import space.dector.openprinttag.model.NfcUrlOptions
import space.dector.openprinttag.ui.Theme

@Composable
fun NfcUrlOptionsSection(
    nfcUrlOptions: NfcUrlOptions,
    onNfcUrlOptionsChange: (NfcUrlOptions) -> Unit,
) {
    SectionCard(title = "NFC URL Options") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = nfcUrlOptions.includeUrlRecord,
                onCheckedChange = {
                    onNfcUrlOptionsChange(nfcUrlOptions.copy(includeUrlRecord = it))
                },
            )
            Text("Include URL Record")
        }

        if (nfcUrlOptions.includeUrlRecord) {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = nfcUrlOptions.tagUrl,
                onValueChange = {
                    onNfcUrlOptionsChange(nfcUrlOptions.copy(tagUrl = it))
                },
                label = { Text("Tag URL") },
                placeholder = { Text("https://example.com/tag/123") },
                modifier = Modifier.fillMaxWidth(),
            )
        }
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
