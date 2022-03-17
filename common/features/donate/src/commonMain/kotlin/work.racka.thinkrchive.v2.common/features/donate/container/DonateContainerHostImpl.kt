package work.racka.thinkrchive.v2.common.features.donate.container

import org.orbitmvi.orbit.ContainerHost
import states.donate.DonateSideEffect
import states.donate.DonateState

internal expect class DonateContainerHostImpl : DonateContainerHost,
    ContainerHost<DonateState.State, DonateSideEffect>