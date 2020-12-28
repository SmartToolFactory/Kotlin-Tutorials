package chapter2OOP.mvp

interface BaseContract {

    interface IView
    interface IPresenter<V : IView> {

        fun attachOutput()
        fun attachView(view: V)
        fun detachView()
        fun onDestroy()
    }

    interface IInteractor {
        fun onDestroy()
    }

    interface IOutput
}