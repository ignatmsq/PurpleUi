package ah.net.purpleui.ui.screens.matras.widgets

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object MantraAnimationSpecs {
    val enterTransition = fadeIn(animationSpec = tween(400)) + 
        slideInHorizontally(
            animationSpec = tween(400),
            initialOffsetX = { it / 3 }
        )
        
    val exitTransition = fadeOut(animationSpec = tween(300)) + 
        slideOutHorizontally(
            animationSpec = tween(300),
            targetOffsetX = { -it / 3 }
        )
}