package com.example.newsapp.presentation.authenticator

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentAuthenticatorBinding
import com.example.newsapp.presentation.ext.safeNavigate

class AuthenticatorFragment : Fragment() {
    private lateinit var binding: FragmentAuthenticatorBinding
    private val cancellationSignal =
        CancellationSignal()


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthenticatorBinding.inflate(layoutInflater)
        tryAuthenticate()
        return binding.root
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal.setOnCancelListener {
            notifyUser(getString(R.string.auth_canceled_byuser))
        }
        return cancellationSignal
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        context?.let { context ->
            val keyguardManager: KeyguardManager =
                context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

            if (!keyguardManager.isKeyguardSecure) {
                notifyUser(getString(R.string.auth_authentication_unable_settings))
                return false
            }
            if (ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.USE_BIOMETRIC
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notifyUser(getString(R.string.auth_authentication_unable))
                return false
            }
            return activity?.packageManager?.hasSystemFeature(
                PackageManager.FEATURE_FINGERPRINT
            ) == true
        }
        return false
    }

    private fun notifyUser(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun tryAuthenticate() {
        context?.let { context ->
            val executor = ContextCompat.getMainExecutor(context)
            if (checkBiometricSupport() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val biometricPrompt = BiometricPrompt.Builder(context)
                val authPrompt =
                    biometricPrompt.setTitle(getString(R.string.auth_title))
                        .setSubtitle(getString(R.string.auth_subtitle)).setNegativeButton(
                            getString(R.string.auth_negative_button_text),
                            executor
                        ) { _, _ -> notifyUser(getString(R.string.auth_cancel_message)) }
                        .build()
                authPrompt.authenticate(getCancellationSignal(), executor, authCallback)
            } else {
                goToHeadlinesPage()
            }
        }
    }

    private val authCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    goToHeadlinesPage()
                    super.onAuthenticationSucceeded(result)
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }
            }

    private fun goToHeadlinesPage() {
        findNavController().safeNavigate(
            AuthenticatorFragmentDirections.actionAuthenticatorFragmentToHeadlinesFragment()
        )
    }
}
