package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Partnership
import com.example.ui.components.PartnershipLogoBadge
import com.example.ui.components.WebsiteCard
import com.example.ui.theme.CharcoalBackground
import com.example.ui.theme.CharcoalCardBottom
import com.example.ui.theme.CharcoalCardTop
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeGlow
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextLightGrey
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnershipDetailScreen(
    partnership: Partnership?,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CharcoalBackground)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top App Bar with back button
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.testTag("btn_detail_back")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.cd_back),
                        tint = OrangePrimary
                    )
                }
            },
            title = {
                Text(
                    text = partnership?.name ?: "Partnership Detail",
                    color = TextWhite,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CharcoalBackground,
                titleContentColor = TextWhite
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (partnership == null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Partnership not found",
                    color = TextLightGrey,
                    fontSize = 16.sp
                )
            }
        } else {
            // Main Content Area with max width for tablets
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 760.dp)
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Banner: Partnership Overview Card
                val headerShape = RoundedCornerShape(20.dp)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(headerShape)
                        .border(1.dp, OrangeBorder, headerShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(CharcoalCardTop, CharcoalCardBottom)
                            )
                        )
                        .padding(20.dp)
                ) {
                    // Ambient glow
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(100.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(OrangeGlow, Color.Transparent),
                                    radius = 180f
                                )
                            )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PartnershipLogoBadge(
                            name = partnership.name,
                            logoUrl = partnership.logoUrl,
                            size = 64.dp
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = partnership.name,
                                color = TextWhite,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = partnership.category,
                                color = OrangeLight,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = partnership.description,
                                color = TextLightGrey,
                                fontSize = 13.5.sp,
                                lineHeight = 19.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Section Title
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Official Websites (${partnership.websites.size})",
                        color = OrangeLight,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )

                    Surface(
                        color = Color(0x1AFFFFFF),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = OrangePrimary,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Verified Links",
                                color = TextMuted,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Scrollable List of Websites
                if (partnership.websites.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No websites currently listed for this partnership.",
                            color = TextLightGrey,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("websites_list"),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(bottom = 32.dp, top = 2.dp)
                    ) {
                        items(
                            items = partnership.websites,
                            key = { it.id }
                        ) { website ->
                            WebsiteCard(website = website)
                        }
                    }
                }
            }
        }
    }
}
