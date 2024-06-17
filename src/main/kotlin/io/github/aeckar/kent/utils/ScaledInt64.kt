package io.github.aeckar.kent.utils

import io.github.aeckar.kent.Int128

/**
 * Destructuring of a value into the closest scaled 64-bit integer to this and its scale.
 */
internal class ScaledInt64 {
    private val value: Long
    private val scale: Short

    /**
     * Does not preserve the state of mutable values.
     *
     * Some information may be lost during conversion.
     */
    constructor(i128: Int128) {
        val sign = i128.sign.toShort()
        var abs = i128.abs()
        var scale: Short = 0
        while (abs > Int.MAX_VALUE.toLong()) {
            abs /= Int128.TEN // Division is not cumulative
            ++scale
        }
        this.value = abs.toLong() * sign
        this.scale = (scale * sign).toShort()
    }

    // Accessed by BigInteger pseudo-constructor only
    constructor(value: Long, scale: Short) {
        this.value = value
        this.scale = scale
    }

    /**
     * The closest value n, for x=n*10^scale, where n <= [Int.MAX_VALUE].
     */
    operator fun component1() = value

    /**
     * See [Rational.scale][io.github.aeckar.kent.Rational.scale] for details.
     */
    operator fun component2() = scale

    companion object {
        // Accessed by string constructor of Rational only
        fun at(iterator: StringIndexIterator): ScaledInt64 {

        }
    }
}