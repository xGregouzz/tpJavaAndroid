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
import edu.esiea.androidcourse.models.User;

public class SignUpActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        databaseHelper = new DatabaseHelper(this);

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button signUpButton = findViewById(R.id.signUpButton);
        TextView loginTextView = findViewById(R.id.loginTextView);

        signUpButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                User newUser = new User(username, password);

                // Ajouter un nouvel utilisateur à la base de données
                long result = databaseHelper.addUser(newUser);

                if (result != -1) {
                    // Création de compte réussie, passez à l'activité NewsFeed
                    Intent intent = new Intent(SignUpActivity.this, NewsFeedActivity.class);
                    startActivity(intent);
                    finish(); // Fermez l'activité actuelle pour éviter le retour en arrière
                } else {
                    // Affichez un message d'erreur si la création de compte échoue
                    showToast("Échec de la création de compte");
                }
            } else {
                showToast("Veuillez remplir tous les champs");
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lorsque l'utilisateur clique sur "Vous avez déjà un compte ? Connectez-vous"
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

