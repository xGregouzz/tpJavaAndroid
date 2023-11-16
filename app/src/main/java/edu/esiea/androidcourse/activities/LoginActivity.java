package edu.esiea.androidcourse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        databaseHelper = new DatabaseHelper(this);
        sessionManager = new SessionManager(this);

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        TextView signUpTextView = findViewById(R.id.signUpTextView);

        loginButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                // Vérification des informations de connexion dans la base de données
                if (databaseHelper.checkUser(username, password)) {
                    // Connexion réussie, enregistrez le nom d'utilisateur dans la session
                    sessionManager.saveUsername(username);

                    // Passez à l'activité NewsFeed
                    Intent intent = new Intent(LoginActivity.this, NewsFeedActivity.class);
                    startActivity(intent);
                    finish(); // Fermez l'activité actuelle pour éviter le retour en arrière
                } else {
                    // Affichez un message d'erreur si la connexion échoue
                    showToast("Nom d'utilisateur ou mot de passe incorrect");
                }
            } else {
                showToast("Veuillez remplir tous les champs");
            }
        });

        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lorsque l'utilisateur clique sur "Vous avez déjà un compte ? Connectez-vous"
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
