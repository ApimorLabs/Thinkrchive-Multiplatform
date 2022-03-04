package work.racka.thinkrchive.v2.common.integration.containers.donate

import org.orbitmvi.orbit.ContainerHost
import states.donate.DonateSideEffect
import states.donate.DonateState

expect class DonateContainerHost : ContainerHost<DonateState.State, DonateSideEffect>