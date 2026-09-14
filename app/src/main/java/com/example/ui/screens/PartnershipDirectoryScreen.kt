package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.PartnershipCard
import com.example.ui.components.SearchBox
import com.example.ui.theme.CharcoalBackground
import com.example.ui.theme.CharcoalSurface
import com.example.ui.theme.DarkTextOnOrange
import com.example.ui.theme.OrangeBorder
import com.example.ui.theme.OrangeLight
import com.example.ui.theme.OrangePrimary
import com.example.ui.theme.TextLightGrey
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.viewmodel.PartnershipUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartnershipDirectoryScreen(
    uiState: PartnershipUiState,
    onSearchQueryChange: (String) -> Unit,
    onClearSearch: () -> Unit,
    onPartnershipClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(CharcoalBackground)
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // App Logo
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, OrangeBorder, RoundedCornerShape(10.dp))
                            .background(CharcoalSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_axpokies_logo),
                            contentDescription = stringResource(R.string.cd_app_logo),
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Column {
                        Text(
                            text = stringResource(R.string.title_partnerships),
                            color = TextWhite,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.app_name),
                            color = OrangeLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.8.sp
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = CharcoalBackground,
                titleContentColor = TextWhite
            ),
            modifier = Modifier.fillMaxWidth()
        )

        // Centered Content Column for Phone & Tablet responsiveness
        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 760.dp)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(6.dp))

            // Prominent Search Box
            SearchBox(
                query = uiState.searchQuery,
                onQueryChange = onSearchQueryChange,
                onClear = onClearSearch,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Stats / Directory header indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val resultsCount = uiState.searchResults.size
                val statusText = if (uiState.searchQuery.isBlank()) {
                    "All Partners ($resultsCount)"
                } else {
                    "Found $resultsCount matching partners"
                }

                Text(
                    text = statusText,
                    color = OrangeLight,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )

                if (uiState.searchQuery.isBlank()) {
                    Text(
                        text = "${uiState.totalWebsitesCount} Websites Active",
                        color = TextMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Content Area
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = OrangePrimary)
                }
            } else if (uiState.searchResults.isEmpty()) {
                // Empty state
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = CharcoalSurface,
                            border = androidx.compose.foundation.BorderStroke(1.dp, OrangeBorder),
                            modifier = Modifier.size(72.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.SearchOff,
                                    contentDescription = null,
                                    tint = OrangePrimary,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = stringResource(R.string.empty_search_message),
                            color = TextWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Check the spelling or try searching with different keywords.",
                            color = TextLightGrey,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = onClearSearch,
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = OrangePrimary,
                                contentColor = DarkTextOnOrange
                            ),
                            modifier = Modifier.testTag("btn_reset_search")
                        ) {
                            Text(
                                text = stringResource(R.string.btn_clear_search),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            } else {
                // Vertically scrollable list of partnership banners
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("partnerships_list"),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 32.dp, top = 4.dp)
                ) {
                    items(
                        items = uiState.searchResults,
                        key = { it.partnership.id }
                    ) { result ->
                        PartnershipCard(
                            partnership = result.partnership,
                            matchedWebsites = result.matchedWebsites,
                            isFiltered = uiState.searchQuery.isNotBlank(),
                            onClick = { onPartnershipClick(result.partnership.id) }
                        )
                    }
                }
            }
        }
    }
}
