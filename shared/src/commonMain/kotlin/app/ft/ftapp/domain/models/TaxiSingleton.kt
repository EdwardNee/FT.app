package app.ft.ftapp.domain.models

object TaxiSingleton {
    object Query {
        const val CLID = "clid"
        const val APIKEY = "apikey"
        const val RLL = "rll"
        const val CLASS = "class"
        const val REQ = "req"
        const val LANG = "lang"
    }

    enum class Class(s: String) {
        ECONOM("econom"),
        BUSINESS("business"),
        COMFORTPLUS("comfortplus"),
        VIP("vip"),
        MINIVAN("minivan")
    }

    enum class Reqs(s: String) {
        YELLOWCARNUMBER("yellowcarnumber"),
        NOSMOKING("nosmoking"),
        CHILDCHAIR("childchair"),
        CONDITIONER("conditioner"),
        BICYCLE("bicycle"),
        CHECK("check"),
    }

    enum class Lang(s: String) {
        RU("ru"),
        EN("en")
    }
}