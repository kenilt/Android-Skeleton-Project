package com.kenilt.skeleton.ui.feature.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by thangnguyen on 2019-05-21.
 */
class MainViewModel: ViewModel() {
    var isHideBubble: MutableLiveData<Boolean> = MutableLiveData()
    var activeIndex: MutableLiveData<Int> = MutableLiveData()
}
