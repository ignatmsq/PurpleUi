package ah.net.purpleui.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ah.net.purpleui.ui.theme.ButtonPurple
import ah.net.purpleui.ui.theme.ButtonTextStyle
import ah.net.purpleui.ui.theme.OnButtonPurple

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(66.dp), // Proper aspect ratio
        colors = ButtonDefaults.buttonColors(
            containerColor = ButtonPurple,
            contentColor = OnButtonPurple
        ),
        shape = RoundedCornerShape(24.dp), // Proper rounded corners (half of height)
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp
        )
    ) {
        Text(
            text = text,
            style = ButtonTextStyle, // Using custom ExtraBold style
            textAlign = TextAlign.Center
        )
    }
} 