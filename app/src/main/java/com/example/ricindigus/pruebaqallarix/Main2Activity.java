package com.example.ricindigus.pruebaqallarix;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity {
    private String mNumero;
    private String mIdVerification;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private EditText etCodigo;
    private Button btIngresar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mNumero = getIntent().getExtras().getString("numero");

        mAuth = FirebaseAuth.getInstance();

        etCodigo = findViewById(R.id.etCodigo);
        btIngresar = findViewById(R.id.btIngresar);

        btIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verificarCodigo(etCodigo.getText().toString());
            }
        });

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                String code = phoneAuthCredential.getSmsCode();

                if (code != null){
                    etCodigo.setText(code);
                    verificarCodigo(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String idVerification, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(idVerification, forceResendingToken);
                mIdVerification = idVerification;
                mResendToken = forceResendingToken;
            }
        };

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+51"+mNumero,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS ,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }

    private void verificarCodigo(String code) {
        signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(mIdVerification,code));
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = task.getResult().getUser();
                            Intent intent = new Intent(Main2Activity.this,Main3Activity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(Main2Activity.this, "codigo ingresado es invalido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}
