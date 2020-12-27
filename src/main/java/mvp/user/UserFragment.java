package mvp.user;

import java.util.List;

import chapter2OOP.User;
import mvp.BaseFragment;
import mvp.UserContract;

public class UserFragment extends BaseFragment<UserContract.UserView, UserContract.UserPresenter>
        implements UserContract.UserView {

    @Override
    public void onCreate() {
        super.onCreate();
        presenter = InitUserFragment.initUserModule();
    }

    @Override
    public void onCreateView() {
        super.onCreateView();
        presenter.fetchUsers();
    }

    @Override
    public void displayUser(List<User> users) {
        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);
            System.out.println("UserFragment displayUser() USER: " + user.getFullName());
        }

    }

    @Override
    public void displayError(String message) {

    }

    static class InitUserFragment {

        public static UserContract.UserPresenter initUserModule() {
            UserContract.UserInteractor userInteractor = new UserInteractor();
            return new UserPresenter(userInteractor);

        }

    }
}
