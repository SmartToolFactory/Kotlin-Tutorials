package mvp.user;

import java.util.ArrayList;
import java.util.List;

import chapter2OOP.User;
import mvp.BaseInteractor;
import mvp.UserContract;

public class UserInteractor extends BaseInteractor<UserContract.UserOutput> implements UserContract.UserInteractor {

    @Override
    public void fetchUsersFromNetwork() {
        List<User> userList = GetUsers.getUsers();
        output.onUsersFetched(userList);
    }

    @Override
    public void setOutput(UserContract.UserOutput ouput) {
        this.output = ouput;
    }

    static class GetUsers {

        static List<User> getUsers() {

            List<User> userList = new ArrayList();

            User user = new User("John", "Smith");
            userList.add(user);

            return userList;
        }

    }
}

