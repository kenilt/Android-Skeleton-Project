package com.kenilt.skeleton

import com.kenilt.skeleton.utils.NumberUtilTest
import com.kenilt.skeleton.utils.VNCharacterUtilsTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        NumberUtilTest::class,
        VNCharacterUtilsTest::class
)
class JunitTestSuite
