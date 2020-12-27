package mvp;

import mvp.user.UserFragment;

public class UserFragmentMain {

    public static void main(String[] args) throws InterruptedException {
        UserFragment userFragment = new UserFragment();

        userFragment.onCreate();
        Thread.sleep(400);
        userFragment.onCreateView();
        Thread.sleep(400);
        userFragment.onDestroyView();
        Thread.sleep(400);
        userFragment.onDestroy();

        System.out.println("PROGRAM FINISHED");
    }
}
