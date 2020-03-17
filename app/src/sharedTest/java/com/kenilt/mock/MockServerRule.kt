package com.kenilt.mock

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.rules.ExternalResource

class MockServerRule @JvmOverloads constructor(
        private val port: Int = 8080
) : ExternalResource() {

    lateinit var mockWebServer: MockWebServer
    lateinit var mockDispatcher: MockDispatcher

    @Before
    override fun before() {
        mockWebServer = MockWebServer()
        mockWebServer.start(port)
        mockDispatcher = MockDispatcher()
        mockWebServer.dispatcher = mockDispatcher
    }

    @After
    override fun after() {
        mockWebServer.shutdown()
    }

    fun setDispatcher(mockDispatcher: MockDispatcher) {
        this.mockDispatcher = mockDispatcher
        mockWebServer.dispatcher = mockDispatcher
    }
}
