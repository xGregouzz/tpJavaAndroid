package edu.esiea.androidcourse.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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

        // Soulignement
        TextView textView = (TextView) findViewById(R.id.loginTextView);
        SpannableString content = new SpannableString(textView.getText());
        content.setSpan(new UnderlineSpan(), 27, content.length(), 0);
        textView.setText(content);

        // Recupération de la database et de ses méthodes
        databaseHelper = new DatabaseHelper(this);

        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        Button signUpButton = findViewById(R.id.signUpButton);
        TextView loginTextView = findViewById(R.id.loginTextView);

        // Logique pour la création du user
        signUpButton.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                User newUser = new User(username, password);

                // Ajout du nouvel user en bdd
                long result = databaseHelper.addUser(newUser);

                if (result != -1) {
                    // Transition NewsFeed
                    Intent intent = new Intent(SignUpActivity.this, NewsFeedActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    showToast("Échec de la création de compte");
                }
            } else {
                showToast("Veuillez remplir tous les champs");
            }
        });

        loginTextView.setOnClickListener(view -> {
            // Transition Login
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

