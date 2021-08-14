package com.smarttoolfactory.tutorial.chapter2OOP

import java.util.LinkedHashMap
import kotlin.reflect.KProperty

fun main() {

    val isSelected: MutableState<Boolean> = remember { mutableStateOf(true) }
    isSelected.value = false

    var selected by remember { mutableStateOf(false) }
    selected = false
}


/*
 *  MutableState
 */
interface MutableState<T> : State<T> {
    override var value: T
}

// Delegation Functions for setting and getting value
operator fun <T> State<T>.getValue(thisObj: Any?, property: KProperty<*>): T = value

operator fun <T> MutableState<T>.setValue(thisObj: Any?, property: KProperty<*>, value: T) {
    this.value = value
}

interface State<out T> {
    val value: T
}

class MutableStateImpl<T>(value: T) : MutableState<T> {
    override var value: T = value
}

fun <T> mutableStateOf(value: T): MutableState<T> = MutableStateImpl(value)

/*
 *  Remember
 */

inline fun <T> remember(calculation: () -> T): T {
    return calculation()
}