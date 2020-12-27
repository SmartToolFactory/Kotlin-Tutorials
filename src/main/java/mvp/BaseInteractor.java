package mvp;

public abstract class BaseInteractor<O extends BaseContract.IOutput> implements BaseContract.IInteractor<O> {

    protected O output;

    public BaseInteractor() {

    }

    @Override
    public void onDestroy() {
        System.out.println("🍒 BaseInteractor onDestroy()");
        output = null;
    }
}
