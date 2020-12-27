package mvp;

public interface BaseContract {

    interface IView {

    }

    interface IPresenter<V extends IView> {

        void attachView(V view);

        void detachView();

        void onDestroy();
    }


    interface IInteractor {
        void onDestroy();
    }

    interface IOutput {

    }


}
