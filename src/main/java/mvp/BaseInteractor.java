package mvp;

public abstract class BaseInteractor<O extends BaseContract.IOutput> implements BaseContract.IInteractor {

    protected O output;

    public void setOutput(O output) {
        this.output = output;
    }

    @Override
    public void onDestroy() {
        System.out.println("🍒 BaseInteractor onDestroy()");
        output = null;
    }
}
