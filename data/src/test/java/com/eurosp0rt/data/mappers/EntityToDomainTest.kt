package com.eurosp0rt.data.mappers

import com.eurosp0rt.data.fakePostEntityObject
import com.eurosp0rt.data.fakeSportEntityObject
import com.eurosp0rt.domain.models.Post
import com.eurosp0rt.domain.models.PostType
import com.eurosp0rt.domain.models.Sport
import org.junit.Test
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat


class EntityToDomainTest {

    @Test
    fun `should transform post entity into post domain with the same values`() {
        val postEntity = fakePostEntityObject(1, PostType.VIDEO)
        val postDomain = postEntity.toDomainModel()
        assertThat(postDomain, `is`(instanceOf(Post::class.java)))
        assertThat(postDomain.id, `is`(postEntity.id))
        assertThat(postDomain.postType, `is`(postEntity.postType))
        assertThat(postDomain.sport.id, `is`(postEntity.sport.id))
    }

    @Test
    fun `should transform sport entity into sport domain with the same values`() {
        val sportEntity = fakeSportEntityObject()
        val sportDomain = sportEntity.toDomainModel()
        assertThat(sportDomain, `is`(instanceOf(Sport::class.java)))
        assertThat(sportDomain.id, `is`(sportEntity.id))
        assertThat(sportDomain.name, `is`(sportEntity.name))
    }
}