package chapter2OOP.mvp

import chapter2OOP.mvp.user.UserFragment


fun main() {
    val userFragment = UserFragment()
    userFragment.onCreate()
    Thread.sleep(400)

    userFragment.onCreateView()
    Thread.sleep(400)

    userFragment.onDestroyView()
    Thread.sleep(400)
    userFragment.onDestroy()

    println("PROGRAM FINISHED")
}
