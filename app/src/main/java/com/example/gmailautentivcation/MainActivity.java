package com.example.gmailautentivcation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
    }

    public  void iniciarGoogleOAuth(View view) {

        OAuthProvider.Builder provider =
                OAuthProvider.newBuilder("google.com");

        // Forzar pantalla de login SIEMPRE
        provider.addCustomParameter("prompt", "login");

        mAuth.startActivityForSignInWithProvider(this, provider.build())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(this, PerfilActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this,
                                "Error OAuth Google",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}