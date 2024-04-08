package com.phucbruh.giua_ki.presentation.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Functions
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar(
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                NavItemConstants.BottomNavItems.forEach { navItem ->
                    IconButton(onClick = { navController.navigate(navItem.route) }) {
                        Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                    }
                }
            }
        }
    )
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
)

object NavItemConstants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Prime",
            icon = Icons.Filled.QuestionMark,
            route = Routes.Prime.route
        ),
        BottomNavItem(
            label = "Equation",
            icon = Icons.Default.Functions,
            route = Routes.Equation.route
        ),
        BottomNavItem(
            label = "Book",
            icon = Icons.Filled.Book,
            route = Routes.Book.route
        ),
    )
}