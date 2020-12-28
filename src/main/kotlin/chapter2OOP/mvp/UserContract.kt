package chapter2OOP.mvp

import chapter2OOP.User
import chapter2OOP.mvp.BaseContract.*

interface UserContract {

    interface UserView : IView {
        fun displayUser(users: List<User>)
        fun displayError(message: String?)
    }

    interface UserPresenter : IPresenter<UserView> {
        fun fetchUsers()
    }

    interface UserInteractor : IInteractor {
        fun fetchUsersFromNetwork()
    }

    interface UserOutput : IOutput {
        fun onUsersFetched(users: List<User>)
        fun onErrorOccurred(message: String)
    }
}