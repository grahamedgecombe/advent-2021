package com.grahamedgecombe.advent2021.util

data class Matrix3x3(
    val m11: Int,
    val m12: Int,
    val m13: Int,
    val m21: Int,
    val m22: Int,
    val m23: Int,
    val m31: Int,
    val m32: Int,
    val m33: Int,
) {
    operator fun times(v: Vector3): Vector3 {
        return Vector3(
            v.x * m11 + v.y * m12 + v.z * m13,
            v.x * m21 + v.y * m22 + v.z * m23,
            v.x * m31 + v.y * m32 + v.z * m33,
        )
    }

    companion object {
        val ROTATE_X_90 = Matrix3x3(
            1, 0, 0,
            0, 0, -1,
            0, 1, 0,
        )

        val ROTATE_Y_90 = Matrix3x3(
            0, 0, 1,
            0, 1, 0,
            -1, 0, 0,
        )

        val ROTATE_Z_90 = Matrix3x3(
            0, -1, 0,
            1, 0, 0,
            0, 0, 1,
        )
    }
}
