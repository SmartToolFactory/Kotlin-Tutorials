package mvp;

import static mvp.BaseContract.IInteractor;
import static mvp.BaseContract.IOutput;
import static mvp.BaseContract.IView;

public abstract class BasePresenter<V extends IView, I extends IInteractor>
        implements BaseContract.IPresenter<V>, IOutput {

    protected V view = null;

    protected I interactor;

    public BasePresenter(I interactor) {
        this.interactor = interactor;

        if (interactor instanceof BaseInteractor) {
            ((BaseInteractor<IOutput>) interactor).output = this;
        }

    }

    @Override
    public void attachView(V view) {
        this.view = view;
        System.out.println("😅 BasePresenter attachView() view: " + view);
    }

    @Override
    public void detachView() {
        System.out.println("😅 BasePresenter detachView()");
        view = null;
    }

    @Override
    public void onDestroy() {
        System.out.println("😅 BasePresenter onDestroy()");

        interactor.onDestroy();
        interactor = null;
    }

}
