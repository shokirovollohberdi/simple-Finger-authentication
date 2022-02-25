package uz.authentication.biometricauthentication

import android.content.Context
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    lateinit var btnAuth: TextView
    lateinit var tvAuth: TextView
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: androidx.biometric.BiometricPrompt
    private lateinit var promptInfo: androidx.biometric.BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAuth = findViewById(R.id.btnAuth)
        tvAuth = findViewById(R.id.tvAuthStatus)
        executor = ContextCompat.getMainExecutor(this)
//        biometricPrompt = androidx.biometric.BiometricPrompt(this, executor, object :
//            BiometricPrompt.AuthenticationCallback() {
//            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
//                tvAuth.text = "Error- $errString"
//            }
//
//            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
//                tvAuth.text = "Auth succeed..."
//            }
//
//            override fun onAuthenticationFailed() {
//                tvAuth.text = "Failed"
//            }
//        })

        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = androidx.biometric.BiometricPrompt(this, executor,
            object : androidx.biometric.BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(result: androidx.biometric.BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    tvAuth.text = "Succeed"
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    tvAuth.text = "Fail"
                }


            })

        promptInfo = androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for my app")
            .setSubtitle("Log in using your biometric credential")
            .setNegativeButtonText("Use account password")
            .build()

        // Prompt appears when user clicks "Log in".
        // Consider integrating with the keystore to unlock cryptographic operations,
        // if needed by your app.

        btnAuth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }
}