package com.marleyspoon.recipes.model

import com.contentful.java.cda.CDAAsset
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.CDAResource
import com.contentful.java.cda.LocalizedResource
import com.contentful.java.cda.image.ImageOption

data class Recipe(
    val title: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val chef: String? = null,
    val tags: List<String>? = null
) {

    companion object {
        const val CONTENT_TYPE = "recipe"
        private const val TITLE = "title"
        private const val DESCRIPTION = "description"
        private const val NAME = "name"
        private const val CHEF = "chef"
        private const val TAGS = "tags"
        private const val PHOTO = "photo"

        fun createRecipeFromCDAResource(
            cdaResource: CDAResource
        ): Recipe {
            return Recipe(
                title = (cdaResource as LocalizedResource).getField<String>(TITLE),
                description = cdaResource.getField<String>(DESCRIPTION),
                chef = cdaResource.getField<CDAEntry>(CHEF)?.getField<String>(NAME),
                tags = cdaResource.getField<ArrayList<CDAEntry>>(TAGS)
                    ?.map { tag -> tag.getField<String>(NAME) },
                imageUrl = cdaResource.getField<CDAAsset>(PHOTO)
                    ?.urlForImageWith(ImageOption.https())
            )
        }
    }
}