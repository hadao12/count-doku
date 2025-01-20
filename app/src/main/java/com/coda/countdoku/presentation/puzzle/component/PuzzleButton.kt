import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IconButtonComponent(
    modifier: Modifier = Modifier,
    icon: Any?,
    color: Color,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .size(64.dp)
            .clip(CircleShape)
            .background(
                color = color,
                shape = CircleShape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        when (icon) {
            is String -> {
                Text(
                    text = icon,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 24.sp
                    )
                )
            }
            is Int -> {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Icon button",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewTextButtonComponent() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        IconButtonComponent(
            onClick = { },
            icon = "+",
            color = Color.White.copy(alpha = 0.15f)
        )
        IconButtonComponent(
            onClick = {  },
            icon = "2",
            color = Color.White
        )
    }
}
