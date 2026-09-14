package com.example

import com.example.data.repository.SamplePartnershipRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {

  private val repository = SamplePartnershipRepository()

  @Test
  fun testGetPartnershipsLoadsSampleData() = runBlocking {
    val list = repository.getPartnerships().first()
    assertEquals(4, list.size)
    assertEquals("Example Partnership 1", list[0].name)
    assertEquals(2, list[0].websites.size)
    assertEquals("Example Website 1", list[0].websites[0].name)
  }

  @Test
  fun testGetPartnershipById() = runBlocking {
    val partnership = repository.getPartnershipById("part_2")
    assertNotNull(partnership)
    assertEquals("Example Partnership 2", partnership?.name)
    assertEquals(2, partnership?.websites?.size)
  }

  @Test
  fun testSearchByPartnershipName() = runBlocking {
    val list = repository.getPartnerships().first()
    val query = "Partnership 3"
    val filtered = list.filter { it.name.contains(query, ignoreCase = true) }
    assertEquals(1, filtered.size)
    assertEquals("Example Partnership 3", filtered.first().name)
  }

  @Test
  fun testSearchByWebsiteDomain() = runBlocking {
    val list = repository.getPartnerships().first()
    val query = "megaways-club"
    val matching = list.filter { p ->
      p.websites.any { it.url.contains(query, ignoreCase = true) }
    }
    assertEquals(1, matching.size)
    assertEquals("Example Partnership 3", matching.first().name)
  }
}

