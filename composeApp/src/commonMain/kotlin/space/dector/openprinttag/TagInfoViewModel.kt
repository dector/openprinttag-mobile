package space.dector.openprinttag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import space.dector.openprinttag.model.*
import space.dector.openprinttag.TagInfoIntent as Intent


class TagInfoViewModel : ViewModel() {

    private val _state = MutableStateFlow(TagState())
    val state: StateFlow<TagState> = _state.asStateFlow()

    fun onIntent(intent: Intent) {
        viewModelScope.launch {
            when (intent) {
                is Intent.MaterialClassChange -> _state.value = _state.value.copy(materialClass = intent.value)
                is Intent.MaterialTypeChange -> _state.value = _state.value.copy(materialType = intent.value)
                is Intent.BrandNameChange -> _state.value = _state.value.copy(brandName = intent.value)
                is Intent.MaterialNameChange -> _state.value = _state.value.copy(materialName = intent.value)
                is Intent.PrimaryColorChange -> _state.value = _state.value.copy(primaryColor = intent.value)
                is Intent.DensityChange -> _state.value = _state.value.copy(density = intent.value)
                is Intent.GtinChange -> _state.value = _state.value.copy(gtin = intent.value)
                is Intent.ManufacturedDateChange -> _state.value = _state.value.copy(manufacturedDate = intent.value)
                is Intent.CountryOfOriginChange -> _state.value = _state.value.copy(countryOfOrigin = intent.value)
                is Intent.MaterialTagsChange -> _state.value = _state.value.copy(materialTags = intent.value)
                is Intent.GreenguardCertificationChange -> _state.value = _state.value.copy(hasGreenguardCertification = intent.value)
                is Intent.FlameRetardantCertificationChange -> _state.value = _state.value.copy(hasFlameRetardantCertification = intent.value)
                is Intent.PrintTemperatureChange -> _state.value = _state.value.copy(printTemperature = intent.value)
                is Intent.PreheatTemperatureChange -> _state.value = _state.value.copy(preheatTemperature = intent.value)
                is Intent.BedTemperatureChange -> _state.value = _state.value.copy(bedTemperature = intent.value)
                is Intent.WeightChange -> _state.value = _state.value.copy(weight = intent.value)
                is Intent.NfcUrlOptionsChange -> _state.value = _state.value.copy(nfcUrlOptions = intent.value)
            }
        }
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
