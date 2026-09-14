package com.example.data.repository

import com.example.data.model.Partnership
import com.example.data.model.Website
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * ===========================================================================================
 * REPOSITORY CONFIGURATION & DATA SOURCE
 * ===========================================================================================
 * TO REPLACE SAMPLE DATA OR CONNECT TO A BACKEND API:
 * 1. Modify the sample list below in [SamplePartnershipRepository.SAMPLE_PARTNERSHIPS], or
 * 2. Implement [PartnershipRepository] with Retrofit / Room / Firebase / Ktor calls.
 * ===========================================================================================
 */
interface PartnershipRepository {
    fun getPartnerships(): Flow<List<Partnership>>
    suspend fun getPartnershipById(id: String): Partnership?
}

class SamplePartnershipRepository : PartnershipRepository {

    companion object {
        /**
         * EDIT HERE: Replace or add partnership names, logos, descriptions, and website URLs.
         */
        val SAMPLE_PARTNERSHIPS = listOf(
            Partnership(
                id = "part_1",
                name = "Example Partnership 1",
                logoUrl = null,
                description = "Premier digital entertainment & gaming group providing top-tier slot adventures, table tournaments, and premium rewards.",
                category = "Premium Slots & Casino",
                featured = true,
                websites = listOf(
                    Website(
                        id = "web_1",
                        name = "Example Website 1",
                        logoUrl = null,
                        url = "https://example.com/slots-hub",
                        description = "State-of-the-art online pokies and progressive jackpot spins with fast payouts.",
                        parentPartnershipId = "part_1",
                        badge = "Top Rated"
                    ),
                    Website(
                        id = "web_2",
                        name = "Example Website 2",
                        logoUrl = null,
                        url = "https://example.com/classic-pokies",
                        description = "Classic Vegas-style 3-reel and 5-reel pokies with daily spin bonuses.",
                        parentPartnershipId = "part_1",
                        badge = "Instant Play"
                    )
                )
            ),
            Partnership(
                id = "part_2",
                name = "Example Partnership 2",
                logoUrl = null,
                description = "Global high-roller gaming network featuring live dealer tables, VIP cash drops, and modern mobile pokies.",
                category = "VIP Gaming Lounge",
                featured = false,
                websites = listOf(
                    Website(
                        id = "web_3",
                        name = "Example Website 3",
                        logoUrl = null,
                        url = "https://example.com/live-tables",
                        description = "Immersive real-time blackjack, roulette, and baccarat streamed in HD.",
                        parentPartnershipId = "part_2",
                        badge = "Live HD"
                    ),
                    Website(
                        id = "web_4",
                        name = "Example Website 4",
                        logoUrl = null,
                        url = "https://example.com/bonus-hub",
                        description = "Exclusive cashback rewards, deposit boosters, and weekly tournament leaderboards.",
                        parentPartnershipId = "part_2",
                        badge = "Cashback"
                    )
                )
            ),
            Partnership(
                id = "part_3",
                name = "Example Partnership 3",
                logoUrl = null,
                description = "Mobile-first gaming studio specialized in fast lightning tournaments, drop & wins, and community jackpots.",
                category = "Lightning Studio",
                featured = false,
                websites = listOf(
                    Website(
                        id = "web_5",
                        name = "Example Website 5",
                        logoUrl = null,
                        url = "https://example.com/megaways-club",
                        description = "High volatility Megaways pokies offering up to 117,649 ways to hit massive multipliers.",
                        parentPartnershipId = "part_3",
                        badge = "Megaways"
                    )
                )
            ),
            Partnership(
                id = "part_4",
                name = "Example Partnership 4",
                logoUrl = null,
                description = "Next-generation crypto & web3-ready arcade gaming with provably fair RNG mechanisms.",
                category = "Crypto & Arcade",
                featured = false,
                websites = listOf(
                    Website(
                        id = "web_6",
                        name = "Example Website 6",
                        logoUrl = null,
                        url = "https://example.com/crypto-spins",
                        description = "Ultra-fast zero-fee deposits, instant token withdrawals, and provably fair spins.",
                        parentPartnershipId = "part_4",
                        badge = "Crypto Ready"
                    )
                )
            )
        )
    }

    private val _partnershipsFlow = MutableStateFlow(SAMPLE_PARTNERSHIPS)

    override fun getPartnerships(): Flow<List<Partnership>> = _partnershipsFlow.asStateFlow()

    override suspend fun getPartnershipById(id: String): Partnership? {
        return _partnershipsFlow.value.find { it.id == id }
    }
}
