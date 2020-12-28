package chapter2OOP.mvp

import chapter2OOP.mvp.BaseContract.*

open class BaseFragment<V : IView, P : IPresenter<V>> : Fragment() {

    var presenter: P? = null

    override fun onCreate() {
        super.onCreate()
        presenter?.attachOutput()
    }

    override fun onCreateView() {
        super.onCreateView()
        println("😍 BaseFragment onCreateView()")
        presenter?.attachView(this as V)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        println("😍 BaseFragment onDestroyView()")
        presenter?.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        println("😍 BaseFragment onDestroy()")
        presenter?.onDestroy()
    }
}

abstract class BasePresenter<V : IView, I : IInteractor>(interactor: I) : IPresenter<V>,
    IOutput {

    protected var view: V? = null

    protected var interactor: I?

    init {
        this.interactor = interactor
    }

    override fun attachOutput() {
        if (interactor is BaseInteractor<*>) {
            (interactor as? BaseInteractor<IOutput>)?.output = this
        }
    }

    override fun attachView(view: V) {
        this.view = view
        println("😅 BasePresenter attachView() view: $view")
    }

    override fun detachView() {
        println("😅 BasePresenter detachView()")
        view = null
    }

    override fun onDestroy() {
        println("😅 BasePresenter onDestroy()")
        interactor?.onDestroy()
        interactor = null
    }


}

abstract class BaseInteractor<O : IOutput> : IInteractor {
    var output: O? = null

    override fun onDestroy() {
        println("🍒 BaseInteractor onDestroy()")
        output = null
    }
}

/**
 * Mock fragment class to mock Android's Fragment with MVP
 */
open class Fragment {
    open fun onCreate() {
        println("🔥 Fragment() onCreate()")
    }

    open fun onCreateView() {
        println("🌽 Fragment() onCreateView()")
    }

    open fun onDestroyView() {
        println("🎃 Fragment() onDestroyView()")
    }

    open fun onDestroy() {
        println("🍺 Fragment() onDestroy()")
    }
}