package space.dector.openprinttag

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform