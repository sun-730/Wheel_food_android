package wheelofeats.roxdesign.com.wheelofeats.Fragment;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import javax.microedition.khronos.opengles.GL;

import wheelofeats.roxdesign.com.wheelofeats.Activity.TermsActivity;
import wheelofeats.roxdesign.com.wheelofeats.Activity.TutorialActivity;
import wheelofeats.roxdesign.com.wheelofeats.Activity.UnlockFeatureActivity;
import wheelofeats.roxdesign.com.wheelofeats.R;
import wheelofeats.roxdesign.com.wheelofeats.Utils.Global;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends android.app.Fragment {
    LinearLayout privacyPolicy, unlockFeature;
    Switch swt_sound;

    public SettingFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        privacyPolicy = (LinearLayout) view.findViewById(R.id.privacy_policy);
        unlockFeature = (LinearLayout) view.findViewById(R.id.unlock_feature);
        swt_sound = (Switch) view.findViewById(R.id.switch_sound);
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), TermsActivity.class));

            }
        });
        unlockFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), UnlockFeatureActivity.class));

            }
        });
        setEvent();
        ImageView sh_facebook = (ImageView) view.findViewById(R.id.share_facebook);
        ImageView sh_google= (ImageView) view.findViewById(R.id.share_google);
        ImageView sh_instagram = (ImageView) view.findViewById(R.id.share_instagram);
        ImageView sh_pinterest = (ImageView) view.findViewById(R.id.share_pinterest);
        ImageView sh_twitter = (ImageView) view.findViewById(R.id.share_twitter);
        ImageView sh_tumblr = (ImageView) view.findViewById(R.id.share_tumblr);
        ImageView sh_mail = (ImageView) view.findViewById(R.id.share_mail);
        ImageView follow_facebook = (ImageView) view.findViewById(R.id.follow_fb);
        ImageView follow_twitter = (ImageView) view.findViewById(R.id.follow_tt);
        ImageView follow_instagram = (ImageView) view.findViewById(R.id.follow_ig);
        sh_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareFacebook();
            }
        });
        sh_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareGoogle();
            }
        });
        sh_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareInstagram();
            }
        });
        sh_pinterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePinterest();
            }
        });
        sh_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTwitter();
            }
        });
        sh_tumblr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareTumblr();
            }
        });
        sh_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareMail();
            }
        });
        follow_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followFacebook();
            }
        });
        follow_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followTwitter();
            }
        });
        follow_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                followInstagram();
            }
        });
        return view;
    }

    void setEvent(){
        swt_sound.setChecked(Global.sound_on);
        swt_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                savePreference(b);
            }
        });
    }

    private void savePreference(boolean b){
        String key = Global.WHEEL_SP;
        SharedPreferences sp = getActivity().getSharedPreferences(key, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Global.SOUND_ONOFF, b);
        editor.commit();
    }
    void shareFacebook(){

    }
    void shareGoogle(){

    }
    void shareInstagram(){

    }
    void sharePinterest(){

    }
    void shareTwitter(){

    }
    void shareTumblr(){

    }
    void shareMail(){

    }
    void followFacebook(){

    }
    void followTwitter(){

    }
    void followInstagram(){

    }
}
