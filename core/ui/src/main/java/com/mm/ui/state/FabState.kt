package com.mm.ui.state

sealed class FabState {

    data object Hidden : FabState()

    data class Shown(
        val iconRes: Int,
        val contentDescription: String,
        val onClick: () -> Unit
    ) : FabState()
}
