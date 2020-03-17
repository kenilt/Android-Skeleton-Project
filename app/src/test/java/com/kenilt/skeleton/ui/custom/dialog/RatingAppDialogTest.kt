package com.kenilt.skeleton.ui.custom.dialog

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kenilt.skeleton.constant.TestConstant
import com.kenilt.skeleton.managers.helpers.ControllerHelper
import com.kenilt.skeleton.managers.prefs.LXConfig
import com.kenilt.skeleton.utils.InstantClock
import com.kenilt.skeleton.utils.TestableClock
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RatingAppDialogTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val clock = TestableClock()
    private val week = 7 * 24 * 60 * 60L

    @Before
    fun setUp() {
        InstantClock.clock = clock

        val config = mockk<LXConfig>()
        ControllerHelper.init(config)
        every { ControllerHelper.token() } returns TestConstant.VALID_TOKEN

        clock.currentMillis = 1_000_000_000
    }

    @Test
    fun isShouldShowDialog_whenShouldNotShow() {
        every { ControllerHelper.getShouldShowRating() } returns false
        every { ControllerHelper.getLastRatingTime() } returns 0
        every { ControllerHelper.getLastCrashedTime() } returns 0
        every { ControllerHelper.getLikeCount() } returns 3
        every { ControllerHelper.get5StarRatingCount() } returns 1
        every { ControllerHelper.getBadRatingCount() } returns 0
        every { ControllerHelper.getOpenAppCount() } returns 3

        assertFalse(RatingAppDialog.isShouldShowDialog())
    }

    @Test
    fun isShouldShowDialog_whenLastRatingTimeWithin2Weeks() {
        every { ControllerHelper.getShouldShowRating() } returns true
        every { ControllerHelper.getLastRatingTime() } returns 1_000_000_000 - 5 * 24 * 60 * 60
        every { ControllerHelper.getLastCrashedTime() } returns 0
        every { ControllerHelper.getLikeCount() } returns 3
        every { ControllerHelper.get5StarRatingCount() } returns 1
        every { ControllerHelper.getBadRatingCount() } returns 0
        every { ControllerHelper.getOpenAppCount() } returns 3

        assertFalse(RatingAppDialog.isShouldShowDialog())
    }

    @Test
    fun isShouldShowDialog_whenLastCrashedTimeWithin5Weeks() {
        every { ControllerHelper.getShouldShowRating() } returns true
        every { ControllerHelper.getLastRatingTime() } returns 1_000_000_000 - 2 * week
        every { ControllerHelper.getLastCrashedTime() } returns 1_000_000_000 - 3 * week
        every { ControllerHelper.getLikeCount() } returns 3
        every { ControllerHelper.get5StarRatingCount() } returns 1
        every { ControllerHelper.getBadRatingCount() } returns 0
        every { ControllerHelper.getOpenAppCount() } returns 3

        assertFalse(RatingAppDialog.isShouldShowDialog())
    }

    @Test
    fun isShouldShowDialog_whenLikeSmallerThan3Times() {
        every { ControllerHelper.getShouldShowRating() } returns true
        every { ControllerHelper.getLastRatingTime() } returns 0
        every { ControllerHelper.getLastCrashedTime() } returns 1_000_000_000 - 4 * week
        every { ControllerHelper.getLikeCount() } returns 2
        every { ControllerHelper.get5StarRatingCount() } returns 1
        every { ControllerHelper.getBadRatingCount() } returns 0
        every { ControllerHelper.getOpenAppCount() } returns 3

        assertFalse(RatingAppDialog.isShouldShowDialog())
    }

    @Test
    fun isShouldShowDialog_whenHasNo5StarsRating() {
        every { ControllerHelper.getShouldShowRating() } returns true
        every { ControllerHelper.getLastRatingTime() } returns 0
        every { ControllerHelper.getLastCrashedTime() } returns 0
        every { ControllerHelper.getLikeCount() } returns 3
        every { ControllerHelper.get5StarRatingCount() } returns 0
        every { ControllerHelper.getBadRatingCount() } returns 0
        every { ControllerHelper.getOpenAppCount() } returns 3

        assertFalse(RatingAppDialog.isShouldShowDialog())
    }

    @Test
    fun isShouldShowDialog_whenHasBadRating() {
        every { ControllerHelper.getShouldShowRating() } returns true
        every { ControllerHelper.getLastRatingTime() } returns 0
        every { ControllerHelper.getLastCrashedTime() } returns 0
        every { ControllerHelper.getLikeCount() } returns 3
        every { ControllerHelper.get5StarRatingCount() } returns 1
        every { ControllerHelper.getBadRatingCount() } returns 1
        every { ControllerHelper.getOpenAppCount() } returns 3

        assertFalse(RatingAppDialog.isShouldShowDialog())
    }

    @Test
    fun isShouldShowDialog_whenOpenAppSmallerThan3() {
        every { ControllerHelper.getShouldShowRating() } returns true
        every { ControllerHelper.getLastRatingTime() } returns 0
        every { ControllerHelper.getLastCrashedTime() } returns 0
        every { ControllerHelper.getLikeCount() } returns 3
        every { ControllerHelper.get5StarRatingCount() } returns 1
        every { ControllerHelper.getBadRatingCount() } returns 0
        every { ControllerHelper.getOpenAppCount() } returns 2

        assertFalse(RatingAppDialog.isShouldShowDialog())
    }

    @Test
    fun isShouldShowDialog_whenConditionPassed() {
        every { ControllerHelper.getShouldShowRating() } returns true
        every { ControllerHelper.getLastRatingTime() } returns 0
        every { ControllerHelper.getLastCrashedTime() } returns 0
        every { ControllerHelper.getLikeCount() } returns 3
        every { ControllerHelper.get5StarRatingCount() } returns 1
        every { ControllerHelper.getBadRatingCount() } returns 0
        every { ControllerHelper.getOpenAppCount() } returns 3

        assertTrue(RatingAppDialog.isShouldShowDialog())
    }
}
