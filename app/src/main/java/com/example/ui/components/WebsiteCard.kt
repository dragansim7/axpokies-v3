package com.example.ui.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.OpenInNew
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Website
import com.example.ui.theme.CharcoalCardBottom
import com.example.ui.theme.CharcoalCardTop
import com.example.ui.theme.DarkTextOnOrange
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextLightGrey
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@Composable
fun WebsiteCard(
    website: Website,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val cardShape = RoundedCornerShape(18.dp)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(cardShape)
            .border(1.dp, OrangeBorder, cardShape)
            .testTag("website_card_${website.id}"),
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            CharcoalCardTop,
                            CharcoalCardBottom
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                // Header: Favicon/Badge + Name + Verified Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    WebsiteFaviconBadge(
                        name = website.name,
                        logoUrl = website.logoUrl,
                        size = 46.dp
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = website.name,
                                color = TextWhite,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )

                            if (website.badge != null) {
                                Surface(
                                    color = Color(0x33FF7A00),
                                    shape = RoundedCornerShape(6.dp),
                                    border = androidx.compose.foundation.BorderStroke(0.8.dp, OrangeBorder)
                                ) {
                                    Text(
                                        text = website.badge,
                                        color = OrangeLight,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(3.dp))

                        // URL Display with lock icon
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = OrangeLight,
                                modifier = Modifier.size(11.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = website.url.removePrefix("https://").removePrefix("http://"),
                                color = TextMuted,
                                fontSize = 12.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Description
                Text(
                    text = website.description,
                    color = TextLightGrey,
                    fontSize = 13.5.sp,
                    lineHeight = 19.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // "Visit Website" Button
                Button(
                    onClick = { launchWebsiteUrl(context, website.url) },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OrangePrimary,
                        contentColor = DarkTextOnOrange
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("btn_visit_website_${website.id}")
                ) {
                    Text(
                        text = stringResource(R.string.btn_visit_website),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.OpenInNew,
                        contentDescription = null,
                        tint = DarkTextOnOrange,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

private fun launchWebsiteUrl(context: Context, url: String) {
    val sanitizedUrl = if (!url.startsWith("http://") && !url.startsWith("https://")) {
        "https://$url"
    } else {
        url
    }
    try {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(sanitizedUrl)).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(browserIntent)
    } catch (e: Exception) {
        Toast.makeText(context, "Unable to open browser: ${e.localizedMessage ?: "Invalid URL"}", Toast.LENGTH_SHORT).show()
    }
}
