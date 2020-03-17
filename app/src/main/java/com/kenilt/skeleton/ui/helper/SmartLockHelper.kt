package com.kenilt.skeleton.ui.helper

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialRequest
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.kenilt.skeleton.ui.base.BaseActivity
import com.kenilt.skeleton.utils.LXLog
import javax.inject.Inject


/**
 * Created by thangnguyen on 2019-08-28.
 */
class SmartLockHelper @Inject constructor(private val activity: BaseActivity<*>)
    : GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mIsResolving: Boolean = false
    private var mIsRequesting: Boolean = false
    private var mIsAutoRequestFirstTime: Boolean = false

    var smartLockListener: SmartLockListener? = null

    fun initApiClient(smartLockListener: SmartLockListener?) {
        mGoogleApiClient = GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .enableAutoManage(activity, 0, this)
                .addApi(Auth.CREDENTIALS_API)
                .build()

        this.smartLockListener = smartLockListener
    }

    override fun onConnected(bundle: Bundle?) {
        // Request Credentials once connected. If credentials are retrieved
        // the user will either be automatically signed in or will be
        // presented with credential options to be used by the application
        // for sign in.
        if (!mIsAutoRequestFirstTime) {
            requestCredentials()
            mIsAutoRequestFirstTime = true
        }
    }

    override fun onConnectionSuspended(cause: Int) {
        // do nothing
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        // do nothing
    }

    fun saveCredential(email: String?, password: String?) {
        if (mGoogleApiClient?.isConnected == true) {
            val credential = Credential.Builder(email)
                    .setPassword(password)
                    .build()
            saveCredential(credential)
        }
    }

    private fun saveCredential(credential: Credential) {
        // Credential is valid so save it.
        Auth.CredentialsApi.save(mGoogleApiClient,
                credential).setResultCallback { status ->
                    if (status.isSuccess) {
                        LXLog.d(TAG, "Credential saved")
                    } else {
                        LXLog.d(TAG, "Attempt to save credential failed " +
                                status.getStatusMessage() + " " +
                                status.getStatusCode())
                        resolveResult(status, RC_SAVE)
                    }
                }
    }

    private fun resolveResult(status: Status, requestCode: Int) {
        // We don't want to fire multiple resolutions at once since that can result
        // in stacked dialogs after rotation or another similar event.
        if (mIsResolving) {
            LXLog.w(TAG, "resolveResult: already resolving.")
            return
        }

        LXLog.d(TAG, "Resolving: $status")
        if (status.hasResolution()) {
            LXLog.d(TAG, "STATUS: RESOLVING")
            try {
                status.startResolutionForResult(activity, requestCode)
                mIsResolving = true
            } catch (e: IntentSender.SendIntentException) {
                LXLog.e(TAG, "STATUS: Failed to send resolution.", e)
            }
        } else {
            LXLog.d(TAG, "STATUS: NOT RESOLVE")
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        LXLog.d(TAG, "onActivityResult:$requestCode:$resultCode:$data")

        if (requestCode == RC_READ) {
            if (resultCode == RESULT_OK) {
                val credential = data?.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                credential?.let { smartLockListener?.onRetrievedCredential(it) }
            } else {
                LXLog.e(TAG, "Credential Read: NOT OK")
            }
        } else if (requestCode == RC_SAVE) {
            LXLog.d(TAG, "Result code: $resultCode")
            if (resultCode == RESULT_OK) {
                LXLog.d(TAG, "Credential Save: OK")
            } else {
                LXLog.e(TAG, "Credential Save Failed")
            }
        }
        mIsResolving = false
    }

    private fun requestCredentials() {
        mIsRequesting = true

        val request = CredentialRequest.Builder()
                .setPasswordLoginSupported(true)
                .build()

        Auth.CredentialsApi.request(mGoogleApiClient, request).setResultCallback { credentialRequestResult ->
            mIsRequesting = false
            val status = credentialRequestResult.status
            when {
                credentialRequestResult.status.isSuccess -> {
                    // Successfully read the credential without any user interaction, this
                    // means there was only a single credential and the user has auto
                    // sign-in enabled.
                    val credential = credentialRequestResult.credential
                    smartLockListener?.onRetrievedCredential(credential)
                }
                status.statusCode == CommonStatusCodes.RESOLUTION_REQUIRED -> {
                    // This is most likely the case where the user has multiple saved
                    // credentials and needs to pick one.
                    resolveResult(status, RC_READ)
                }
                status.statusCode == CommonStatusCodes.SIGN_IN_REQUIRED -> {
                    // This is most likely the case where the user does not currently
                    // have any saved credentials and thus needs to provide a username
                    // and password to sign in.
                    LXLog.d(TAG, "Sign in required")
                }
                else -> LXLog.w(TAG, "Unrecognized status code: " + status.statusCode)
            }
        }
    }

    fun deleteCredential(email: String?, password: String?) {
        val credential = Credential.Builder(email)
                .setPassword(password)
                .build()
        deleteCredential(credential)
    }

    private fun deleteCredential(credential: Credential) {
        Auth.CredentialsApi.delete(mGoogleApiClient,
                credential).setResultCallback { status ->
            if (status.isSuccess) {
                LXLog.d(TAG, "Credential successfully deleted.")
            } else {
                // This may be due to the credential not existing, possibly
                // already deleted via another device/app.
                LXLog.d(TAG, "Credential not deleted successfully.")
            }
        }
    }

    interface SmartLockListener {
        fun onRetrievedCredential(credential: Credential)
    }

    companion object {
        private const val TAG = "SmartLockHelper"
        private const val RC_SAVE = 1
        private const val RC_READ = 3
    }
}
