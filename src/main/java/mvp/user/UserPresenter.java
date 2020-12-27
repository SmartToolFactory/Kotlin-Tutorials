package mvp.user;

import java.util.List;

import chapter2OOP.User;
import mvp.BasePresenter;
import mvp.UserContract;

public class UserPresenter extends BasePresenter<UserContract.UserView, UserContract.UserInteractor>
        implements UserContract.UserPresenter, UserContract.UserOutput {

    public UserPresenter(UserContract.UserInteractor interactor) {
        super(interactor);
    }

    @Override
    public void fetchUsers() {
        interactor.fetchUsersFromNetwork();
    }

    @Override
    public void onUsersFetched(List<User> users) {
        view.displayUser(users);
    }

    @Override
    public void onErrorOccurred(String message) {
        view.displayError(message);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        interactor.onDestroy();
    }
}
