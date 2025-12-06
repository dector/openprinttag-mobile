package space.dector.openprinttag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import space.dector.openprinttag.model.Color
import space.dector.openprinttag.model.CountryCode
import space.dector.openprinttag.model.MaterialClass
import space.dector.openprinttag.model.MaterialTag
import space.dector.openprinttag.model.MaterialType
import space.dector.openprinttag.model.NfcUrlOptions
import space.dector.openprinttag.model.TagState
import space.dector.openprinttag.model.TemperatureRange
import space.dector.openprinttag.model.WeightInfo
import space.dector.openprinttag.TagInfoIntent as Intent
import space.dector.openprinttag.TagInfoState as State


class TagInfoViewModel : ViewModel() {

    private val _state = MutableStateFlow<State>(State.WithData(TagState()))
    val state: StateFlow<State> = _state.asStateFlow()

    fun onIntent(intent: Intent) {
        viewModelScope.launch {
            when (intent) {
                is Intent.MaterialClassChange ->
                    updateData { it.copy(materialClass = intent.value) }

                is Intent.MaterialTypeChange ->
                    updateData { it.copy(materialType = intent.value) }

                is Intent.BrandNameChange ->
                    updateData { it.copy(brandName = intent.value) }

                is Intent.MaterialNameChange ->
                    updateData { it.copy(materialName = intent.value) }

                is Intent.PrimaryColorChange ->
                    updateData { it.copy(primaryColor = intent.value) }

                is Intent.DensityChange ->
                    updateData { it.copy(density = intent.value) }

                is Intent.GtinChange ->
                    updateData { it.copy(gtin = intent.value) }

                is Intent.ManufacturedDateChange ->
                    updateData { it.copy(manufacturedDate = intent.value) }

                is Intent.CountryOfOriginChange ->
                    updateData { it.copy(countryOfOrigin = intent.value) }

                is Intent.MaterialTagsChange ->
                    updateData { it.copy(materialTags = intent.value) }

                is Intent.GreenguardCertificationChange ->
                    updateData { it.copy(hasGreenguardCertification = intent.value) }

                is Intent.FlameRetardantCertificationChange ->
                    updateData { it.copy(hasFlameRetardantCertification = intent.value) }

                is Intent.PrintTemperatureChange ->
                    updateData { it.copy(printTemperature = intent.value) }

                is Intent.PreheatTemperatureChange ->
                    updateData { it.copy(preheatTemperature = intent.value) }

                is Intent.BedTemperatureChange ->
                    updateData { it.copy(bedTemperature = intent.value) }

                is Intent.WeightChange ->
                    updateData { it.copy(weight = intent.value) }

                is Intent.NfcUrlOptionsChange ->
                    updateData { it.copy(nfcUrlOptions = intent.value) }
            }
        }
    }

    private fun updateData(transform: (TagState) -> TagState) {
        val currentState = _state.value
        if (currentState !is State.WithData) return

        val newData = transform(currentState.data)
        val errors = buildSet {
            if (!newData.isValid) {
                add(VerificationError.MaterialTypeRequired)
            }
            if (!newData.materialTagsValid) {
                add(VerificationError.MaterialTagsLimitExceeded)
            }
            if (!newData.gtinValid) {
                add(VerificationError.InvalidGtin)
            }
        }

        _state.update {
            currentState.copy(
                data = newData,
                verificationErrors = errors,
            )
        }
    }
}

sealed interface TagInfoState {
    data class WithData(
        val data: TagState,
        val verificationErrors: Set<VerificationError> = emptySet(),
    ) : State
}

sealed interface VerificationError {
    fun text(): String

    data object MaterialTypeRequired : VerificationError {
        override fun text(): String = "Material type is required"
    }

    data object MaterialTagsLimitExceeded : VerificationError {
        override fun text(): String = "Maximum ${TagState.MAX_MATERIAL_TAGS} material tags allowed"
    }

    data object InvalidGtin : VerificationError {
        override fun text(): String = "GTIN must be 8, 12, 13, or 14 digits"
    }
}

sealed interface TagInfoIntent {
    data class MaterialClassChange(val value: MaterialClass) : Intent
    data class MaterialTypeChange(val value: MaterialType?) : Intent
    data class BrandNameChange(val value: String) : Intent
    data class MaterialNameChange(val value: String) : Intent
    data class PrimaryColorChange(val value: Color?) : Intent
    data class DensityChange(val value: Float?) : Intent
    data class GtinChange(val value: String) : Intent
    data class ManufacturedDateChange(val value: LocalDate?) : Intent
    data class CountryOfOriginChange(val value: CountryCode?) : Intent
    data class MaterialTagsChange(val value: Set<MaterialTag>) : Intent
    data class GreenguardCertificationChange(val value: Boolean) : Intent
    data class FlameRetardantCertificationChange(val value: Boolean) : Intent
    data class PrintTemperatureChange(val value: TemperatureRange?) : Intent
    data class PreheatTemperatureChange(val value: Int?) : Intent
    data class BedTemperatureChange(val value: TemperatureRange?) : Intent
    data class WeightChange(val value: WeightInfo?) : Intent
    data class NfcUrlOptionsChange(val value: NfcUrlOptions) : Intent
}
