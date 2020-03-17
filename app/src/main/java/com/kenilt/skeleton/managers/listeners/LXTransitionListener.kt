package com.kenilt.skeleton.managers.listeners

import androidx.transition.Transition

/**
 * Created by thangnguyen on 11/28/18.
 */
interface LXTransitionListener: Transition.TransitionListener {
    override fun onTransitionEnd(transition: Transition) {}

    override fun onTransitionResume(transition: Transition) {}

    override fun onTransitionPause(transition: Transition) {}

    override fun onTransitionCancel(transition: Transition) {}

    override fun onTransitionStart(transition: Transition) {}
}
