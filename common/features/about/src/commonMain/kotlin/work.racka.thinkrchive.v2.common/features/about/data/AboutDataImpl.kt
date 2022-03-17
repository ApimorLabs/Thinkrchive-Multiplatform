package work.racka.thinkrchive.v2.common.features.about.data

import domain.AppAbout
import work.racka.thinkrchive.v2.common.features.about.updates.checkUpdates
import work.racka.thinkrchive.v2.common.features.about.util.Constants

internal class AboutDataImpl : AboutData {
    override fun getAppAboutData(): AppAbout =
        AppAbout(
            appName = Constants.APP_NAME,
            tagline = Constants.TAGLINE,
            appVersion = appVersion
        )

    override fun hasUpdates(): Boolean = checkUpdates()
}