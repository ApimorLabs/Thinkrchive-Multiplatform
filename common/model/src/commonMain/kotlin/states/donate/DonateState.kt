package states.donate

import domain.Product

sealed class DonateState {
    data class State(
        val product: List<Product> = listOf()
    ) : DonateState()

    companion object {
        val EmptyState = State()
    }
}
