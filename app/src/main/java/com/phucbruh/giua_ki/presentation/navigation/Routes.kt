package com.phucbruh.giua_ki.presentation.navigation

sealed class Routes(val route: String) {
    object Prime: Routes("prime")
    object Equation: Routes("equation")
    object Book: Routes("book")
}