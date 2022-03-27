package com.eurosp0rt.data.mappers

import com.eurosp0rt.data.*
import com.eurosp0rt.data.database.entities.PostEntity
import com.eurosp0rt.data.database.entities.SportEntity
import com.eurosp0rt.domain.models.PostType
import org.junit.Test
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat


class ResponseToEntityTest {

    @Test
    fun `should transform video response into post entity with the same values`() {
        val videoResponse = fakeVideoResponseObject(1)
        val postEntity = videoResponse.toEntityModel()
        assertThat(postEntity, `is`(instanceOf(PostEntity::class.java)))
        assertThat(postEntity.id, `is`(videoResponse.id))
        assertThat(postEntity.postType, `is`(PostType.VIDEO))
        assertThat(postEntity.sport.id, `is`(videoResponse.sport.id))
    }

    @Test
    fun `should transform story response into post entity with the same values`() {
        val storyResponse = fakeStoryResponseObject(1)
        val postEntity = storyResponse.toEntityModel()
        assertThat(postEntity, `is`(instanceOf(PostEntity::class.java)))
        assertThat(postEntity.id, `is`(storyResponse.id))
        assertThat(postEntity.postType, `is`(PostType.STORY))
        assertThat(postEntity.sport.id, `is`(storyResponse.sport.id))
    }

    @Test
    fun `should transform sport response into sport entity with the same values`() {
        val sportResponse = fakeSportResponseObject()
        val sportEntity = sportResponse.toEntityModel()
        assertThat(sportEntity, `is`(instanceOf(SportEntity::class.java)))
        assertThat(sportEntity.id, `is`(sportResponse.id))
        assertThat(sportEntity.name, `is`(sportResponse.name))
    }
}