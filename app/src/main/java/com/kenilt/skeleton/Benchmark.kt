package com.kenilt.skeleton

import com.kenilt.skeleton.utils.NumberUtil
import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

abstract class Benchmark(internal val name: String) {

    @Throws(Throwable::class)
    internal abstract fun run(iterations: Int)

    private fun time(): BigDecimal {
        try {
            var nextI = 1
            var i: Int
            var duration: Long
            do {
                i = nextI
                val start = System.nanoTime()
                run(i)
                duration = System.nanoTime() - start
                nextI = i shl 1 or 1
            } while (duration < 100000000 && nextI > 0)
            return BigDecimal(duration * 1000 / i).movePointLeft(3)
        } catch (e: Throwable) {
            throw RuntimeException(e)
        }
    }

    override fun toString(): String {
        return name + "\t" + time() + " ns"
    }

    companion object {

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val format = "#,### đ"
            val benchmarks = arrayOf(object : Benchmark("fastFormatPrice") {
                @Throws(Throwable::class)
                override fun run(iterations: Int) {
                    val random = Random()
                    for (i in 0 until iterations) {
                        val number = random.nextInt(10000) + 100000000
                        NumberUtil.fastFormatPrice(number)
                    }
                }
            }, object : Benchmark("DecimalFormat") {
                @Throws(Throwable::class)
                override fun run(iterations: Int) {
                    val formatter = DecimalFormat(format)
                    val random = Random()
                    for (i in 0 until iterations) {
                        formatter.format(random.nextInt(10000) + 100000000)
                    }
                }
            })

            Thread.sleep(1500)
            for (bm in benchmarks) {
                println(bm)
            }
        }
    }
}
