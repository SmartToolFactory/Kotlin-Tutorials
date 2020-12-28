package chapter2OOP.mvp.user

import chapter2OOP.User
import chapter2OOP.mvp.BaseFragment
import chapter2OOP.mvp.BaseInteractor
import chapter2OOP.mvp.BasePresenter
import chapter2OOP.mvp.UserContract
import chapter2OOP.mvp.UserContract.UserView
import java.util.*

class UserFragment : BaseFragment<UserView, UserContract.UserPresenter>(), UserView {

    init {
        presenter = InitUserFragment.initUserModule()
    }

    override fun onCreate() {
        super.onCreate()
        println("😍 UserFragment onCreate()")
    }

    override fun onCreateView() {
        super.onCreateView()
        presenter?.fetchUsers()
    }

    override fun displayUser(users: List<User>) {
        for (i in users.indices) {
            val user = users[i]
            println("🔥🔥 UserFragment displayUser() USER: " + user.fullName)
        }
    }

    override fun displayError(message: String?) {}

    internal object InitUserFragment {
        fun initUserModule(): UserContract.UserPresenter {
            val userInteractor: UserContract.UserInteractor = UserInteractor()
            return UserPresenter(userInteractor)
        }
    }
}


class UserPresenter(interactor: UserContract.UserInteractor) :
    BasePresenter<UserView, UserContract.UserInteractor>(interactor), UserContract.UserPresenter,
    UserContract.UserOutput {

    override fun fetchUsers() {
        interactor?.fetchUsersFromNetwork()
    }

    override fun onUsersFetched(users: List<User>) {
        view?.displayUser(users)
    }

    override fun onErrorOccurred(message: String) {
        view?.displayError(message)
    }
}

class UserInteractor : BaseInteractor<UserContract.UserOutput>(), UserContract.UserInteractor {

    override fun fetchUsersFromNetwork() {
        val userList = GetUsers.users
        output?.onUsersFetched(userList)
        println("🤩 UserInteractor fetchUsersFromNetwork() userList: $userList")
    }

    internal object GetUsers {
        val users: List<User>
            get() {
                val userList: MutableList<User> = ArrayList()
                val user = User("John", "Smith")
                userList.add(user)
                return userList
            }
    }
}