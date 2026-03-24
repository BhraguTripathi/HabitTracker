package com.example.habittracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.theme.CircleShape
import com.example.habittracker.ui.theme.ErrorRed
import com.example.habittracker.ui.theme.NavIconActive
import com.example.habittracker.ui.theme.NavIconInactive
import com.example.habittracker.ui.theme.Primary

enum class NavDestination { HOME, EXPLORE, ACTIVITY, PROFILE}

@Composable
fun BottomNavBar(
    currentDestination: NavDestination,
    onHomeClick : () -> Unit = {},
    onFabClick : () -> Unit = {},
    onExploreClick : () -> Unit = {},
    onActivityClick : () -> Unit = {},
    onProfileClick : () -> Unit = {},
    isFabOpen          : Boolean = false,
    hasActivityBadge   : Boolean = true,
    modifier           : Modifier = Modifier
){
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = Color.White,
        // FIX: Use the 'modifier' parameter so Alignment.BottomCenter is applied
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 12.dp
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(64.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            // ── Home ──────────────────────────────────────────────
            NavIcon(
                icon       = Icons.Default.Home,
                isSelected = currentDestination == NavDestination.HOME,
                onClick    = onHomeClick
            )

            // ── Explore ───────────────────────────────────────────
            NavIcon(
                icon       = Icons.Default.Explore,
                isSelected = currentDestination == NavDestination.EXPLORE,
                onClick    = onExploreClick
            )

            // ── FAB (center) ──────────────────────────────────────
            Box(
                modifier         = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Primary)
                    .clickable { onFabClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = if (isFabOpen) Icons.Default.Close else Icons.Default.Add,
                    contentDescription = if (isFabOpen) "Close" else "Add",
                    tint               = Color.White,
                    modifier           = Modifier.size(26.dp)
                )
            }

            // ── Activity (with red dot badge) ─────────────────────
            Box(contentAlignment = Alignment.TopEnd) {
                NavIcon(
                    icon       = Icons.Default.RadioButtonChecked,
                    isSelected = currentDestination == NavDestination.ACTIVITY,
                    onClick    = onActivityClick
                )
                // Red notification dot
                if (hasActivityBadge) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(ErrorRed, CircleShape)
                            .border(1.dp, Color.White, CircleShape)
                    )
                }
            }

            // ── Profile ───────────────────────────────────────────
            NavIcon(
                icon       = Icons.Default.Person,
                isSelected = currentDestination == NavDestination.PROFILE,
                onClick    = onProfileClick
            )
        }
    }

}


@Composable
fun NavIcon(
    icon : ImageVector,
    isSelected : Boolean,
    onClick : () -> Unit = {},
){
    Box(
        modifier = Modifier
            .size(44.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) NavIconActive else NavIconInactive,
            modifier = Modifier.size(26.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun BottomNavBarPreview(){
    BottomNavBar(
        currentDestination = NavDestination.HOME,
        isFabOpen = false,
        hasActivityBadge = true
    )
}
