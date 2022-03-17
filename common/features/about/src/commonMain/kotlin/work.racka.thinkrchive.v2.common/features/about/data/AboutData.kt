package work.racka.thinkrchive.v2.common.features.about.data

import domain.AppAbout

interface AboutData {

    fun getAppAboutData(): AppAbout

    fun hasUpdates(): Boolean
}