package com.kenilt.skeleton.utils

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by thangnguyen on 3/4/19.
 */
class StatusCodeUtilTest {
    @Test
    fun isSuccess_Code200_True() {
        val res = StatusCodeUtil.isSuccess(200)
        assertEquals(true, res)
    }

    @Test
    fun isSuccess_Code299_True() {
        val res = StatusCodeUtil.isSuccess(299)
        assertEquals(true, res)
    }

    @Test
    fun isSuccess_Code199_False() {
        val res = StatusCodeUtil.isSuccess(199)
        assertEquals(false, res)
    }

    @Test
    fun isSuccess_Code300_False() {
        val res = StatusCodeUtil.isSuccess(300)
        assertEquals(false, res)
    }

    @Test
    fun isInformation_Code100_True() {
        val res = StatusCodeUtil.isInformation(100)
        assertEquals(true, res)
    }

    @Test
    fun isInformation_Code199_True() {
        val res = StatusCodeUtil.isInformation(199)
        assertEquals(true, res)
    }

    @Test
    fun isInformation_Code99_False() {
        val res = StatusCodeUtil.isInformation(99)
        assertEquals(false, res)
    }

    @Test
    fun isInformation_Code200_False() {
        val res = StatusCodeUtil.isInformation(200)
        assertEquals(false, res)
    }

    @Test
    fun isServerError_Code500_True() {
        val res = StatusCodeUtil.isServerError(500)
        assertEquals(true, res)
    }

    @Test
    fun isServerError_Code599_True() {
        val res = StatusCodeUtil.isServerError(599)
        assertEquals(true, res)
    }

    @Test
    fun isServerError_Code499_False() {
        val res = StatusCodeUtil.isServerError(499)
        assertEquals(false, res)
    }

    @Test
    fun isServerError_Code600_False() {
        val res = StatusCodeUtil.isServerError(600)
        assertEquals(false, res)
    }

    @Test
    fun isClientError_Code400_True() {
        val res = StatusCodeUtil.isClientError(400)
        assertEquals(true, res)
    }

    @Test
    fun isClientError_Code499_True() {
        val res = StatusCodeUtil.isClientError(499)
        assertEquals(true, res)
    }

    @Test
    fun isClientError_Code399_False() {
        val res = StatusCodeUtil.isClientError(399)
        assertEquals(false, res)
    }

    @Test
    fun isClientError_Code500_False() {
        val res = StatusCodeUtil.isClientError(500)
        assertEquals(false, res)
    }
}
