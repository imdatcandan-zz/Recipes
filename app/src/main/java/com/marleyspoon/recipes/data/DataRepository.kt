package com.marleyspoon.recipes.data

import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.CDAResource
import com.marleyspoon.recipes.model.Recipe

class DataRepository {

    val cdaClient: CDAClient by lazy { createCdaClient() }

    private fun createCdaClient(): CDAClient {
        return CDAClient.builder()
            .setSpace(SPACE_ID)
            .setToken(ACCESS_TOKEN)
            .build()
    }

    fun fetchRecipeList(): MutableList<CDAResource> {
        return cdaClient.fetch(CDAEntry::class.java)
            .withContentType(Recipe.CONTENT_TYPE)
            .all()
            .items()
    }

    companion object {
        private const val SPACE_ID = "kk2bw5ojx476"
        private const val ACCESS_TOKEN =
            "7ac531648a1b5e1dab6c18b0979f822a5aad0fe5f1109829b8a197eb2be4b84c"
    }
}