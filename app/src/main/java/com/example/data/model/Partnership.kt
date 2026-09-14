package com.example.data.model

/**
 * Data model representing a partnership containing affiliated websites.
 *
 * Customize or replace this model with remote API response structures if needed.
 */
data class Partnership(
    val id: String,
    val name: String,
    val logoUrl: String? = null,
    val description: String,
    val websites: List<Website> = emptyList(),
    val category: String = "Gaming & Entertainment",
    val featured: Boolean = false
)
