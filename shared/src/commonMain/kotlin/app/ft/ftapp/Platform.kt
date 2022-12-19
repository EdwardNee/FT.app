package app.ft.ftapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform