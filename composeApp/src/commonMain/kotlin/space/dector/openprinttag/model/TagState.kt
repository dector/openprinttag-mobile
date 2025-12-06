package space.dector.openprinttag.model

import kotlinx.datetime.LocalDate


/**
 * Represents the complete state of an OpenPrintTag NFC tag configuration.
 */
data class TagState(
    // Material Classification
    val materialClass: MaterialClass = MaterialClass.FFF,
    val materialType: MaterialType? = null,

    // Basic Information
    val brandName: String = "",
    val materialName: String = "",
    val primaryColor: Color? = null,
    val density: Float? = null, // g/cm³ (typical range: 0.5-5.0)
    val gtin: String = "", // 8, 12, 13, or 14 digits
    val manufacturedDate: LocalDate? = null,
    val countryOfOrigin: CountryCode? = null,

    // Material Properties & Tags
    val materialTags: Set<MaterialTag> = emptySet(), // Maximum 16 selections
    val hasGreenguardCertification: Boolean = false, // UL 2818
    val hasFlameRetardantCertification: Boolean = false, // UL 94 V0

    // Temperature Settings (°C)
    val printTemperature: TemperatureRange? = null,
    val preheatTemperature: Int? = null,
    val bedTemperature: TemperatureRange? = null,

    // Weight Information (g)
    val weight: WeightInfo? = null,

    // NFC Tag Options
    val nfcUrlOptions: NfcUrlOptions = NfcUrlOptions(),
) {
    /**
     * Validates that material type is provided (required field).
     */
    val isValid: Boolean
        get() = materialType != null

    /**
     * Validates that material tags don't exceed maximum allowed.
     */
    val materialTagsValid: Boolean
        get() = materialTags.size <= MAX_MATERIAL_TAGS

    /**
     * Validates GTIN format (8, 12, 13, or 14 digits).
     */
    val gtinValid: Boolean
        get() = gtin.isEmpty() || gtin.matches(Regex("^\\d{8}$|^\\d{12}$|^\\d{13}$|^\\d{14}$"))

    companion object {
        const val MAX_MATERIAL_TAGS = 16
    }
}

/**
 * Represents a temperature range in °C.
 */
data class TemperatureRange(
    val min: Int,
    val max: Int,
) {
    init {
        require(min <= max) { "Minimum temperature must be less than or equal to maximum temperature" }
    }
}

/**
 * Represents weight information in grams.
 */
data class WeightInfo(
    val nominal: Float? = null,
    val actual: Float? = null,
    val emptyContainer: Float? = null,
) {
    init {
        nominal?.let { require(it > 0) { "Nominal weight must be positive" } }
        actual?.let { require(it > 0) { "Actual weight must be positive" } }
        emptyContainer?.let { require(it > 0) { "Empty container weight must be positive" } }
    }
}

/**
 * Represents NFC tag URL record options.
 */
data class NfcUrlOptions(
    val includeUrlRecord: Boolean = false,
    val tagUrl: String = "",
) {
    /**
     * Validates that if URL record is included, a URL is provided.
     */
    val isValid: Boolean
        get() = !includeUrlRecord || tagUrl.isNotBlank()
}

/**
 * Material classification types.
 */
enum class MaterialClass {
    FFF, // Filament
//    SLA, // Resin
//    SLS, // Powder
//    PELLET,
}

/**
 * Material types supported by OpenPrintTag.
 * Represents the specific polymer or material composition.
 */
enum class MaterialType(val displayName: String, val fullName: String) {
    PLA("PLA", "Polylactic Acid"),
    PETG("PETG", "Polyethylene Terephthalate Glycol"),
    TPU("TPU", "Thermoplastic Polyurethane"),
    ABS("ABS", "Acrylonitrile Butadiene Styrene"),
    ASA("ASA", "Acrylonitrile Styrene Acrylate"),
    PC("PC", "Polycarbonate"),
    PCTG("PCTG", "Polycyclohexylenedimethylene Terephthalate Glycol"),
    PP("PP", "Polypropylene"),
    PA6("PA6", "Polyamide 6 (Nylon 6)"),
    PA11("PA11", "Polyamide 11 (Nylon 11)"),
    PA12("PA12", "Polyamide 12 (Nylon 12)"),
    PA66("PA66", "Polyamide 66 (Nylon 66)"),
    CPE("CPE", "Copolyester"),
    TPE("TPE", "Thermoplastic Elastomer"),
    HIPS("HIPS", "High Impact Polystyrene"),
    PHA("PHA", "Polyhydroxyalkanoate"),
    PET("PET", "Polyethylene Terephthalate"),
    PEI("PEI", "Polyetherimide (Ultem)"),
    PBT("PBT", "Polybutylene Terephthalate"),
    PVB("PVB", "Polyvinyl Butyral"),
    PVA("PVA", "Polyvinyl Alcohol"),
    PEKK("PEKK", "Polyetherketoneketone"),
    PEEK("PEEK", "Polyether Ether Ketone"),
    BVOH("BVOH", "Butenediol Vinyl Alcohol Copolymer"),
    TPC("TPC", "Thermoplastic Copolyester"),
    PPS("PPS", "Polyphenylene Sulfide"),
    PPSU("PPSU", "Polyphenylsulfone"),
    PVC("PVC", "Polyvinyl Chloride"),
    PEBA("PEBA", "Polyether Block Amide"),
    PVDF("PVDF", "Polyvinylidene Fluoride"),
    PPA("PPA", "Polyphthalamide"),
    PCL("PCL", "Polycaprolactone"),
    PES("PES", "Polyethersulfone"),
    PMMA("PMMA", "Polymethyl Methacrylate (Acrylic)"),
    POM("POM", "Polyoxymethylene (Delrin)"),
    PPE("PPE", "Polyphenylene Ether"),
    PS("PS", "Polystyrene"),
    PSU("PSU", "Polysulfone"),
    TPI("TPI", "Thermoplastic Polyimide"),
    SBS("SBS", "Styrene-Butadiene-Styrene"),
    OBC("OBC", "Olefin Block Copolymer");

    companion object {
        /**
         * Finds a MaterialType by its display name or full name (case-insensitive).
         */
        fun fromString(value: String): MaterialType? {
            return entries.find {
                it.displayName.equals(value, ignoreCase = true) ||
                        it.fullName.equals(value, ignoreCase = true)
            }
        }
    }
}

/**
 * Represents a color value.
 */
data class Color(
    val red: Int,
    val green: Int,
    val blue: Int,
) {
    init {
        require(red in 0..255) { "Red value must be between 0 and 255" }
        require(green in 0..255) { "Green value must be between 0 and 255" }
        require(blue in 0..255) { "Blue value must be between 0 and 255" }
    }

    /**
     * Converts to hex color string (e.g., "#FF5733").
     */
    fun toHexString(): String = buildString {
        append("#")
        append(red.toString(16))
        append("#")
        append(green.toString(16))
        append("#")
        append(blue.toString(16))
    }

    companion object {
        /**
         * Creates a Color from a hex string (e.g., "#FF5733" or "FF5733").
         */
        fun fromHex(hex: String): Color {
            val cleanHex = hex.removePrefix("#")
            require(cleanHex.length == 6) { "Hex color must be 6 characters" }
            return Color(
                red = cleanHex.substring(0, 2).toInt(16),
                green = cleanHex.substring(2, 4).toInt(16),
                blue = cleanHex.substring(4, 6).toInt(16),
            )
        }
    }
}

/**
 * Material tag categories.
 */
enum class TagCategory {
    BIOLOGICAL,
    PHYSICAL,
    ELECTRICAL,
    THERMAL,
    OPTICAL,
    COMPOSITE
}

/**
 * Material tags for additional properties.
 * Based on OpenPrintTag specification.
 */
enum class MaterialTag(val category: TagCategory) {
    // Biological
    FOOD_SAFE(TagCategory.BIOLOGICAL),
    RECYCLED(TagCategory.BIOLOGICAL),

    // Physical
    ABRASIVE(TagCategory.PHYSICAL),
    FLEXIBLE(TagCategory.PHYSICAL),
    FOAM(TagCategory.PHYSICAL),
    HEAT_RESISTANT(TagCategory.PHYSICAL),
    LIGHTWEIGHT(TagCategory.PHYSICAL),
    MOISTURE_SENSITIVE(TagCategory.PHYSICAL),
    SOLUBLE_SUPPORT(TagCategory.PHYSICAL),
    WATER_SOLUBLE(TagCategory.PHYSICAL),

    // Electrical
    CONDUCTIVE(TagCategory.ELECTRICAL),
    ESD_SAFE(TagCategory.ELECTRICAL),

    // Thermal
    HIGH_TEMP(TagCategory.THERMAL),

    // Optical
    GLOW_IN_DARK(TagCategory.OPTICAL),
    TRANSPARENT(TagCategory.OPTICAL),
    UV_RESISTANT(TagCategory.OPTICAL),

    // Composite
    CARBON_FIBER(TagCategory.COMPOSITE),
    COMPOSITE(TagCategory.COMPOSITE),
    GLASS_FIBER(TagCategory.COMPOSITE),
    METAL_FILLED(TagCategory.COMPOSITE),
    WOOD_FILLED(TagCategory.COMPOSITE)
}
