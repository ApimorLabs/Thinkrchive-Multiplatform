package work.racka.thinkrchive.v2.common.integration.containers.donate

import org.orbitmvi.orbit.ContainerHost
import states.donate.DonateSideEffect
import states.donate.DonateState

internal expect class DonateContainerHostImpl : DonateContainerHost,
    ContainerHost<DonateState.State, DonateSideEffect>