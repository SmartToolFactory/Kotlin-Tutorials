package mvp;

public abstract class BasePresenter<V extends BaseContract.IView, I extends BaseContract.IInteractor>
        implements BaseContract.IPresenter<V> {

    protected V view = null;

    protected I interactor;

    public BasePresenter(I interactor) {
        this.interactor = interactor;

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
