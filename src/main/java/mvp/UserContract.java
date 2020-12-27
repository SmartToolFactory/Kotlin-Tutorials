package mvp;

import java.util.List;

import chapter2OOP.User;
import mvp.BaseContract.IInteractor;
import mvp.BaseContract.IPresenter;

import static mvp.BaseContract.IOutput;
import static mvp.BaseContract.IView;

public interface UserContract {

    interface UserView extends IView {
        void displayUser(List<User> users);

        void displayError(String message);
    }

    interface UserPresenter extends IPresenter<UserView> {
        void fetchUsers();
    }

    interface UserInteractor extends IInteractor {
        void fetchUsersFromNetwork();
    }

    interface UserOutput extends IOutput {
        void onUsersFetched(List<User> users);

        void onErrorOccurred(String message);
    }

}
