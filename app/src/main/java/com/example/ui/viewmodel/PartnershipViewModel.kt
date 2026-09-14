package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Partnership
import com.example.data.model.Website
import com.example.data.repository.PartnershipRepository
import com.example.data.repository.SamplePartnershipRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class PartnershipSearchResult(
    val partnership: Partnership,
    val matchedWebsites: List<Website>,
    val matchedPartnershipDirectly: Boolean
)

data class PartnershipUiState(
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val searchResults: List<PartnershipSearchResult> = emptyList(),
    val totalPartnershipsCount: Int = 0,
    val totalWebsitesCount: Int = 0,
    val selectedPartnership: Partnership? = null
)

class PartnershipViewModel(
    private val repository: PartnershipRepository = SamplePartnershipRepository()
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedPartnershipId = MutableStateFlow<String?>(null)
    val selectedPartnershipId: StateFlow<String?> = _selectedPartnershipId.asStateFlow()

    private val _partnerships = repository.getPartnerships()

    val uiState: StateFlow<PartnershipUiState> = combine(
        _partnerships,
        _searchQuery,
        _selectedPartnershipId
    ) { partnerships, query, selectedId ->
        val trimmedQuery = query.trim()
        val results = if (trimmedQuery.isEmpty()) {
            partnerships.map { partnership ->
                PartnershipSearchResult(
                    partnership = partnership,
                    matchedWebsites = partnership.websites,
                    matchedPartnershipDirectly = true
                )
            }
        } else {
            partnerships.mapNotNull { partnership ->
                val nameMatches = partnership.name.contains(trimmedQuery, ignoreCase = true)
                val descMatches = partnership.description.contains(trimmedQuery, ignoreCase = true)
                val directMatch = nameMatches || descMatches

                val matchedWebsites = partnership.websites.filter { website ->
                    website.name.contains(trimmedQuery, ignoreCase = true) ||
                    website.url.contains(trimmedQuery, ignoreCase = true) ||
                    website.description.contains(trimmedQuery, ignoreCase = true)
                }

                if (directMatch || matchedWebsites.isNotEmpty()) {
                    PartnershipSearchResult(
                        partnership = partnership,
                        matchedWebsites = matchedWebsites,
                        matchedPartnershipDirectly = directMatch
                    )
                } else {
                    null
                }
            }
        }

        val totalWebsites = partnerships.sumOf { it.websites.size }
        val selected = partnerships.find { it.id == selectedId }

        PartnershipUiState(
            isLoading = false,
            searchQuery = query,
            searchResults = results,
            totalPartnershipsCount = partnerships.size,
            totalWebsitesCount = totalWebsites,
            selectedPartnership = selected
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PartnershipUiState(isLoading = true)
    )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun selectPartnership(id: String) {
        _selectedPartnershipId.value = id
    }

    fun clearSelectedPartnership() {
        _selectedPartnershipId.value = null
    }
}
