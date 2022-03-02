package work.racka.thinkrchive.v2.common.about.data

import domain.AppAbout

interface AboutData {

    fun getAppAboutData(): AppAbout

    fun hasUpdates(): Boolean
}