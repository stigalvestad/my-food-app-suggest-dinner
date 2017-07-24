package no.stig.dinner.suggest.myfoodappsuggestdinner;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.amazonaws.auth.CognitoCredentialsProvider;
import com.amazonaws.mobileconnectors.cognitoauth.Auth;
import com.amazonaws.mobileconnectors.cognitoauth.AuthUserSession;
import com.amazonaws.mobileconnectors.cognitoauth.handlers.AuthHandler;
import com.amazonaws.regions.Regions;


public class HelloWorldActivity extends FragmentActivity {

    private Auth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_world);


        // alternative A
        initCognito();

        // alternative B
        Context appContext = getApplicationContext();
        CognitoCredentialsProvider credentialsProvider = new CognitoCredentialsProvider(
                getResourceString(appContext, R.string.identity_id_test),
                Regions.fromName(getResourceString(appContext, R.string.aws_region)));
    }

    @NonNull
    private String getResourceString(Context appContext, int identity_id_test) {
        return appContext.getResources().getString(identity_id_test);
    }

    /**
     * Setup authentication with Cognito.
     */
    void initCognito() {
        //  -- Create an instance of Auth --
        Auth.Builder builder = new Auth.Builder().setAppClientId(getString(R.string.cognito_client_id))
                .setAppCognitoWebDomain(getString(R.string.cognito_web_domain))
                .setApplicationContext(getApplicationContext())
                .setAuthHandler(new callback())
                .setSignInRedirect(getString(R.string.app_redirect))
                .setSignOutRedirect(getString(R.string.app_redirect));
        this.auth = builder.build();
//        appRedirect = Uri.parse(getString(R.string.app_redirect));
    }

    /**
     * Callback handler for Amazon Cognito.
     */
    class callback implements AuthHandler {

        @Override
        public void onSuccess(AuthUserSession authUserSession) {
            // Show tokens for the authenticated user
//            setAuthUserFragment(authUserSession);
            System.out.println("on success: " + authUserSession);
        }

        @Override
        public void onSignout() {
            // Back to new user screen.
            System.out.println("on signout");
//            setNewUserFragment();
        }

        @Override
        public void onFailure(Exception e) {
            System.err.println("on FAILURE");
        }
    }

}
