public class LoginPersenter implements ILoginPresenter{

    private ILoginView loginView;
    private UserModel mUser;


    public LoginPersenter(ILoginView loginView) {
        this.loginView = loginView;
        initUser();
    }

    private void initUser(){
        mUser = new UserModel(loginView.getUsername(),loginView.getPassword());
    }

    @Override
    public void Login(String username, String password) {
        loginView.showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loginView.hideProgress();
                int code = mUser.checkUserValidity(loginView.getUsername(), loginView.getPassword());
                if (code == -1) {
                    loginView.setPasswordError();
                } else if (code == 0) {
                    loginView.loginSuccess();
                }
            }
        },2000);
    }

}