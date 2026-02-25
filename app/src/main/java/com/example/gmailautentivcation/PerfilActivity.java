package com.example.gmailautentivcation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Date;

public class PerfilActivity extends AppCompatActivity {
    private TextView txtCorreo, txtProveedor, txtUltimoIngreso, txtUID;
    private Button btnCerrarSesion;
    private FirebaseAuth mAuth;

    ImageView imgFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgFoto = findViewById(R.id.imgFoto);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        cargarFotoPerfil(user);
        // Conectar con los elementos del XML
        txtCorreo = findViewById(R.id.txtCorreo);
        txtProveedor = findViewById(R.id.txtProveedor);
        txtUltimoIngreso = findViewById(R.id.txtUltimoIngreso);
        txtUID = findViewById(R.id.txtUID);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        if (user != null) {
            // Correo
            txtCorreo.setText("Correo: " + user.getEmail());
            // UID
            txtUID.setText("UID: " + user.getUid());
            // Último ingreso formateado bonito
            long timestamp = user.getMetadata().getLastSignInTimestamp();
            String fecha = DateFormat.getDateTimeInstance().format(new Date(timestamp));
            txtUltimoIngreso.setText("Último ingreso: " + fecha);
            // Proveedor (google.com)
            if (user.getProviderData().size() > 1) {
                txtProveedor.setText("Proveedor: " +     user.getProviderData().get(1).getProviderId());
            } else {
                txtProveedor.setText("Proveedor: Google");
            }
        }


    }
    public void cargarFotoPerfil(FirebaseUser user) {
        if (user.getPhotoUrl() != null) {
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .into(imgFoto);
        }
    }

     public void btnCerrarSesion ( View view)
    {
        mAuth.signOut();

        Intent intent = new Intent(PerfilActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        finish();
    }
}