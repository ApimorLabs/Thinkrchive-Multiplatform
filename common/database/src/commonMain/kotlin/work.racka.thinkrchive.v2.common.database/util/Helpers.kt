package work.racka.thinkrchive.v2.common.database.util

import work.racka.thinkrchive.v2.common.database.model.Thinkpad
import workrackathinkrchivev2commondatabase.db.ThinkpadDatabaseQueries
import workrackathinkrchivev2commondatabase.db.ThinkpadListTable

object Helpers {

    fun ThinkpadDatabaseQueries.insertAllThinkpadsToDb(
        response: List<Thinkpad>
    ) {
        // This implementation removes all existing rows
        // in db and then inserts results from api request
        // Using "transaction" accelerate the batch of queries, especially inserting
        this.transaction {
            deleteAll()
            response.forEach {
                insertAllThinkpads(
                    ThinkpadListTable(
                        model = it.model,
                        imageUrl = it.imageUrl,
                        releaseDate = it.releaseDate,
                        series = it.series,
                        marketPriceStart = it.marketPriceStart,
                        marketPriceEnd = it.marketPriceEnd,
                        processorPlatforms = it.processorPlatforms,
                        processors = it.processors,
                        graphics = it.graphics,
                        maxRam = it.maxRam,
                        displayRes = it.displayRes,
                        touchScreen = it.touchScreen,
                        screenSize = it.screenSize,
                        backlitKb = it.backlitKb,
                        fingerPrintReader = it.fingerPrintReader,
                        kbType = it.kbType,
                        dualBatt = it.dualBatt,
                        internalBatt = it.internalBatt,
                        externalBatt = it.externalBatt,
                        psrefLink = it.psrefLink,
                        biosVersion = it.biosVersion,
                        knownIssues = it.knownIssues,
                        knownIssuesLinks = it.knownIssuesLinks,
                        displaysSupported = it.displaysSupported,
                        otherMods = it.otherMods,
                        otherModsLinks = it.otherModsLinks,
                        biosLockIn = it.biosLockIn,
                        ports = it.ports
                    )
                )
            }
        }
    }

    fun ThinkpadDatabaseQueries.getAllThinkpadsFromDb() =
        this.getAllThinkpads(
            mapper = { model, imageUrl, releaseDate, series, marketPriceStart,
                       marketPriceEnd, processorPlatforms, processors, graphics,
                       maxRam, displayRes, touchScreen, screenSize, backlitKb,
                       fingerPrintReader, kbType, dualBatt, internalBatt, externalBatt,
                       psrefLink, biosVersion, knownIssues, knownIssuesLinks, displaysSupported,
                       otherMods, otherModsLinks, biosLockIn, ports ->
                Thinkpad(
                    model = model,
                    imageUrl = imageUrl,
                    releaseDate = releaseDate,
                    series = series,
                    marketPriceStart = marketPriceStart,
                    marketPriceEnd = marketPriceEnd,
                    processorPlatforms = processorPlatforms,
                    processors = processors,
                    graphics = graphics,
                    maxRam = maxRam,
                    displayRes = displayRes,
                    touchScreen = touchScreen,
                    screenSize = screenSize,
                    backlitKb = backlitKb,
                    fingerPrintReader = fingerPrintReader,
                    kbType = kbType,
                    dualBatt = dualBatt,
                    internalBatt = internalBatt,
                    externalBatt = externalBatt,
                    psrefLink = psrefLink,
                    biosVersion = biosVersion,
                    knownIssues = knownIssues,
                    knownIssuesLinks = knownIssuesLinks,
                    displaysSupported = displaysSupported,
                    otherMods = otherMods,
                    otherModsLinks = otherModsLinks,
                    biosLockIn = biosLockIn,
                    ports = ports
                )
            }
        )

    fun ThinkpadDatabaseQueries.getThinkpadFromDb(thinkpadModel: String) =
        this.getThinkpad(
            model = thinkpadModel,
            mapper = { model, imageUrl, releaseDate, series, marketPriceStart,
                       marketPriceEnd, processorPlatforms, processors, graphics,
                       maxRam, displayRes, touchScreen, screenSize, backlitKb,
                       fingerPrintReader, kbType, dualBatt, internalBatt, externalBatt,
                       psrefLink, biosVersion, knownIssues, knownIssuesLinks, displaysSupported,
                       otherMods, otherModsLinks, biosLockIn, ports ->
                Thinkpad(
                    model = model,
                    imageUrl = imageUrl,
                    releaseDate = releaseDate,
                    series = series,
                    marketPriceStart = marketPriceStart,
                    marketPriceEnd = marketPriceEnd,
                    processorPlatforms = processorPlatforms,
                    processors = processors,
                    graphics = graphics,
                    maxRam = maxRam,
                    displayRes = displayRes,
                    touchScreen = touchScreen,
                    screenSize = screenSize,
                    backlitKb = backlitKb,
                    fingerPrintReader = fingerPrintReader,
                    kbType = kbType,
                    dualBatt = dualBatt,
                    internalBatt = internalBatt,
                    externalBatt = externalBatt,
                    psrefLink = psrefLink,
                    biosVersion = biosVersion,
                    knownIssues = knownIssues,
                    knownIssuesLinks = knownIssuesLinks,
                    displaysSupported = displaysSupported,
                    otherMods = otherMods,
                    otherModsLinks = otherModsLinks,
                    biosLockIn = biosLockIn,
                    ports = ports
                )
            }
        )

}