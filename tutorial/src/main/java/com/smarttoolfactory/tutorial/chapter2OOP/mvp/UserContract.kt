package com.smarttoolfactory.tutorial.chapter2OOP.mvp

import com.smarttoolfactory.tutorial.chapter2OOP.User


interface UserContract {

    interface UserView : BaseContract.IView {
        fun displayUser(users: List<User>)
        fun displayError(message: String?)
    }

    interface UserPresenter : BaseContract.IPresenter<UserView> {
        fun fetchUsers()
    }

    interface UserInteractor : BaseContract.IInteractor {
        fun fetchUsersFromNetwork()
    }

    interface UserOutput : BaseContract.IOutput {
        fun onUsersFetched(users: List<User>)
        fun onErrorOccurred(message: String)
    }
}