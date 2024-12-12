package com.app.agrilink.domain.mapper

/**
 * Class for transform Local/Remote objects to domain object
 */
interface Transformable<T> {
    fun transform(): T
}