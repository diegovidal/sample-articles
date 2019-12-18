package com.dvidal.samplearticles.model

/**
 * @author diegovidal on 2019-12-18.
 */
data class Article(
    private val sku: String,
    private val title: String,
    private val description: String?,
    private val imageUrl: String
)