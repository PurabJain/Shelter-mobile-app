package com.purabjain.shelter;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Model {

    private int mHostelImage1;
    private int mHostelImage2;
    private int mHostelImage3;
    private String facilities_textView;
    private String restrictions_textView;
    private String mHostelName;
    private String mHostelAddress1;
    private String mPhoneNumber;


    public Model() {
    }

    public Model(int mHostelImage1, int mHostelImage2, int mHostelImage3,
                 String facilities_textView, String restrictions_textView, String mHostelName,
                 String mHostelAddress1, String mPhoneNumber) {
        this.mHostelImage1 = mHostelImage1;
        this.mHostelImage2 = mHostelImage2;
        this.mHostelImage3 = mHostelImage3;
        this.facilities_textView = facilities_textView;
        this.restrictions_textView = restrictions_textView;
        this.mHostelName = mHostelName;
        this.mHostelAddress1 = mHostelAddress1;
        this.mPhoneNumber = mPhoneNumber;
    }


}
