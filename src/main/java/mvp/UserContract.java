package mvp;

import java.util.List;

import chapter2OOP.User;

public interface UserContract {

    interface UserView extends BaseContract.IView {

        void displayUser(List<User> users);

        void displayError(String message);

    }

    interface UserPresenter extends BaseContract.IPresenter<UserView> {

        void fetchUsers();


    }


    interface UserInteractor extends BaseContract.IInteractor<UserOutput> {
        void fetchUsersFromNetwork();
    }

    interface UserOutput extends BaseContract.IOutput {

        void onUsersFetched(List<User> users);

        void onErrorOccurred(String message);
    }

}
