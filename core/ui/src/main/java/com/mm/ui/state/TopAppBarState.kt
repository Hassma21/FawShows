package com.mm.ui.state

data class TopAppBarState(
    val visible: Boolean = false,
    val title: String = "",
    val showBack: Boolean = false,
    val onBackClick: (() -> Unit)? = null
)
