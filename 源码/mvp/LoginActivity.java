public class LoginActivity extends AppCompatActivity implements ILoginView,View.OnClickListener{

    private EditText usernameEdit,passwrodEdit;

    private Button loginButton;

    ProgressDialog pd;
    LoginPersenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvplogin);

        pd = new ProgressDialog(this);

        usernameEdit = (EditText) findViewById(R.id.et_username);
        passwrodEdit = (EditText) findViewById(R.id.et_username);
        loginButton = (Button) findViewById(R.id.bt_login);

        loginButton.setOnClickListener(this);
        //初始化
        loginPresenter = new LoginPersenter(this);

        //Click方法中的调用
        loginPresenter.Login(usernameEdit.getText().toString(),passwrodEdit.getText().toString());
    }

    @Override
    public void showProgress() {
        pd.show();
    }

    @Override
    public void hideProgress() {
        pd.cancel();
    }

    @Override
    public void setPasswordError() {
        passwrodEdit.setError("passwrod error");
    }

    @Override
    public String getUsername() {
        return usernameEdit.getText().toString();
    }

    @Override
    public String getPassword() {
        return passwrodEdit.getText().toString();
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(this, "login success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_login:

                break;
        }
    }