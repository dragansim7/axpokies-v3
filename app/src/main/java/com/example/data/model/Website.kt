package com.example.data.model

/**
 * Data model representing an individual website under a partnership.
 *
 * Customize or replace this model with remote API response structures if needed.
 */
data class Website(
    val id: String,
    val name: String,
    val logoUrl: String? = null,
    val url: String,
    val description: String,
    val parentPartnershipId: String,
    val badge: String? = "Verified"
)
