package com.kenilt.skeleton.extension

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MediatorLiveData
import com.jraska.livedata.test
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

/**
 * Created by thangnguyen on 2019-09-24.
 */
class MediatorLiveDataKtTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun fromSource_whenSourceChangeData() {
        val source = MediatorLiveData<Int>()
        val dest = MediatorLiveData<Int>()
        val observer = dest.test()
        dest.fromSource(source)
        observer.assertNoValue()
        source.value = 1
        observer.assertValue(1)
    }

    @Test
    fun fromSource_whenDestChangeData() {
        val source = MediatorLiveData<Int>()
        val dest = MediatorLiveData<Int>()
        val observer = source.test()
        dest.fromSource(source)
        observer.assertNoValue()
        dest.value = 1
        observer.assertNoValue()
    }

    @Test
    fun fromSource_withAction_whenSourceChangeData() {
        val source = MediatorLiveData<Int>()
        val dest = MediatorLiveData<Int>()
        val observer = dest.test()

        val action: (Int) -> Unit = mockk(relaxed = true)
        dest.fromSource(source, action)

        observer.assertNoValue()
        source.value = 1
        observer.assertValue(1)
        verify { action.invoke(1) }
    }

    @Test
    fun changeOneTimeFromSource_sourceChangeData() {
        val source = MediatorLiveData<Int>()
        val dest = MediatorLiveData<Int>()
        val observer = dest.test()

        val action: (Int) -> Unit = mockk(relaxed = true)
        dest.changeOneTimeFromSource(source, action)

        observer.assertNoValue()
        source.value = 1
        observer.assertValueHistory(1)
        verify { action.invoke(1) }

        source.value = 2
        observer.assertValueHistory(1)
    }

    @Test
    fun changeOneTimeFromSource_subscribe2Times() {
        val source = MediatorLiveData<Int>()
        val dest = MediatorLiveData<Int>()
        val observer = dest.test()

        val action: (Int) -> Unit = mockk(relaxed = true)
        dest.changeOneTimeFromSource(source, action)
        dest.changeOneTimeFromSource(source, action)

        observer.assertNoValue()
        source.value = 1
        observer.assertValueHistory(1)
        verify { action.invoke(1) }

        source.value = 2
        observer.assertValueHistory(1)
    }

    @Test
    fun watchChangeOneTime_sourceChangeData() {
        val source = MediatorLiveData<Int>()
        val dest = MediatorLiveData<Int>()
        val observer = dest.test()

        val action: (Int) -> Unit = mockk(relaxed = true)
        dest.watchChangeOneTime(source, action)

        observer.assertNoValue()
        source.value = 1
        observer.assertNoValue()
        verify { action.invoke(1) }

        source.value = 2
        observer.assertNoValue()
    }

    @Test
    fun watchChangeOneTime_subscribe2Times() {
        val source = MediatorLiveData<Int>()
        val dest = MediatorLiveData<Int>()
        val observer = dest.test()

        val action: (Int) -> Unit = mockk(relaxed = true)
        dest.watchChangeOneTime(source, action)
        dest.watchChangeOneTime(source, action)

        observer.assertNoValue()
        source.value = 1
        observer.assertNoValue()
        verify { action.invoke(1) }

        source.value = 2
        observer.assertNoValue()
    }

    @Test
    fun addMultipleSource_whenSourceChangeData() {
        val source1 = MediatorLiveData<Int>()
        val source2 = MediatorLiveData<String>()
        val dest = MediatorLiveData<Int>()
        val observer = dest.test()
        val action: () -> Unit = mockk(relaxed = true)

        dest.addSource(source1, source2, observer = action)

        observer.assertNoValue()
        source1.value = 1
        observer.assertNoValue()
        verify(exactly = 1) { action.invoke() }

        source2.value = "test"
        observer.assertNoValue()
        verify(exactly = 2) { action.invoke() }
    }
}
