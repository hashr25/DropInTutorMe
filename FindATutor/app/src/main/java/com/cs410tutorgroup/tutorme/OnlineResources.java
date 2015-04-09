package com.cs410tutorgroup.tutorme;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.cs410tutorgroup.findatutor.R;


public class OnlineResources extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_resources);

        enableLinks();
    }

    private void enableLinks()
    {
        /**
         * This method is used to fix an unclickable link problem.
         * This solution was found on stackoverflow.com
         * If any more links are added to the layout. This method
         *      must be changed to include the new link.
         */

        TextView linkToEnable = (TextView) findViewById(R.id.onlineTutoringText);
        linkToEnable.setMovementMethod(LinkMovementMethod.getInstance());

        linkToEnable = (TextView) findViewById(R.id.khanAcademyText);
        linkToEnable.setMovementMethod(LinkMovementMethod.getInstance());

        linkToEnable = (TextView) findViewById(R.id.mitOpenCourseWareText);
        linkToEnable.setMovementMethod(LinkMovementMethod.getInstance());
    }

}