package data.local

import kotlinx.serialization.Serializable

/**
 * Domain objects are plain Kotlin data classes that represent the things in our app. These are the
 * objects that should be displayed on screen, or manipulated by the app.
 *
 * see data/network for objects that are mapped to the network
 * see data/responses for objects that parse or prepare network calls
 */

@Serializable
data class ThinkpadDatabaseObject(
    val model: String,
    val imageUrl: String,
    val releaseDate: String,
    val series: String,
    val marketPriceStart: Int,
    val marketPriceEnd: Int,
    val processorPlatforms: String,
    val processors: String,
    val graphics: String,
    val maxRam: String,
    val displayRes: String,
    val touchScreen: String,
    val screenSize: String,
    val backlitKb: String,
    val fingerPrintReader: String,
    val kbType: String,
    val dualBatt: String,
    val internalBatt: String,
    val externalBatt: String,
    val psrefLink: String,
    val biosVersion: String,
    val knownIssues: String,
    val knownIssuesLinks: String,
    val displaysSupported: String,
    val otherMods: String,
    val otherModsLinks: String,
    val biosLockIn: String,
    val ports: String
)
