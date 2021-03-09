package com.example.tallerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int RC_SIGN_IN = 1;
    String TAG;
    Button btnIniciarSesion;
    SignInButton signInGoogle;
    EditText etUserName, etPasswd;
    Switch swAuth;
    AdView mAdView;
    TextView errorText;
    EditText edHello;
    Boolean accessAllowed = false;
    Object users;
    String usersArray;
    GoogleSignInClient mGoogleSignInClient;

    RequestQueue requestQueue;
    private String url = "https://run.mocky.io/v3/1aaf3907-9707-4ddf-94d5-dc1d24afb383";

    MyService mService;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Toast.makeText(this,"OnCreate", Toast.LENGTH_LONG).show();

        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        signInGoogle = findViewById(R.id.signInGoogle);
        signInGoogle.setSize(SignInButton.SIZE_STANDARD);

        etUserName = findViewById(R.id.etUserName);
        etPasswd = findViewById(R.id.etPasswd);
        swAuth = findViewById(R.id.swAuth);
        errorText = findViewById(R.id.errorText);

        btnIniciarSesion.setOnClickListener(this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        signInGoogle.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("message");
//
//        myRef.setValue("Profesor Royer");
//        edHello = findViewById(R.id.edHello);

//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                edHello.setText(" " + value );
//                //  Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                //  Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        System.out.println(signInIntent);
    }

    public void checkInternetConnection(){
        Intent ir = new Intent(this, MyService.class);
        startService(ir);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
  
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signInGoogle:
                signIn();
                break;
            case R.id.btnIniciarSesion:
                String userName = etUserName.getText().toString();
                String passwrd = etPasswd.getText().toString();
                if (userName.matches("") || passwrd.matches("")) {
                    errorText.setText("Username and password are required");
                } else if (swAuth.isChecked() != true) {
                    errorText.setText("You must check that you are human");
                } else {
                    // Request a string response from the provided URL.
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url, null, response -> {
                                try {
                                    users = response.get("users");
                                    System.out.println(users);
                                    usersArray = users.toString();
                                    System.out.println(usersArray);

                                    JSONArray array = new JSONArray(usersArray);
                                    Boolean userFound = false;
                                    Boolean truePass = false;
                                    for (int i =0; i < array.length(); i++) {
                                        JSONObject row = array.getJSONObject(i);
                                        String user = row.getString("usernamer");
                                        String pass = row.getString("passwd");
                                        if (userName.matches(user)) {
                                            System.out.println("User match");
                                            userFound = true;
                                            if (passwrd.matches(pass)) {
                                                System.out.println("Pass match");
                                                truePass = true;
                                            }
                                        }
                                        System.out.println(userName);
                                        System.out.println(user);
                                    }
                                    System.out.println(userFound);
                                    if (userFound == true && truePass == true) {
                                        logIn();
                                    } else if (userFound == false) {
                                        accessAllowed = false;
                                        errorText.setText("This user doesn't exists!");
                                    } else {
                                        accessAllowed = false;
                                        errorText.setText("Username and password combination is wrong!");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }, error -> {
                                // TODO: Handle error
                                accessAllowed = false;
                                errorText.setText("Login error!");
                            });

                    requestQueue.add(jsonObjectRequest);
                }
            default:
                break;
        }
    }

    public void logIn() {
        Intent ir = new Intent(this, Home.class);
        Bundle data = new Bundle();
        data.putString("userName", etUserName.getText().toString());
        data.putString("passwd", etPasswd.getText().toString());
        ir.putExtras(data);
        ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(ir);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater Inflater = getMenuInflater();
//        Inflater.inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case  R.id.menuInicio:
                //Toast.makeText(this, "Hola menu Home", Toast.LENGTH_LONG).show();
                Intent ir2 = new Intent(this, MainActivity.class);
                ir2.addFlags(ir2.FLAG_ACTIVITY_CLEAR_TOP | ir2.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ir2);
            case  R.id.menuHome:
                //Toast.makeText(this, "Hola menu Home", Toast.LENGTH_LONG).show();
                Intent ir = new Intent(this, Home.class);
                ir.addFlags(ir.FLAG_ACTIVITY_CLEAR_TOP | ir.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(ir);
            case  R.id.menuLists:
                //Toast.makeText(this, "Hola menu Home", Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, MyLists.class);
                i.addFlags(i.FLAG_ACTIVITY_CLEAR_TOP | i.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        checkInternetConnection();
    }

    private void updateUI(GoogleSignInAccount account) {
        if(account != null){
            Toast.makeText(this,"U Signed In successfully",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"U Didnt signed in", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Toast.makeText(this,"OnStop", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this,"OnDestroy", Toast.LENGTH_LONG).show();
    }

}