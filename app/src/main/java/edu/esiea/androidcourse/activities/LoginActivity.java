package edu.esiea.androidcourse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.esiea.androidcourse.R;
import edu.esiea.androidcourse.data.DatabaseHelper;
import edu.esiea.androidcourse.data.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        TextView signUpTextView = (TextView) findViewById(R.id.signUpTextView);

        // Soulignement
        SpannableString content = new SpannableString(signUpTextView.getText());
        content.setSpan(new UnderlineSpan(), 28, content.length(), 0);
        signUpTextView.setText(content);

        // Recupération de la database, de la session et de leurs méthodes
        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            //Condition de connexion
            if (!username.isEmpty() && !password.isEmpty()) {
                // Vérification des informations de connexion dans la base de données
                if (databaseHelper.checkUser(username, password)) {
                    // Insertion du username en session
                    sessionManager.saveUsername(username);
                    // Transition NewsFeed
                    Intent intent = new Intent(LoginActivity.this, NewsFeedActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast("Nom d'utilisateur ou mot de passe incorrect");
                }
            } else {
                showToast("Veuillez remplir tous les champs");
            }
        });

        signUpTextView.setOnClickListener(view -> {
            // Transition SignUp
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
