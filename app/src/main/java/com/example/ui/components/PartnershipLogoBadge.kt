package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.theme.CharcoalCardTop
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextWhite

@Composable
fun PartnershipLogoBadge(
    name: String,
    logoUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 56.dp,
    fallbackIcon: ImageVector = Icons.Default.Casino
) {
    val shape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .border(1.dp, OrangeBorder, shape)
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF321E10),
                        CharcoalCardTop
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!logoUrl.isNullOrBlank()) {
            AsyncImage(
                model = logoUrl,
                contentDescription = "$name logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(size)
            )
        } else {
            // Stylized logo placeholder with badge/initials & orange accent
            val initials = name.split(" ")
                .mapNotNull { it.firstOrNull()?.toString() }
                .take(2)
                .joinToString("")
                .uppercase()

            if (initials.isNotEmpty()) {
                Text(
                    text = initials,
                    color = OrangeLight,
                    fontSize = (size.value * 0.35f).sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
            } else {
                Icon(
                    imageVector = fallbackIcon,
                    contentDescription = "$name logo placeholder",
                    tint = OrangePrimary,
                    modifier = Modifier.size(size * 0.55f)
                )
            }
        }
    }
}

@Composable
fun WebsiteFaviconBadge(
    name: String,
    logoUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 44.dp
) {
    val shape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .border(1.dp, OrangeBorder, shape)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF28190E),
                        Color(0xFF14151C)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        if (!logoUrl.isNullOrBlank()) {
            AsyncImage(
                model = logoUrl,
                contentDescription = "$name favicon",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(size)
            )
        } else {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = "$name website icon",
                tint = OrangeLight,
                modifier = Modifier.size(size * 0.55f)
            )
        }
    }
}
