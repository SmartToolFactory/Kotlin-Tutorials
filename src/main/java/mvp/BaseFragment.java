package mvp;

public class BaseFragment<V extends BaseContract.IView, P extends BaseContract.IPresenter<V>> extends Fragment {

    public P presenter = null;

    public void setPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreateView() {
        super.onCreateView();
        presenter.attachView((V) this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }


}


/**
 * Mock fragment class to mock Android's Fragment with MVP
 */
class Fragment {

    public void onCreate() {
        System.out.println("🔥 Fragment() onCreate()");
    }

    public void onCreateView() {
        System.out.println("🌽 Fragment() onCreateView()");
    }

    public void onDestroyView() {
        System.out.println("🎃 Fragment() onDestroyView()");
    }
    public void onDestroy() {
        System.out.println("🍺 Fragment() onDestroy()");

    }
}
