package com.example.finalprojectcs125;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


/** Discussion page! This is where we will communicate so we know what we want & any issues we have - be as descriptive as possible
 /** IF a task needs to be done, use the TODO text to do so
 *
 * Feedback Page ---------------
 * I would prefer if we did not have our email in clear text. The app should do work behind to scenes to take their info and send/store it
 *
 *
 * Settings Page ---------------
 * TODO add settings ofc - reset points button
 * Todo !!!!(ANGEL)!!!! Can you sort out the UI here? and check if everything looks neat and cool?
 * Be careful when sorting the UI ^^^^^ The points screen needs ample size to display the score ex: points of 100 or 100000000
 * What settings are going to be stored?
 *
 * Secret Thank You Page -------------
 * todo fix the clunkiness of thank you
 *
 *
 *
 * Main Clicker App Page --------------
 * todo make the page & theme & upgrade page
 * todo upgrades being x points per click, points per sec
 * todo special abilities IF time
 * todo fix transition crash
 *
 *
 * General Critiques ---------
 * Effectivity of a randomized array of only 6 or so greetings
 * is questionable. It's PSYCH 101 when understanding that the Diversification of greetings adds excitement and encourages
 * repeated visits to the main menu. I've coined it
 * the "fortune cookie effect", wherein user will visit menu (or any screen with randomized
 * strings) multiple times in an attempt to see a new greeting or message. May increase users time on our app. Note.
 *
 *
 */

public class MainActivity extends AppCompatActivity {
    public TextView timerio;
    public  TextView clicksio;
    public  Button empezario;
    /**
     * music
     */
    MediaPlayer song;
    MediaPlayer song2;
    MediaPlayer hack;
    /**
     * checks if X upgrade has been purchased.
     */
    boolean upgrade1Purchased = false;
    boolean upgrade2Purchased = false;
    boolean upgrade3Purchased = false;
    boolean upgrade4Purchased = false;
    boolean upgrade5Purchased = false;
    boolean upgrade6Purchased = false;
    boolean upgrade7Purchased = false;
    boolean upgrade8Purchased = false;

    CountDownTimer time;

    int tiempo = 30;
    int clix = 0;

    boolean leftClickerGame = false;

    /** points
     * Does what it says! Holds and stores the points that we have accumulated
     */
    public static int points;

    /** pointsPerClick
     * Sets the pointsPerClick! Initially is 1 point per click
     */
    public static int pointsPerClick;

    /** loadTime
     * The amount of time in seconds the user will need to wait until the page loads
     */
    public static int loadTime = 10;

    /**
     *
     */
    boolean firstimeforeverything = true;

    /** musicEnabled
     * tells us if the user wants to hear music
     */
    boolean musicEnabled = true;

    /** soundEffectsEnabled
     * tells us if the user wants to hear sound effects
     */
    boolean soundEffectsEnabled = true;

    /** goingbackToMainMenu
     *
     */
    boolean goingBackToMainMenu = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 2019-12-07 brief transition when starting app
        setContentView(R.layout.activity_main);

        VideoView video = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid);
        video.setVideoURI(uri);
        video.start();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        //ImageView imtired = findViewById(R.id.keybd);
       // imtired.

        /**
         * adds music
         */
        song = MediaPlayer.create(this, R.raw.song);
        song.setLooping(true);
        song.setVolume(1000,1000);
        song.start();
        //MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.song);
        // ring.start();

        MediaPlayer upsong2 = MediaPlayer.create(this, R.raw.still_feel);
        upsong2.setLooping(true);
        upsong2.setVolume(1000,1000);


        View currentView = this.findViewById(android.R.id.content);
        ConstraintLayout clickup = findViewById(R.id.clickup);
        if (currentView.equals(clickup) && musicEnabled) { //credits menu music
            song2 = MediaPlayer.create(this, R.raw.upgrades_song);
            song2.setLooping(true);
            song2.setVolume(1000,1000);
            song2.start();
        } else {
            if (musicEnabled) {
                song.start();
            }
        }
        TextView title = findViewById(R.id.title);
        title.setTextColor(Color.WHITE);
        title.setBackgroundColor(Color.TRANSPARENT);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(100); //blink duration
        anim.setStartOffset(1100);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        title.startAnimation(anim);




        /**
         * will save data if we choose to enable
         */
        SharedPreferences.Editor save = getSharedPreferences("myInt", MODE_PRIVATE).edit();
        save.putInt("points", points);
        save.apply();

        SharedPreferences pref = getSharedPreferences("PreferencesName", MODE_PRIVATE);
        int myInt = pref.getInt("points",0);

        pointsPerClick = 1;

        // Sets the welcome screen text to something nice and welcoming :)
        String[] welcomeArray = new String[6];
        double random = (Math.random() * (welcomeArray.length - 1));
        int replacer = (int) Math.ceil(random);
        welcomeArray[0] = "Welcome!";
        welcomeArray[1] = "Hey guys!";
        welcomeArray[2] = "How about that extra credit?";
        welcomeArray[3] = "Welcome to my CS125 app!";
        welcomeArray[4] = "Hello there :D";
        welcomeArray[5] = "Hey guys, welcome to my CS125 Project walkthrough :)";
       // TextView welcomeText = findViewById(R.id.welcomeText);
        //welcomeText.setText(welcomeArray[replacer]); //sets the welcome text
        // End of welcome text setting stuff!


        // Takes us to the feedback menu
        Button feedbackMenuButton = findViewById(R.id.creditsButton);
        feedbackMenuButton.setBackgroundColor(Color.RED);
        feedbackMenuButton.setOnClickListener(e -> {
            setContentView(R.layout.feedback_layout);
            feedbackTransition();
        });

        // Takes us to the secret/staff menu
        Button secretButton = findViewById(R.id.secretButton);
        secretButton.setBackgroundColor(Color.RED);
        secretButton.setOnClickListener(o -> {
            secretNameMethodTransition();
        });

        Button info = findViewById(R.id.info);
        info.setBackgroundColor(Color.RED);
        info.setTextColor(Color.WHITE);
        info.setOnClickListener(e -> {
            infoTransition();
        });

        // Takes us to the clicker game
        Button clickerGameButton = findViewById(R.id.clickergameButton);
        clickerGameButton.setBackgroundColor(Color.RED);
        clickerGameButton.setOnClickListener(iw -> {
            attemptatttempt();
        });

        // Takes us to the settingsButton
        Button settingsMenuButton = findViewById(R.id.settingsButton);
        settingsMenuButton.setBackgroundColor(Color.RED);
        settingsMenuButton.setOnClickListener(heyooo -> {
            settingsMenuTransition();
        });

      //  TextView menuTextPoints = findViewById(R.id.menuPointsTextView);
       // menuTextPoints.setTextColor(Color.WHITE);
       // menuTextPoints.setText("You have: " + points + " points");

    }
    public void infoTransition() {
        setContentView(R.layout.activity_info);


        //Button info = findViewById(R.id.info);
        //info.setBackgroundColor(Color.RED);
        //info.setTextColor(Color.WHITE);
        TextView heroes = findViewById(R.id.txt1);
        heroes.setTextColor(Color.WHITE);
        /**
         * Android plugin that enables links
         */
        TextView Url1 = findViewById(R.id.txt2);
        Url1.setText("ALAN TURING ----> https://www.britannica.com/biography/Alan-Turing");
        Url1.setTextColor(Color.WHITE);
        Linkify.addLinks(Url1, Linkify.WEB_URLS);
        Url1.setLinkTextColor(Color.WHITE);

        TextView Url2 = findViewById(R.id.txt3);
        Url2.setText("GEOFF ----> https://cs.illinois.edu/directory/profile/challen");
        Url2.setTextColor(Color.WHITE);
        Linkify.addLinks(Url2, Linkify.WEB_URLS);
        Url2.setLinkTextColor(Color.WHITE);

        TextView Url3 = findViewById(R.id.txt4);
        Url3.setText(" MARK ZUCKERBURG ----> https://www.thoughtco.com/mark-zuckerberg-biography-1991135");
        Url3.setTextColor(Color.WHITE);
        Linkify.addLinks(Url3, Linkify.WEB_URLS);
        Url3.setLinkTextColor(Color.WHITE);


        TextView Url4= findViewById(R.id.txt5);
        Url4.setText("ELON MUSK ----> https://www.biography.com/business-figure/elon-musk ");
        Url4.setTextColor(Color.WHITE);
        Linkify.addLinks(Url4, Linkify.WEB_URLS);
        Url4.setLinkTextColor(Color.WHITE);

        TextView Url5 = findViewById(R.id.txt6);
        Url5.setText("TODD HOWARD ----> https://www.famousbirthsdeaths.com/todd-howard-bio-net-worth-facts/");
        Url5.setTextColor(Color.WHITE);
        Linkify.addLinks(Url5, Linkify.WEB_URLS);
        Url5.setLinkTextColor(Color.WHITE);

        Button infoReturn = findViewById(R.id.inforeturn);
        infoReturn.setTextColor(Color.BLACK);
        infoReturn.setBackgroundColor(Color.RED);
        infoReturn.setOnClickListener(qq -> {
            mainMenuButtonReinitializer();
        });

    }
    /** feedbackTransition
     * transitions us to the feedbackMenu and initializes all the buttons
     */
    public void feedbackTransition() {
        // TODO: 2019-12-07 add transition func here 
        setContentView(R.layout.feedback_layout);
        ConstraintLayout e = findViewById(R.id.feedback);
        e.setBackgroundColor(Color.BLACK);
        TextView feedtext = findViewById(R.id.feedtext);
        feedtext.setBackgroundColor(Color.TRANSPARENT);
        feedtext.setTextColor(Color.WHITE);
        feedtext.setVisibility(View.VISIBLE);
        /**
         * credits?
         */

        String[] taArray = new String[8];
        taArray[0] = "Aabhas Chauhan";
        taArray[1] = "Heather Huynh";
        taArray[2] = "Jishnu Dey";
        taArray[3] = "Mingkun Gao";
        taArray[4] = "Mohammed Hassan";
        taArray[5] = "Nerla Jean-Louis";
        taArray[6] = "Silas Hsu";
        taArray[7] = "Vinith Krishnan";

        String[] caArray = new String[86];
        caArray[0] = "Alan Andrade";
        caArray[1] = "Alex Nickl";
        caArray[2] = "Andre Castaneda";
        caArray[3] = "Ang Li";
        caArray[4] = "Archisha Majee";
        caArray[5] = "Arden Wen";
        caArray[6] = "Ashay Mehta";
        caArray[7] = "Ben Sutter";
        caArray[8] = "Benedict Austriaco";
        caArray[9] = "Blair Wang";
        caArray[10] = "Calen Resh";
        caArray[11] = "Charudutt Kher";
        caArray[12] = "Chris Kull";
        caArray[13] = "Colleen McConnell";
        caArray[14] = "David Ruvinskiy";
        caArray[15] = "Dylan Ott";
        caArray[16] = "Emilia Kedainis";
        caArray[17] = "Eric Liu";
        caArray[18] = "Filip Matasic";
        caArray[19] = "Gabriella Xue";
        caArray[20] = "Geon Kim";
        caArray[21] = "Husnain Raza";
        caArray[22] = "Hyosang Ahn";
        caArray[23] = "Isabel Ruiz";
        caArray[24] = "Isaiah Delgado";
        caArray[25] = "Jack Gentile";
        caArray[26] = "Jack Shao";
        caArray[27] = "Jeffrey Aguirre";
        caArray[28] = "Jessie Yang";
        caArray[29] = "Jiacheng Guo";
        caArray[30] = "Jiahe Su";
        caArray[31] = "Jiaqi Cao";
        caArray[32] = "Jiaqi Cheng";
        caArray[33] = "John Weng";
        caArray[34] = "Jonathan Kim";
        caArray[35] = "Jordan Parker";
        caArray[36] = "Joseph Wang";
        caArray[37] = "Long Phan";
        caArray[38] = "Matt Angel";
        caArray[39] = "Matt Forrest";
        caArray[40] = "Matthew Augustyn";
        caArray[41] = "Matthew Lezak";
        caArray[42] = "Matthew Pham";
        caArray[43] = "Megan Wang";
        caArray[44] = "Mona Liao";
        caArray[45] = "Moritz Kraemer";
        caArray[46] = "Nanyi Yang";
        caArray[47] = "Nima Shafikhani";
        caArray[48] = "Ning Wan";
        caArray[49] = "Pranav Raman";
        caArray[50] = "Quentin Wetzel";
        caArray[51] = "Quinn Collins";
        caArray[52] = "Ridha Alkhabaz";
        caArray[53] = "Rochelle Tham";
        caArray[54] = "Ruisong Li";
        caArray[55] = "Ryan O'Neall";
        caArray[56] = "Sarod Nori";
        caArray[57] = "Shirley Wang";
        caArray[58] = "Shruthi Kondin";
        caArray[59] = "Snehal Somalraju";
        caArray[60] = "Wajid Siddiqui";
        caArray[61] = "Waleed Khan";
        caArray[62] = "Wall Sun";
        caArray[63] = "Wangqi Xiang";
        caArray[64] = "Wei Shen";
        caArray[65] = "Xavier Higgins";
        caArray[66] = "Xiaohan Wang";
        caArray[67] = "Xiaoying Zhu";
        caArray[68] = "Yan Yan";
        caArray[69] = "Yixuan Jia";
        caArray[70] = "Youcheng Cai";
        caArray[71] = "Yu Du";
        caArray[72] = "Yunwen Zhu";
        caArray[73] = "Yuxin Wang";
        caArray[74] = "Zach Hamilton";
        caArray[75] = "Zaitian Wang";
        caArray[76] = "Zepei Li";
        caArray[77] = "Zihan Shan";
        caArray[78] = "Zihan Xu"; // Last CA ----
        caArray[79] = "Ahmad Dinkins"; // Office hours captains vvv
        caArray[80] = "Ajay Tatachar";
        caArray[81] = "Amirthavarshini Sureshbabu";
        caArray[82] = "Catherine Yuan";
        caArray[83] = "Daniel Gleason";
        caArray[84] = "Nikhil Garg";
        caArray[85] = "Rima Bouhal";


        Animation animation;
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.feedback_layout);
        //feedtext.animate().scaleY(2f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(10);
        /*todo add the CDs and test that everyone is present
        feedtext.setText("CREDITS\n\n" + "DEVELOPERS:\n\n" + "ANGEL CANTY\n" +
                "Lord-Manipulator of human-computer interaction(UI)\n" + "Master-Scheduler\n" +"Master Animations Coder\n" +
                "Front-End Coordinator\n" + "framework assistant\n\n" +"NATHANIEL ZUKOWSKI \n" +"Lord-Architect of software framework(Code Structure/Groundwork)\n"
                + "Master-Scrutinizer\n" +  "Master Debugger\n" + "Back-End Coordinator\n" + "Animations assistant\n\n" +
                "\nTeaching Assistants\n" + "\n" + taArray[0] + "\n" + taArray[1] + "\n" + taArray[2] + "\n" + taArray[3] + "\n" + taArray[4] + "\n" + taArray[5] + "\n" + taArray[6] + "\n" + taArray[7] + "\n" +
                "Course Assistants\n\n" + "\n" +caArray[0] + "\n" +caArray[1] + "\n" +caArray[2] + "\n" +caArray[3] + "\n" +caArray[4] + "\n" +caArray[5] + "\n" +caArray[6] + "\n" +caArray[7] + "\n" +caArray[8] + "\n" +caArray[9] + "\n" +caArray[10] + "\n" +caArray[11] + "\n" +caArray[12] + "\n" +caArray[13] + "\n" +caArray[14] + "\n" +caArray[15] + "\n" +caArray[16] + "\n" +caArray[17] + "\n" +caArray[18] + "\n" +caArray[19] + "\n" +caArray[20] + "\n" +caArray[21] + "\n" +caArray[22] + "\n" +caArray[23] + "\n" +caArray[24] + "\n" +caArray[25] + "\n" +caArray[26] + "\n" +caArray[27] + "\n" +caArray[28] + "\n" +caArray[29] + "\n" +caArray[30] + "\n" +caArray[31] + "\n" +caArray[32] + "\n" +caArray[33] + "\n" +caArray[34] + "\n" +caArray[35] + "\n" +caArray[36] + "\n" +caArray[37] + "\n" +caArray[38] + "\n" +caArray[39] + "\n" +caArray[40] + "\n" +caArray[41] + "\n" +caArray[42] + "\n" +caArray[43] + "\n" +caArray[44] + "\n" +caArray[45] + "\n" +caArray[46] + "\n" +caArray[47] + "\n" +caArray[48] + "\n" +caArray[49] + "\n" +caArray[50] + "\n" +caArray[51] + "\n" +caArray[52] + "\n" +caArray[53] + "\n" +caArray[54] + "\n" +caArray[55] + "\n" +caArray[56] + "\n" +caArray[57] + "\n" +caArray[58] + "\n" +caArray[59] + "\n" +caArray[60] + "\n" +caArray[61] + "\n" +caArray[62] + "\n" +caArray[63] + "\n" +caArray[64] + "\n" +caArray[65] + "\n" +caArray[66] + "\n" +caArray[67] + "\n" +caArray[68] + "\n" +caArray[69] + "\n" +caArray[70] + "\n" +caArray[71] + "\n" +caArray[72] + "\n" +caArray[73] + "\n" +caArray[74] + "\n" +caArray[75] + "\n" +caArray[76] + "\n" +caArray[77] + "\n" +caArray[78] + "\n" +caArray[79] + "\n" +caArray[80] + "\n" +caArray[81] + "\n" +caArray[82] + "\n" +caArray[83] + "\n" +caArray[84] + "\n" +caArray[85] + "\n" +
                "\n" + "Course Developers\n\n" );
         */
        // TODO: 2019-12-09 Fix the text looking warped
        feedtext.setText("CREDITS:\n\n\n" + "DEVELOPERS:\n\n" + "ANGEL CANTY\n" +
                        "-Lord-Manipulator of human-computer interaction(UI)\n" + "-Master-Scheduler\n" +"-Master Animations/'Cool feats' Coder\n" +
                        "-Front-End Coordinator\n" + "-framework assistant\n\n" +"NATHANIEL ZUKOWSKI \n" +"-Lord-Architect of software framework/groundwork\n" +
                         "-Master-Scrutinizer\n" +  "-Master Debugger\n" + "-Back-End Coordinator\n"
                + "Animations assistant\n\n" + "SPECIAL THANKS TO ALL CAs, TAs, and CDs, ESPECIALLY THE SUPER-HELPFUL ONES\n" + "You know who you are. :)\n\n" + "WE DO NOT OWN ANY MUSIC OR IMAGES. \n\n" + "--Lay down your sweet and " +
                        "weary head -- the night is falling, you have come to journey's end. - Into the West, LOTR, Return of the King.\n\n" +
                "--'We're going to make it happen. As God is my bloody witness, I'm hell-bent on making it work. - Elon Musk\"\n\n " + "CS major or not, fight to make  CS work for you.\n\n" + "Code on.");
        feedtext.startAnimation(animation);

       // TextView thanjs = findViewById(R.layout.feedback_layout);
       // Animation thankks;
       // thankks =AnimationUtils.loadAnimation(getApplicationContext(), R.anim.feedback_layout);
       /// thanks.setText()
        if (musicEnabled) {
            song.stop();
            song2 = MediaPlayer.create(this, R.raw.still_feel);
            song2.setLooping(true);
            song2.setVolume(1000, 1000);
            song2.start();
        }

        Button returnMain = findViewById(R.id.returnMain);
        returnMain.setBackgroundColor(Color.RED);
        returnMain.setOnClickListener(f -> { //Returns to mainMenu from feedback screen
            setContentView(R.layout.activity_main);
            if (musicEnabled) {
                song2.stop();
                song = MediaPlayer.create(this, R.raw.song);
                song.setLooping(true);
                song.setVolume(1000, 1000);
                song.start();
            }
            mainMenuButtonReinitializer();
        });
    }

    /** clickerGame
     * code that allows the clicker to work properly.
     */
    public void clickerGame() {

        firstimeforeverything = false;

        //startClickerGame
        // if startClickerGame == true then start everything and set boolean to FALSE
        // if timer == 0 then stop the timer and do the do
        // IF the layout is NOT clickergame then stop
        // or, if user calls upgrade menu or settings or mainmenu, set the boolean to false


        /**
         * this textView is the timer.
         */
        timerio = findViewById(R.id.timer);
        //timerio.setTextColor(Color.WHITE);
        //timerio.setBackgroundColor(Color.RED);
        /**
         * this textView shows the # of clicks the user has
         */

        clicksio = findViewById(R.id.clicks);
        clicksio.setBackgroundColor(Color.RED);
        clicksio.setTextColor(Color.BLACK);

        /**
         * this button allows the user to start a clicking session.
         * If not clicked, the clicker wil not work. empezar = start, in spanish.
         */
        empezario = findViewById(R.id.empezar);
        /**
         * this is the clicker button itself.
         */

        currentEraButton.setEnabled(false);
        // TODO: 2019-12-07  declare all image buttons here - I think this is done :)

        empezario.setEnabled(true);

        /**
         * this textview encourages the user to keep playing once the timer has run out.
         */
        TextView finished = findViewById(R.id.finished);
       // finished.setTextColor(Color.WHITE);
        //finished.setBackgroundColor(Color.BLACK);
        //MediaPlayer upsong = MediaPlayer.create(this, R.raw.upgrades_song);


        time = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                empezario.setVisibility(View.INVISIBLE);;
                tiempo--;
                timerio.setText("Time: " + tiempo);
                //finished.setText("");
                if (tiempo == 0 ) {
                    finished.setText("Out Of Time!");
                }
            }
            @Override
            public void onFinish() {
                TextView clickerapppTs = findViewById(R.id.clickerAppPointsTextView);
                empezario.setVisibility(View.VISIBLE);
                song2.stop();
                clickerapppTs.setTextColor(Color.WHITE);
                clickerapppTs.setBackgroundColor(Color.TRANSPARENT);
                empezario.setEnabled(true);
                currentEraButton.setEnabled(false);
                String[] coercionArray = new String[9];
                double random = (Math.random() * (coercionArray.length - 1));
                int cs225 = (int) Math.ceil(random);
                coercionArray[0] = "Wanna play again?";
                coercionArray[1] = "What? You thought that was it? PLAY AGAIN!";
                coercionArray[2] = "Play again. I dare you";
                coercionArray[3] = "give in to your anger....click that button some more!";
                coercionArray[4] = "clickclickclickclick---don't stop! let's play again!";
                coercionArray[5] = "let us play again. shall we?";
                coercionArray[6] = "my i5 four-core can run faster than you can click. click faster!";
                coercionArray[7] = "Good work, but like my CS125 grade, it could be better. Try again?";
                coercionArray[8] = "Have you gotten Arthritis yet? No? Then click some more!";
                finished.setText(coercionArray[cs225]);
                if (leftClickerGame == true) {
                    song2.stop();
                }
                if (leftClickerGame == false) {
                    TextView clickerappPts = findViewById(R.id.clickerAppPointsTextView);
                    clickerappPts.setText("Total points: " + points);
                    clickerappPts.setTextColor(Color.WHITE);
                    // TODO: 2019-12-07 Ensure this bug is gone
                }
            }
        };
        currentEraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                clix += pointsPerClick;
                points += pointsPerClick;
                Log.i("Points","Points per click is: " + pointsPerClick);
                clicksio.setText("POINTS: " + clix);
                //timerio.setText("Time: 0");

            }
        });
        time.start();
        empezario.setEnabled(false);
        currentEraButton.setEnabled(true);
        clix = 0;
        tiempo = 30;
        timerio.setText("Time: " + tiempo);
        clicksio.setText("POINTS: " + clix);
        if (tiempo <= 30 && tiempo > 0) {
            finished.setText("click away!");
        }
        if (tiempo <= 0) {
            time.cancel();
        }
    }


    public void attemptatttempt() {
        setContentView(R.layout.activity_load);
        final ProgressBar pbar = findViewById(R.id.pbar);
        int a = 1000000;
        pbar.setProgress(a);
        //pbar.setVisibility(View.VISIBLE);
        ProgressBar v =  findViewById(R.id.pbar);
        v.getIndeterminateDrawable().setColorFilter(Color.RED,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        if (loadTime != 0) { //If we need to load
            ImageView loadIcon = findViewById(R.id.loadIcon); // Initialize the imageview
            if (era0UpgradeEquipped) { // cs125 logo
                loadIcon.setImageResource(R.drawable.background0);
                String[] loadArray = new String[7];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "HINT: on some some days, Forums > Office Hours. Choose wisely.";
                loadArray[1] = "Legend says that the majority of students didn't finish MP1 in FA19.";
                loadArray[2] = "CS 225 is a more difficult version of CS 125, and thus,  its sequel. You've been warned.";
                loadArray[3] = "HINT: On CBTF quizzes, try tackling the code before switching off , periodically, to multiple choice. ";
                loadArray[4] = "HINT: Be weary of neglecting a single grade source in CS 125. HW, quizzes, attendance--they are all important.";
                loadArray[5] = "DID YOU KNOW?: Of the Beginner-level CS courses, CS 125 is the most difficult. Tell all your friends.";
                loadArray[6] = "Contrary to popular belief, CS125, even as an 100-level CS class, is more difficult then some 200-level classes of different subjects.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }

            if (era1UpgradeEquipped) { // Turing Machine/turing facts
                loadIcon.setImageResource(R.drawable.turing_machine);
                String[] loadArray = new String[4];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "Alan Turing is considered by most to be the father of modern Computer Science.";
                loadArray[1] = "DID YOU KNOW?: Despite his hand in saving Britain, Turing was prosecuted and castrated due to his sexuality.";
                loadArray[2] = "HINT: To change clicker, access the 'HEROES' menu from the upgrades.";
                loadArray[3] = "DID YOU KNOW?: Alan Turing created the first computer-based chess game.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era2UpgradeEquipped) { // geoff fact & dogs
                // TODO: 2019-12-09 CHANGE THIS IMMEIDAT:KA<FL !!!!!!!!!!!!!
                loadIcon.setImageResource(R.drawable.chuchu1v1);
                String[] loadArray = new String[6];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "DID YOU KNOW?: Geoffrey Challen received a Phd. In CS from Harvard in 2010.";
                loadArray[1] = "Geoffrey Challen is the CS 125 professor at the University of Illinois, Urbana-Champaign.";
                loadArray[2] = "A professor at heart, Geoff tracks attendance of his class with flawless efficiency.";
                loadArray[3] = "HINT: Geoff holds office hours. In case of CS-induced distress, see him.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era3UpgradeEquipped) { // Tesla stuff
                loadIcon.setImageResource(R.drawable.musk_creation);
                String[] loadArray = new String[4];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "DID YOU KNOW?: At age 9, Elon musk taught himself to code, then read an entire Enyclopedia.";
                loadArray[1] = "DID YOU KNOW?: After only two days of attendance, Elon musk abandoned his pursuance of a Stanford Degree in Physics.";
                loadArray[2] = "DID YOU KNOW?: portions of Iron Man 2 were filmed at SpaceX headquarters.";
                loadArray[3] = "A citizen of three nations, and CEO of two companies, musk is an inventor, engineer, and businessman.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era4UpgradeEquipped) { // Facebook fact/zucc alien
                loadIcon.setImageResource(R.drawable.zucc_creation);
                String[] loadArray = new String[4];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "DID YOU KNOW?: As a teen, Mark Zuckerburg once rejected a hiring offer from Microsoft.";
                loadArray[1] = "DID YOU KNOW?: Zuckerburg dropped out of Harvard so he could manage the site he created, Facebook.";
                loadArray[2] = "DID YOU KNOW?: Zuckerburg is one of the world's youngest billionares.";
                loadArray[3] = "Mark Zuckerburg is a renownded  business man and Computer Scientist.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era5UpgradeEquipped) { // it just works guy, bethesda stuff
                loadIcon.setImageResource(R.drawable.howard_creation);
                String[] loadArray = new String[4];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "Todd Howard is a renowned Video-Game Designer and the top executive at Bethesda Softworks.";
                loadArray[1] = "Todd Howard led the  development of legendary Videogames like Oblivion, Skyrim, and Fallout 3.";
                loadArray[2] = "Howard's most recent creation, Fallout 76, is one of the most heavily-criticized videoGames ever made.";
                loadArray[3] = "Howard is quoted as saying that the purpose of his games were to allow the player to live another life, in another world.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    clickerGameTransition();
                    Log.i("Runner", "Ok charles we're doing it");
                }
            }, loadTime * 1000);//4.5 sec delay before start
            Log.i("Runner", "Charles you left me :(");
        } else {
            clickerGameTransition();
        }

    }

    public void fromClickerGameToMainMenuLoadScreen() {
        setContentView(R.layout.activity_load);
        final ProgressBar pbar = findViewById(R.id.pbar);
        int a = 1000000;
        pbar.setProgress(a);
        //pbar.setVisibility(View.VISIBLE);
        ProgressBar v =  findViewById(R.id.pbar);
        v.getIndeterminateDrawable().setColorFilter(Color.RED,
                android.graphics.PorterDuff.Mode.MULTIPLY);
        if (loadTime != 0) { //If we need to load
            ImageView loadIcon = findViewById(R.id.loadIcon); // Initialize the imageview
            if (era0UpgradeEquipped) { // cs125 logo
                loadIcon.setImageResource(R.drawable.background0);
                String[] loadArray = new String[7];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "HINT: on some some days, Forums > Office Hours. Choose wisely.";
                loadArray[1] = "Legend says that the majority of students didn't finish MP1 in FA19.";
                loadArray[2] = "CS 225 is a more difficult version of CS 125, and thus, its sequel. You've been warned.";
                loadArray[3] = "HINT: On CBTF quizzes, try tackling the code before switching off , periodically, to multiple choice. ";
                loadArray[4] = "HINT: Be weary of neglecting a single grade source in CS125. HW, quizzes, attendance--they are all important.";
                loadArray[5] = "DID YOU KNOW?: Of the Beginner-level CS courses, CS125 is the most difficult. Tell all your friends.";
                loadArray[6] = "Contrary to popular belief, CS125, even as an 100-level CS class, is more difficult then some 200-level classes of different subjects.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era1UpgradeEquipped) { // Turing Machine/turing facts
                loadIcon.setImageResource(R.drawable.turing_machine);
                String[] loadArray = new String[4];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "Alan Turing is considered by most to be the father of modern Computer Science.";
                loadArray[1] = "DID YOU KNOW?: Despite his hand in saving Britain, Turing was prosecuted and castrated due to his sexuality.";
                loadArray[2] = "HINT: To change clicker, access the 'HEROES' menu from the upgrades.";
                loadArray[3] = "DID YOU KNOW?: Alan Turing created the first computer-based chess game.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era2UpgradeEquipped) { // geoff fact & dogs
                loadIcon.setImageResource(R.drawable.chuchu1v1);
                String[] loadArray = new String[5];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "DID YOU KNOW?: Geoffrey Challen received a Phd. In CS from Harvard in 2010.";
                loadArray[1] = "Geoffrey Challen is the CS 125 professor at the University of Illinois, Urbana-Champaign.";
                loadArray[2] = "A professor at heart, Geoff tracks attendance of his class with flawless efficiency.";
                loadArray[3] = "HINT: Geoff holds office hours. In case of CS-induced distress, see him.";
                loadArray[4] = "Ben Nordick, Geoffrey Challen's right hand man, is a master of both biology and software development.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era3UpgradeEquipped) { // Tesla stuff
                loadIcon.setImageResource(R.drawable.musk_creation);
                String[] loadArray = new String[4];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "DID YOU KNOW?: At age 9, Elon musk taught himself to code, then read an entire Enyclopedia.";
                loadArray[1] = "DID YOU KNOW?: After only two days of attendance, Elon musk abandoned his pursuance of a Stanford Degree in Physics.";
                loadArray[2] = "DID YOU KNOW?: portions of Iron Man 2 were filmed at SpaceX headquarters.";
                loadArray[3] = "A citizen of three nations, and CEO of two companies, musk is an inventor, engineer, and businessman.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era4UpgradeEquipped) { // Facebook fact/zucc alien
                loadIcon.setImageResource(R.drawable.zucc_creation);
                String[] loadArray = new String[4];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "DID YOU KNOW?: As a teen, Mark Zuckerburg once rejected an offer to be hired by Microsoft.";
                loadArray[1] = "DID YOU KNOW?: Zuckerburg dropped out of Harvard so he could manage the site he created, Facebook.";
                loadArray[2] = "DID YOU KNOW?: Zuckerburg is one of the world's youngest billionares.";
                loadArray[3] = "Mark Zuckerburg is a renownded  business man and Computer Scientist.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            if (era5UpgradeEquipped) { // it just works guy, bethesda stuff
                loadIcon.setImageResource(R.drawable.howard_creation);
                String[] loadArray = new String[5];
                double random = (Math.random() * (loadArray.length - 1));
                int replacer = (int) Math.ceil(random);
                loadArray[0] = "Todd Howard is a renowned Video-Game Designer and the top executive at Bethesda Softworks,";
                loadArray[1] = "Todd Howard led the  development of legendary Videogames like Oblivion, Skyrim, and Fallout 3.";
                loadArray[2] = "Howard's most recent creation, Fallout 76, is one of the most heavily-criticized videoGames ever made.";
                loadArray[3] = "Howard is quoted as saying that the purpose of his games were to allow the player to live another life, in another world.";
                loadArray[4] = "Howard's most eagerly-awaited release, Elder scrolls 6, is currently being developed by Howards' Bethesda softworks.";
                TextView loadingText = findViewById(R.id.loadingScreenTextView);
                loadingText.setText(loadArray[replacer]);
                loadingText.setTextColor(Color.WHITE);
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainMenuButtonReinitializer();
                    Log.i("Runner", "Ok so we're in the other loading thing");
                }
            }, loadTime * 1000);//4.5 sec delay before start
            Log.i("Runner", "Outside the other loading thing");
        } else {
            mainMenuButtonReinitializer();
        }
    }

    public void settingsMenuTransition() {
        // TODO: 2019-12-07 add transition loading here
        setContentView(R.layout.setting_menu);
        leftClickerGame = true;
        ConstraintLayout set = findViewById(R.id.set);
        set.setBackgroundColor(Color.BLACK);

        TextView settingsMenuTextView = findViewById(R.id.settingsMenuPointsDisplay);
        settingsMenuTextView.setTextColor(Color.WHITE);
        settingsMenuTextView.setText("Your points are: " + points);

        Button muteMusicButton = findViewById(R.id.muteMusicButton);
        muteMusicButton.setBackgroundColor(Color.RED);
        muteMusicButton.setOnClickListener(ef -> {
            muteMusicButton.setText("MUTED");
            if (musicEnabled) {
                musicEnabled = false;
                song.stop();
                Log.i("Music","Music is currently: " + musicEnabled);
            } else {
                muteMusicButton.setText("MUTE MUSIC");
                musicEnabled = true;
                song = MediaPlayer.create(this, R.raw.song);
                song.setLooping(true);
                song.setVolume(1000,1000);
                song.start();
                Log.i("Music","Music is currently: " + musicEnabled);
            }
        });

        MediaPlayer howAboutNo = MediaPlayer.create(this, R.raw.how_about_no);
        Button muteSoundEffectsButton = findViewById(R.id.muteSoundEffectsButton);
        muteSoundEffectsButton.setBackgroundColor(Color.RED);
        muteSoundEffectsButton.setOnClickListener(egg -> {
            if (soundEffectsEnabled) {
                soundEffectsEnabled = false;
            } else {
                soundEffectsEnabled = true;
            }
            howAboutNo.setLooping(false);
            howAboutNo.setVolume(1500,1500);
            howAboutNo.start();
        });
        Button resetPointsButton = findViewById(R.id.resetPointsButton);
        resetPointsButton.setBackgroundColor(Color.RED);
        resetPointsButton.setOnClickListener(eg -> {
            resetPointsButton.setText("POINTS RESET!");
            points = 0;
            settingsMenuTextView.setText("Your points are: " + points);
        });

        Button returnToMainMenu = findViewById(R.id.returnToMainMenuFromSettings);
        returnToMainMenu.setBackgroundColor(Color.RED);
        returnToMainMenu.setOnClickListener(eggwugu -> {
            howAboutNo.stop();
            mainMenuButtonReinitializer();
        });
    }


    /** mainMenuButtonReinitializer
     * Reinitializes all the buttons and stuff on the main menu!
     */
    public void mainMenuButtonReinitializer() {
        // TODO: 2019-12-07 add transition loading here 
        setContentView(R.layout.activity_main);
        goingBackToMainMenu = false;
        leftClickerGame = true;
        TextView title = findViewById(R.id.title);
        title.setTextColor(Color.WHITE);
        title.setBackgroundColor(Color.TRANSPARENT);
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(100); //blink duration
        anim.setStartOffset(2000);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        title.startAnimation(anim);

        VideoView video= (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid);
        video.setVideoURI(uri);
        video.start();


        String[] welcomeArray = new String[6];
        double random = (Math.random() * (welcomeArray.length - 1));
        int replacer = (int) Math.ceil(random);
        welcomeArray[0] = "Welcome!";
        welcomeArray[1] = "Hey guys!";
        welcomeArray[2] = "How about that extra credit?";
        welcomeArray[3] = "Welcome to my CS125 app!";
        welcomeArray[4] = "Hello there :D";
        welcomeArray[5] = "Hey guys, welcome to my CS125 Project walkthrough :)";
        //TextView welcomeText = findViewById(R.id.welcomeText);
       // welcomeText.setText(welcomeArray[replacer]);

        // Reinitializes the secretButton
        Button secretButton = findViewById(R.id.secretButton);
        secretButton.setBackgroundColor(Color.RED);
        secretButton.setOnClickListener(v -> { // Go to secret thankyou screen
            secretNameMethodTransition();
        });


        // Reinitializes the settingsButton
        Button settingsButton = findViewById(R.id.settingsButton);
        settingsButton.setBackgroundColor(Color.RED);
        settingsButton.setOnClickListener(v -> {
            settingsMenuTransition();
        });

        Button info = findViewById(R.id.info);
        info.setBackgroundColor(Color.RED);
        info.setTextColor(Color.WHITE);
        info.setOnClickListener(d -> {
            infoTransition();
        });


        // Reinitializes the feedbackButton
        Button feedbackButton = findViewById(R.id.creditsButton);
        feedbackButton.setBackgroundColor(Color.RED);
        feedbackButton.setOnClickListener(v -> {
            feedbackTransition();
        });

        Button clickerGameButton = findViewById(R.id.clickergameButton);
        clickerGameButton.setBackgroundColor(Color.RED);
        clickerGameButton.setOnClickListener(iw -> {
            attemptatttempt();
        });

        ///TextView menuTextPoints = findViewById(R.id.menuPointsTextView);
        ///menuTextPoints.setTextColor(Color.WHITE);
       /// menuTextPoints.setText("You have: " + points + " points");

        //VideoView video = (VideoView) findViewById(R.id.videoView);
        //Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.vid);
        video.setVideoURI(uri);
        video.start();
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
        //MediaPlayer ring= MediaPlayer.create(MainActivity.this,R.raw.song);
        // ring.start();
    }




    /** Below this block is work that has to do with the clickerGame & its transitions
     * Below this block is work that has to do with the clickerGame & its transitions
     * Below this block is work that has to do with the clickerGame & its transitions
     * Below this block is work that has to do with the clickerGame & its transitions
     * Below this block is work that has to do with the clickerGame & its transitions
     * Below this block is work that has to do with the clickerGame & its transitions
     * Below this block is work that has to do with the clickerGame & its transitions
     */

    public void clickerGameTransition() {
        // TODO: 2019-12-07 add transition loading here
        setContentView(R.layout.clickergame_gaming);
        leftClickerGame = false;
        currentEraButton = findViewById(R.id.clicker);
        MediaPlayer upsong = MediaPlayer.create(this, R.raw.still_feel);
        upsong.setLooping(true);
        upsong.setVolume(1000,1000);
        upsong.stop();

        if (musicEnabled) {
            song.start();
        }

        if (era0UpgradeEquipped) {
            currentEraButton.setImageResource(R.drawable.background0); //CS125 clicker icon
            // TODO: 2019-12-09 CS125 background
        } else if (era1UpgradeEquipped) {
            currentEraButton.setImageResource(R.drawable.background1); //Turing clicker icon
            //ImageView a = findViewById(R.id.imageView5);
            //a.setImageResource(R.drawable.background0);
            // TODO: 2019-12-09 Turing background
        } else if (era2UpgradeEquipped) {
            currentEraButton.setImageResource(R.drawable.background2); //Geoff clicker icon
            // TODO: 2019-12-09 Geoff background
        } else if (era3UpgradeEquipped) {
            currentEraButton.setImageResource(R.drawable.background3); //Musk clicker icon
            // TODO: 2019-12-09 Musk background
        } else if (era4UpgradeEquipped) {
            currentEraButton.setImageResource(R.drawable.background4); //Zucc clicker icon
            // TODO: 2019-12-09 Zucc background
        } else if (era5UpgradeEquipped) {
            currentEraButton.setImageResource(R.drawable.background5); //Todd clicker icon
            // TODO: 2019-12-09 Todd background
        }

        TextView timey = findViewById(R.id.timer);
        timey.setBackgroundColor(Color.RED);
        timey.setTextColor(Color.BLACK);

        TextView finisher = findViewById(R.id.finished);
        finisher.setTextColor(Color.BLACK);
        finisher.setBackgroundColor(Color.RED);



        Button returnToMainMenuButton = findViewById(R.id.gotoMainMenuFromClickerAppButton);
        returnToMainMenuButton.setBackgroundColor(Color.RED);
        returnToMainMenuButton.setTextColor(Color.BLACK);
        returnToMainMenuButton.setOnClickListener(egg -> {
            if (!firstimeforeverything) {
                time.cancel();
            }
            leftClickerGame = true;
            fromClickerGameToMainMenuLoadScreen();
            //mainMenuButtonReinitializer();
        });

        // Sadly this button is gone :(
        Button gotoSettingsButton = findViewById(R.id.gotoSettingsFromClickerAppButton);
        gotoSettingsButton.setBackgroundColor(Color.RED);
        gotoSettingsButton.setVisibility(View.GONE);

        TextView yourPoints = findViewById(R.id.clickerAppPointsTextView);
        yourPoints.setText(" TOTAL POINTS: " + points);
        yourPoints.setTextColor(Color.WHITE);
        yourPoints.setBackgroundColor(Color.TRANSPARENT);

        TextView pointsPerClicks = findViewById((R.id.ppc));
        pointsPerClicks.setTextColor(Color.WHITE);
        pointsPerClicks.setBackgroundColor(Color.TRANSPARENT);
        pointsPerClicks.setText("CLICK VALUE: " + pointsPerClick);

        Button upgradePointsButton = findViewById(R.id.gotoUpgradesFromClickerAppButton);
        upgradePointsButton.setBackgroundColor(Color.RED);
        upgradePointsButton.setOnClickListener(transitionTime -> {
            if (!firstimeforeverything) {
                time.cancel();
            }
            leftClickerGame = true;
            //MediaPlayer upsong = MediaPlayer.create(this, R.raw.upgrades_song);
            //upsong.setLooping(true);
            //upsong.setVolume(1000,1000);
            //upsong.stop();
            clickerUpgradePageOneTransition();
        });

        TextView click = findViewById(R.id.clicks);
        click.setText("POINTS: 0");
        click.setTextColor(Color.BLACK);
        click.setBackgroundColor(Color.RED);

        currentEraButton.setEnabled(false);
        Button attackButton = findViewById(R.id.empezar);
        attackButton.setBackgroundColor(Color.RED);
        attackButton.setTextColor(Color.BLACK);
        song2 = MediaPlayer.create(this, R.raw.upgrades_song);
        song2.setLooping(true);
        song2.setVolume(1000,1000);
        //song2.start();
        attackButton.setOnClickListener(yoyo -> {
            //song2.start();
            attackButton.setEnabled(false);
            clickerGame();
        });
        
    }

    public void clickerUpgradePageOneTransition() {
        setContentView(R.layout.clickerupgrades_menu);
        ConstraintLayout a = (ConstraintLayout) findViewById(R.id.clickup);
        a.setBackgroundColor(Color.BLACK);
        TextView rank = findViewById(R.id.rank);

        song.stop();

        MediaPlayer upsong = MediaPlayer.create(this, R.raw.upgrades_song);
        if (musicEnabled) {
            upsong.setLooping(true);
            upsong.setVolume(1000, 1000);
            upsong.start();
        }

        Button gotoUpgradePageTwo = findViewById(R.id.upgradePageOneToTwo);
        gotoUpgradePageTwo.setTextColor(Color.BLACK);
        gotoUpgradePageTwo.setBackgroundColor(Color.RED);
        gotoUpgradePageTwo.setOnClickListener(letsGo -> {
            upsong.stop();

            MediaPlayer upsong2 = MediaPlayer.create(this, R.raw.upgrades_song2);
            upsong2.setLooping(true);
            upsong2.setVolume(1000,1000);
            retard = MediaPlayer.create(this, R.raw.upgrades_song2);
            retard.setLooping(true);
            retard.setVolume(10000,10000);
            if (musicEnabled) {
                retard.start();
            }
            clickerUpgradePageTwoTransition();
        });

        // X upgrade - costs 100
        Button upgrade1 = findViewById(R.id.upgrade1);
        //upgrade1.setBackgroundColor(Color.BLACK);
        //upgrade1.setTextColor(Color.WHITE);
        if (points < 1000|| upgrade1Purchased) {
            upgrade1.setEnabled(false);
        } else if (points >= 1000 && !upgrade1Purchased){
            upgrade1.setEnabled(true);
        }

        upgrade1.setOnClickListener(e -> {
            pointsPerClick += 2;
            points = points - 1000;
            upgrade1.setEnabled(false);
            upgrade1.setText("UPGRADE ADDED.");
            rank.setText("SYSTEM UPGRADED. CURRENT RANK: Coder Learner\n" + "(CHEAT CODE AWARDED: ABCZ)");
            upgrade1Purchased = true;
        }); if (upgrade1Purchased) {
            rank.setText("SYSTEM UPGRADED. CURRENT RANK: Coder Learner\n" + "(CHEAT CODE AWARDED: ABCZ)");
        }

        // X upgrade - costs 500
        Button upgrade2 = findViewById(R.id.upgrade2);
        //upgrade2.setBackgroundColor(Color.BLACK);
        //upgrade2.setTextColor(Color.WHITE);
        if (points < 2000 || upgrade2Purchased) {
            upgrade2.setEnabled(false);
        } else if (points >= 2000 && !upgrade2Purchased){
            upgrade2.setEnabled(true);
        }
        upgrade2.setOnClickListener(e -> {
            pointsPerClick += 4;
            points = points - 2000;
            upgrade2.setEnabled(false);
            upgrade2.setText("UPGRADE ADDED");
            rank.setText("SYSTEM UPGRADED. CURRENT RANK: Coder Novice\n" + "(CHEAT CODE AWARDED: SWTCW)");
            upgrade2Purchased = true;
        });
        if (upgrade2Purchased) {
            rank.setText("SYSTEM UPGRADED. CURRENT RANK: Coder Novice\n" + "(CHEAT CODE AWARDED: SWTCW)");
        }

        // X upgrade - costs 1000
        Button upgrade3 = findViewById(R.id.upgrade3);
        //upgrade3.setBackgroundColor(Color.BLACK);
       // upgrade3.setTextColor(Color.WHITE);
        if (points < 3000 || upgrade3Purchased) {
            upgrade3.setEnabled(false);
        } else if (points >= 3000 && !upgrade3Purchased){
            upgrade3.setEnabled(true);
        }

            upgrade3.setOnClickListener(e -> {
                pointsPerClick += 6;
                points = points - 3000;
                upgrade3.setEnabled(false);
                upgrade3.setText("UPGRADE ADDED");
                rank.setText("SYSTEM UPGRADED. CURRENT RANK: Coder\n" + "(CHEAT CODE AWARDED: M*ash4078)");
                upgrade3Purchased = true;
            });
        if (upgrade3Purchased) {
            rank.setText("SYSTEM UPGRADED. CURRENT RANK: Coder\n" + "(CHEAT CODE AWARDED: M*ash4078)");
        }

        // X upgrade - costs 2000
        Button upgrade4 = findViewById(R.id.upgrade4);
        //upgrade4.setBackgroundColor(Color.BLACK);
        //upgrade4.setTextColor(Color.BLACK);
        if (points < 4000 || upgrade4Purchased) {
            upgrade4.setEnabled(false);
        } else if (points >= 4000 && !upgrade4Purchased){
            upgrade4.setEnabled(true);
        }
        upgrade4.setOnClickListener(e -> {
            pointsPerClick += 8;
            points = points - 4000;
            upgrade4.setEnabled(false);
            upgrade4.setText("UPGRADE ADDED");
            rank.setText("SYSTEM UPGRADED. CURRENT RANK: Coder Master\n" + "(CHEAT CODE AWARDED: callofhalo)");
            upgrade4Purchased = true;
        });
        if (upgrade4Purchased) {
            rank.setText("SYSTEM UPGRADED. CURRENT RANK: Coder Master\n" + "(CHEAT CODE AWARDED: callofhalo)");
        }

        //i5 upgrade - cost 4k
        Button upgrade5 = findViewById(R.id.upgrade5);
       // upgrade5.setBackgroundColor(Color.BLACK);
       // upgrade5.setTextColor(Color.WHITE);
        if (points < 4000 || upgrade5Purchased) {
            upgrade5.setEnabled(false);
        } else if (points >= 4000 && !upgrade5Purchased) {
            upgrade5.setEnabled(true);
        }
        upgrade5.setOnClickListener(e -> {
            if (loadTime > 7) {
                loadTime = 7;
            }
            Log.i("Loadtime","The loadtime is: " + loadTime);
            points = points - 4000;
            upgrade5.setEnabled(false);
            upgrade5.setText("UPGRADE ADDED");
            upgrade5Purchased = true;
        });

        //i7 upgrade - cost 6k
        Button upgrade6 = findViewById(R.id.upgrade6);
       // upgrade6.setBackgroundColor(Color.BLACK);
       // upgrade6.setTextColor(Color.WHITE);
        if (points < 6000 || upgrade6Purchased) {
            upgrade6.setEnabled(false);
        } else if (points >= 6000 && !upgrade6Purchased) {
            upgrade6.setEnabled(true);
        }
        upgrade6.setOnClickListener(e -> {
            if (loadTime > 5) {
                loadTime = 5;
            }
            Log.i("Loadtime","The loadtime is: " + loadTime);
            points = points - 6000;
            upgrade6.setEnabled(false);
            upgrade6.setText("UPGRADE ADDED");
            upgrade6Purchased = true;
        });

        //i9 upgrade - cost 8k
        Button upgrade7 = findViewById(R.id.upgrade7);
       // upgrade7.setBackgroundColor(Color.BLACK);
       //
        // upgrade7.setTextColor(Color.WHITE);
        if (points < 8000 || upgrade7Purchased) {
            upgrade7.setEnabled(false);
        } else if (points >= 8000 && !upgrade7Purchased) {
            upgrade7.setEnabled(true);
        }
        upgrade7.setOnClickListener(e -> {
            if (loadTime > 3) {
                loadTime = 3;
            }
            Log.i("Loadtime","The loadtime is: " + loadTime);
            points = points - 8000;
            upgrade7.setEnabled(false);
            upgrade7.setText("UPGRADE ADDED");
            upgrade7Purchased = true;
        });

        //threadripper upgrade - cost 10k
        Button upgrade8 = findViewById(R.id.upgrade8);
       // upgrade8.setBackgroundColor(Color.BLACK);
       // upgrade8.setTextColor(Color.WHITE);
        if (points < 10000 || upgrade8Purchased) {
            upgrade8.setEnabled(false);
        } else if (points >= 10000 && !upgrade8Purchased) {
            upgrade8.setEnabled(true);
        }
        upgrade8.setOnClickListener(e -> {
            loadTime = 0;
            Log.i("Loadtime","The loadtime is: " + loadTime);
            points = points - 10000;
            upgrade8.setEnabled(false);
            upgrade8.setText("UPGRADE ADDED");
            upgrade8Purchased = true;
            //Intent a = new Intent()
            //setContentView(R.layout.setting_menu);
        });

        Button returnToClickerApp = findViewById(R.id.returnToClickerApp);
        returnToClickerApp.setBackgroundColor(Color.RED);
        returnToClickerApp.setTextColor(Color.BLACK);
        returnToClickerApp.setOnClickListener(egggg -> {
            upsong.stop();
            song = MediaPlayer.create(this, R.raw.song);
            song.setLooping(true);
            song.setVolume(1000,1000);
            if (musicEnabled) {
                song.start();
            }
            clickerGameTransition();
        });

        TextView upgradeText1 = findViewById(R.id.upgradeText1);
        upgradeText1.setTextColor(Color.WHITE);
        upgradeText1.setText("Java Increases PPC by: 2 (COST:1000PTS)");

        TextView upgradeText2 = findViewById(R.id.upgradeText2);
        upgradeText2.setTextColor(Color.WHITE);
        upgradeText2.setText("Kotlin  Increases PPC by: 4 (COST:2000PTS)");

        TextView upgradeText3 = findViewById(R.id.upgradeText3);
        upgradeText3.setTextColor(Color.WHITE);
        upgradeText3.setText("Python Increases PPC by: 6 (COST:3000PTS)");

        TextView upgradeText4 = findViewById(R.id.upgradeText4);
        upgradeText4.setTextColor(Color.WHITE);
        upgradeText4.setText("C++ Increases PPC by: 8 (COST:4000PTS)");

        TextView upgradeText5 = findViewById(R.id.upgradeText5);
        upgradeText5.setTextColor(Color.WHITE);
        upgradeText5.setText("i5 processor: Loadscreen time = good (COST:4000PTS)");

        TextView upgradeText6 = findViewById(R.id.upgradeText6);
        upgradeText6.setTextColor(Color.WHITE);
        upgradeText6.setText("i7 processor: Loadscreen time = fast (COST:6000PTS)");

        TextView upgradeText7 = findViewById(R.id.upgradeText7);
        upgradeText7.setTextColor(Color.WHITE);
        upgradeText7.setText("i9 processor: Loadscreen time = Amazing (COST:8000PTS)");

        TextView upgradeText8 = findViewById(R.id.upgradeText8);
        upgradeText8.setTextColor(Color.WHITE);
        upgradeText8.setText("ThreadRipper: Loadscreens? What are those? (COST:10000PTS) ");
        // vvv These set the text for if the upgrade has been purchased (global boolean) vvv


        if (upgrade1Purchased) { // Points per click upgrade 1
            upgrade1.setText("PURCHASED");
        } else {
            upgrade1.setText("Java Upgrade"); // TODO: 2019-12-07 Change text here for powerup button name & same for below
        }
        // -------------------------------
        if (upgrade2Purchased) { // Points per click upgrade 2
            upgrade2.setText("PURCHASED");
        } else {
            upgrade2.setText("Kotlin Upgrade");
        }
        // -------------------------------
        if(upgrade3Purchased) { // Points per click upgrade 3
            upgrade3.setText("PURCHASED");
        } else {
            upgrade3.setText("PY. Upgrade");
        }
        // -------------------------------
        if(upgrade4Purchased) { // Points per click upgrade 4
            upgrade4.setText("PURCHASED");
        } else {
            upgrade4.setText("C++ Upgrade");
        }
        // -------------------------------
        if(upgrade5Purchased) { //i5
            upgrade5.setText("PURCHASED");
        } else {
            upgrade5.setText("i5 Processor");
        }
        // -------------------------------
        if(upgrade6Purchased) { //i7
            upgrade6.setText("PURCHASED");
        } else {
            upgrade6.setText("i7 Processor");
        }
        // -------------------------------
        if(upgrade7Purchased) { //i9
            upgrade7.setText("PURCHASED");
        } else {
            upgrade7.setText("i9 Processor");
        }
        // -------------------------------
        if(upgrade8Purchased) { //threadripper
            upgrade8.setText("PURCHASED");
        } else {
            upgrade8.setText("Threadripper");
        }
    }


    public boolean era1UpgradeBought = false;
    public boolean era2UpgradeBought = false;
    public boolean era3UpgradeBought = false;
    public boolean era4UpgradeBought = false;
    public boolean era5UpgradeBought = false;

    public boolean era0UpgradeEquipped = true;
    public boolean era1UpgradeEquipped = false;
    public boolean era2UpgradeEquipped = false;
    public boolean era3UpgradeEquipped = false;
    public boolean era4UpgradeEquipped = false;
    public boolean era5UpgradeEquipped = false;

    public ImageButton currentEraButton;

    MediaPlayer retard;

    public void clickerUpgradePageTwoTransition() {
        //MediaPlayer upsong2 = MediaPlayer.create(this, R.raw.upgrades_song2);
        /*
        MediaPlayer upsong = MediaPlayer.create(this, R.raw.upgrades_song);
        upsong.setLooping(true);
        upsong.setVolume(1000,1000);
        upsong.stop();
         */


        /*
        MediaPlayer upsong2 = MediaPlayer.create(this, R.raw.upgrades_song2);
        upsong2.setLooping(true);
        upsong2.setVolume(1000,1000);
        upsong2.start();
         */




        setContentView(R.layout.clickerupgrades2_menu);
        Button backtoUpgradePageOne = findViewById(R.id.returnToUpgradePg1);
        //backtoUpgradePageOne.setBackgroundColor(Color.RED);
        backtoUpgradePageOne.setOnClickListener(eee -> {
            retard.stop();
            clickerUpgradePageOneTransition();
        });



        Button era0Upgrade = findViewById(R.id.clickerButtonChange0);
        era0Upgrade.setEnabled(false);

        if (!era0UpgradeEquipped) {
            era0Upgrade.setText("Equip?");
            //era0Upgrade.setTextColor(Color.RED);
            era0Upgrade.setEnabled(true);
        }
        era0Upgrade.setOnClickListener(neutral -> {
            era0Upgrade.setText("Equipped");
            //era0Upgrade.setTextColor(Color.RED);
            era0Upgrade.setEnabled(false);
            era0UpgradeEquipped = true; // THIS UPGRADE equipped
            era1UpgradeEquipped = false;
            era2UpgradeEquipped = false;
            era3UpgradeEquipped = false;
            era4UpgradeEquipped = false;
            era5UpgradeEquipped = false;
            clickerUpgradePageTwoTransition();
        });






        // 5k
        Button era1Upgrade = findViewById(R.id.clickerButtonChange1);
        era1Upgrade.setEnabled(false);

        // If the upgrade is not equipped AND the upgrade has been purchased
        if (!era1UpgradeEquipped && era1UpgradeBought) {
            era1Upgrade.setText("Equip?");
            //era1Upgrade.setTextColor(Color.RED);
            era1Upgrade.setEnabled(true);
        }
        // If the upgrade has not been equipped
        if (!era1UpgradeBought && points >= 5000) {
            era1Upgrade.setEnabled(true);
            //era1Upgrade.setTextColor(Color.RED);
            era1Upgrade.setText("BUY?");
        }

        era1Upgrade.setOnClickListener(e -> {
            if (!era1UpgradeBought) {
                //TextView test = findViewById(R.id.test);
               /// test.setText("PURCHASED BABY@!!!!");
                points = points - 5000;
                era1UpgradeBought = true;
                era1UpgradeEquipped = true;
            }
            // equip shenanigans
            era0UpgradeEquipped = false;
            era1UpgradeEquipped = true; // THIS UPGRADE equipped
            era2UpgradeEquipped = false;
            era3UpgradeEquipped = false;
            era4UpgradeEquipped = false;
            era5UpgradeEquipped = false;
            clickerUpgradePageTwoTransition();
        });






        // 10k
        Button era2Upgrade = findViewById(R.id.clickerButtonChange2);
        era2Upgrade.setEnabled(false);

        // If the upgrade is not equipped AND the upgrade has been purchased
        if (!era2UpgradeEquipped && era2UpgradeBought) {
           // era2Upgrade.setTextColor(Color.RED);
            era2Upgrade.setText("Equip?");
            era2Upgrade.setEnabled(true);
        }

        // If the upgrade has not been equipped
        if (!era2UpgradeBought && points >= 10000) {
            era2Upgrade.setEnabled(true);
          //  era2Upgrade.setTextColor(Color.RED);
            era2Upgrade.setText("BUY?");
        }

        era2Upgrade.setOnClickListener(ee -> {
            if (!era2UpgradeBought) {
                points = points - 10000;
                era2UpgradeBought = true;
                era2UpgradeEquipped = true;
            }
            // equip shenanigans
            era0UpgradeEquipped = false;
            era1UpgradeEquipped = false;
            era2UpgradeEquipped = true; // THIS UPGRADE equipped
            era3UpgradeEquipped = false;
            era4UpgradeEquipped = false;
            era5UpgradeEquipped = false;
            clickerUpgradePageTwoTransition();
        });






        // 15k
        Button era3Upgrade = findViewById(R.id.clickerButtonChange3);
        era3Upgrade.setEnabled(false);

        // If the upgrade is not equipped AND the upgrade has been purchased

        if (!era3UpgradeEquipped && era3UpgradeBought) {
            era3Upgrade.setText("Equip?");
            //era1Upgrade.setTextColor(Color.RED);
            era3Upgrade.setEnabled(true);
        }

        // If the upgrade has not been equipped
        if (!era3UpgradeBought && points >= 15000) {
            era3Upgrade.setEnabled(true);
           // era1Upgrade.setTextColor(Color.RED);
            era3Upgrade.setText("BUY?");
        }


        era3Upgrade.setOnClickListener(eee -> {
            if (!era3UpgradeBought) {
                points = points - 15000;
                era3UpgradeBought = true;
                era3UpgradeEquipped = true;
            }
            // equip shenanigans
            era0UpgradeEquipped = false;
            era1UpgradeEquipped = false;
            era2UpgradeEquipped = false;
            era3UpgradeEquipped = true; // THIS UPGRADE equipped
            era4UpgradeEquipped = false;
            era5UpgradeEquipped = false;
            clickerUpgradePageTwoTransition();
        });






        // 20k
        Button era4Upgrade = findViewById(R.id.clickerButtonChange4);
        era4Upgrade.setEnabled(false);

        // If the upgrade is not equipped AND the upgrade has been purchased
        if (!era4UpgradeEquipped && era4UpgradeBought) {
            era4Upgrade.setText("Equip?");
           // era1Upgrade.setTextColor(Color.RED);
            era4Upgrade.setEnabled(true);
        }

        // If the upgrade has not been equipped
        if (!era4UpgradeBought && points >= 20000) {
            era4Upgrade.setEnabled(true);
            //era1Upgrade.setTextColor(Color.RED);
            era4Upgrade.setText("BUY?");
        }

        era4Upgrade.setOnClickListener(eeee -> {
            if (!era4UpgradeBought) {
                points = points - 20000;
                era4UpgradeBought = true;
                era4UpgradeEquipped = true; // THIS UPGRADE equipped
            }
            era0UpgradeEquipped = false;
            era1UpgradeEquipped = false;
            era2UpgradeEquipped = false;
            era3UpgradeEquipped = false;
            era4UpgradeEquipped = true; // THIS UPGRADE equipped
            era5UpgradeEquipped = false;
            clickerUpgradePageTwoTransition();
        });






        // 25k
        Button era5Upgrade = findViewById(R.id.clickerButtonChange5);
        era5Upgrade.setEnabled(false);

        // If the upgrade is not equipped AND the upgrade has been purchased
        if (!era5UpgradeEquipped && era5UpgradeBought) {
            era5Upgrade.setText("Equip?");
           // era1Upgrade.setTextColor(Color.RED);
            era5Upgrade.setEnabled(true);
        }

        // If the upgrade has not been equipped
        if (!era5UpgradeBought && points >= 25000) {
            era5Upgrade.setEnabled(true);
            era5Upgrade.setText("BUY?");
           // era1Upgrade.setTextColor(Color.RED);
        }

        era5Upgrade.setOnClickListener(eeee -> {
            if (!era5UpgradeBought) {
                points = points - 25000;
                era5UpgradeBought = true;
                era5UpgradeEquipped = true;
            }
            // equip shenanigans
            era0UpgradeEquipped = false;
            era1UpgradeEquipped = false;
            era2UpgradeEquipped = false;
            era3UpgradeEquipped = false;
            era4UpgradeEquipped = false;
            era5UpgradeEquipped = true; // THIS UPGRADE equipped
            clickerUpgradePageTwoTransition();
        });

        if (era0UpgradeEquipped) {
            era0Upgrade.setText("Equipped");
        }
        if (era1UpgradeEquipped) {
            era1Upgrade.setText("Equipped");
        }
        if (era2UpgradeEquipped) {
            era2Upgrade.setText("Equipped");
        }
        if (era3UpgradeEquipped) {
            era3Upgrade.setText("Equipped");
        }
        if (era4UpgradeEquipped) {
            era4Upgrade.setText("Equipped");
        }
        if (era5UpgradeEquipped) {
            era5Upgrade.setText("Equipped");
        }
        //TextView

    }




    /** Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     * Below this block is work that has to do with the secretNameMethod & its transitions
     */

    /** secretNameMethodTransition
     * Transitions us from main menu to the secretNameMethod
     */
    public void secretNameMethodTransition() {
        // Sets the screen and ensures that the
        setContentView(R.layout.secret_thankyou);
        song.stop();
        hack = MediaPlayer.create(this, R.raw.hacking);
        hack.setLooping(true);
        hack.setVolume(1000, 1000);
        if (musicEnabled) {
            hack.start();
        }


        Button submitNameButton = findViewById(R.id.submitNameButton);
        EditText thankYouNameInput = findViewById(R.id.thankyouNameInput);
        //thankYouNameInput.setBackgroundColor(Color.RED);

        // When we click submit - call the secretPoints method & clear the text box
        submitNameButton.setOnClickListener(l -> {
            String x = thankYouNameInput.getText().toString();
            secretNameMethod(x);
            thankYouNameInput.setText("");

            // Hides the keyboard after we input something
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager managerABC = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                managerABC.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        thankYouNameInput.setOnClickListener(ef -> {
            thankYouNameInput.setText("");
        });

        //Reinitializes the button to take us back to the main menu
        Button returnToMain = findViewById(R.id.returnToMainMenuFromSecret);
        returnToMain.setOnClickListener(f -> { //Returns to mainMenu from secret thankyou screen
            //setContentView(R.layout.activity_main);
            hack.stop();
            song = MediaPlayer.create(this, R.raw.song);
            song.setLooping(true);
            song.setVolume(1000, 1000);
            if (musicEnabled) {
                song.start();
            }
            System.out.println("!!!!points are: " + points);
            mainMenuButtonReinitializer();
        });
    }


    /** secretNameMethod
     * Takes the parameter and compares it to see if the user is a CA/TA/Staff/Creator of the game
     * @param compareName Passed through textbox that is provided and is used as a comparison.
     */
    public void secretNameMethod(String compareName) {
        boolean CS125Staff = false;
        TextView thankYouMessage = findViewById(R.id.thankyouMessage);
        thankYouMessage.setText(""); // clears the text box each time the screen is accessed & button is pressed
        thankYouMessage.setTextColor(Color.GREEN);
        //thankYouMessage.setBackground();
        String firstcode = "SWTCW";
        String seconndcode = "ABCZ";
        String thirdcode = "M*ash4078";
        String fourthcode = "callofhalo";
        if (compareName.equals(firstcode)) {
            CS125Staff = true;
            thankYouMessage.setText("You're a coder student now. Practice often. Be willing to lose sleep, friends, or even your mind, if necessary. Do what it takes to " +
                    "start yourself off on the right foot. This is the way. Start with java -- you'll thank me later.");
        }
        if (compareName.equals(seconndcode)) {
            thankYouMessage.setText("So you're a coder novice now, well done. Kotlin is where your journey should take you next. Begin thinking algorithmically -- " +
                    "in your sleep, in class, while your date is rambling on about his/her day. Become one with the way of the Algorithm. This is the way.");
            CS125Staff = true;
        }
        if (compareName.equals(thirdcode)) {
            thankYouMessage.setText("Congratulations, you're an offical coder now. Read 'Coders', by Clive Thompson -- it'll help you enter the mindeset of a Coder. " +
                    "Take a break from convoluted syntax and try learning Python. This is the way.");
            CS125Staff = true;
        }
        if(compareName.equals(fourthcode)) {
            thankYouMessage.setText("You've made it. There is little more I can teach you. " +
                    "You have fought hard to become a Coder Master. Learn C++, for it is there that your greatest challenge awaits. Impart upon others the knowledge that I have imparted upon you, " +
                    "and in doing so increase the ranks of our order of those who code. Goodbye now, Coder Master. Remember -- this is the way.");
            CS125Staff = true;
        }
        if (compareName.equals("Nathaniel Zukowski")) {
            points = 1000000000; //bil
            thankYouMessage.setText("DEVELOPER CODE RECOGNIZED: INFINITE POINTS AWARDED.");
            CS125Staff = true;
        }

        if (compareName.equals("Angel Canty")) {
            points = 1000000000; //bil
            thankYouMessage.setText("DEVELOPER CODE RECOGNIZED: INFINITE POINTS AWARDED.");
            CS125Staff = true;
        }

        if (compareName.equals("Geoff Challen")) {
            points = 999999999; // 378M points and some
            Log.i("SecretNameMethod","---------------");
            Log.i("SecretNameMethod","JDHSBKLMDSFNSD our points are: " + points);
            CS125Staff = true;
            thankYouMessage.setText("There's no explanation needed here. Sir, please take some points.\n" + "AWARDED: INFINITE POINTS.");
        }
        if (compareName.equals("Ben Nordick")) {
            points = 39284407; // 39M points and some
            CS125Staff = true;
            thankYouMessage.setText("Ah, the Nordick god of CS Thunder himself. Take some points.\n" + "AWARDED: ALOT OF POINTS.");
            // Thank you message
        }
        String[] taArray = new String[8];
        taArray[0] = "Aabhas Chauhan";
        taArray[1] = "Heather Huynh";
        taArray[2] = "Jishnu Dey";
        taArray[3] = "Mingkun Gao";
        taArray[4] = "Mohammed Hassan";
        taArray[5] = "Nerla Jean-Louis";
        taArray[6] = "Silas Hsu";
        taArray[7] = "Vinith Krishnan";

        String[] caArray = new String[86];
        caArray[0] = "Alan Andrade";
        caArray[1] = "Alex Nickl";
        caArray[2] = "Andre Castaneda";
        caArray[3] = "Ang Li";
        caArray[4] = "Archisha Majee";
        caArray[5] = "Arden Wen";
        caArray[6] = "Ashay Mehta";
        caArray[7] = "Ben Sutter";
        caArray[8] = "Benedict Austriaco";
        caArray[9] = "Blair Wang";
        caArray[10] = "Calen Resh";
        caArray[11] = "Charudutt Kher";
        caArray[12] = "Chris Kull";
        caArray[13] = "Colleen McConnell";
        caArray[14] = "David Ruvinskiy";
        caArray[15] = "Dylan Ott";
        caArray[16] = "Emilia Kedainis";
        caArray[17] = "Eric Liu";
        caArray[18] = "Filip Matasic";
        caArray[19] = "Gabriella Xue";
        caArray[20] = "Geon Kim";
        caArray[21] = "Husnain Raza";
        caArray[22] = "Hyosang Ahn";
        caArray[23] = "Isabel Ruiz";
        caArray[24] = "Isaiah Delgado";
        caArray[25] = "Jack Gentile";
        caArray[26] = "Jack Shao";
        caArray[27] = "Jeffrey Aguirre";
        caArray[28] = "Jessie Yang";
        caArray[29] = "Jiacheng Guo";
        caArray[30] = "Jiahe Su";
        caArray[31] = "Jiaqi Cao";
        caArray[32] = "Jiaqi Cheng";
        caArray[33] = "John Weng";
        caArray[34] = "Jonathan Kim";
        caArray[35] = "Jordan Parker";
        caArray[36] = "Joseph Wang";
        caArray[37] = "Long Phan";
        caArray[38] = "Matt Angel";
        caArray[39] = "Matt Forrest";
        caArray[40] = "Matthew Augustyn";
        caArray[41] = "Matthew Lezak";
        caArray[42] = "Matthew Pham";
        caArray[43] = "Megan Wang";
        caArray[44] = "Mona Liao";
        caArray[45] = "Moritz Kraemer";
        caArray[46] = "Nanyi Yang";
        caArray[47] = "Nima Shafikhani";
        caArray[48] = "Ning Wan";
        caArray[49] = "Pranav Raman";
        caArray[50] = "Quentin Wetzel";
        caArray[51] = "Quinn Collins";
        caArray[52] = "Ridha Alkhabaz";
        caArray[53] = "Rochelle Tham";
        caArray[54] = "Ruisong Li";
        caArray[55] = "Ryan O'Neall";
        caArray[56] = "Sarod Nori";
        caArray[57] = "Shirley Wang";
        caArray[58] = "Shruthi Kondin";
        caArray[59] = "Snehal Somalraju";
        caArray[60] = "Wajid Siddiqui";
        caArray[61] = "Waleed Khan";
        caArray[62] = "Wall Sun";
        caArray[63] = "Wangqi Xiang";
        caArray[64] = "Wei Shen";
        caArray[65] = "Xavier Higgins";
        caArray[66] = "Xiaohan Wang";
        caArray[67] = "Xiaoying Zhu";
        caArray[68] = "Yan Yan";
        caArray[69] = "Yixuan Jia";
        caArray[70] = "Youcheng Cai";
        caArray[71] = "Yu Du";
        caArray[72] = "Yunwen Zhu";
        caArray[73] = "Yuxin Wang";
        caArray[74] = "Zach Hamilton";
        caArray[75] = "Zaitian Wang";
        caArray[76] = "Zepei Li";
        caArray[77] = "Zihan Shan";
        caArray[78] = "Zihan Xu"; // Last CA ----
        caArray[79] = "Ahmad Dinkins"; // Office hours captains vvv
        caArray[80] = "Ajay Tatachar";
        caArray[81] = "Amirthavarshini Sureshbabu";
        caArray[82] = "Catherine Yuan";
        caArray[83] = "Daniel Gleason";
        caArray[84] = "Nikhil Garg";
        caArray[85] = "Rima Bouhal";

        String[] cdArray = new String[12];
        cdArray[0] = "Anush Sandeep";
        cdArray[1] = "Arjun V. Nair";
        cdArray[2] = "Bailey Tincher";
        cdArray[3] = "Harsh Deep";
        cdArray[4] = "Jonathan Shobrook";
        cdArray[5] = "Kyle Begovich";
        cdArray[6] = "Leo Loubieres";
        cdArray[7] = "Max Kopinsky";
        cdArray[8] = "Nick Husin";
        cdArray[9] = "Nitish Poddar";
        cdArray[10] = "Sathwik Pochampally";
        cdArray[11] = "Satvik Sethia";


        for (int i = 0; i < taArray.length; i ++) { // TA thankyou array
            if (taArray[i].equals(compareName)) {
                points = 1000000; //Million points
                 CS125Staff = true;
                thankYouMessage.setText("CS 125 TAs get it done, and in doing so help students get " +
                        "what they've got to get done done as well. In other words--thank you! " + "AWARDED: 1,000,000pts.");
            }
        }
        for (int i = 0; i < caArray.length; i++) { // CA thankyou array
            if (caArray[i].equals(compareName)) {
               CS125Staff = true;
                points = 500000; //Half a million points
                thankYouMessage.setText(" CS 125 CAs are the life jackets keeping students " +
                        "afloat while they learn" +
                        " to swim their way through this ocean of a course. Thank you! AWARDED: 500,000pts.");
            }
        }
        for (int i = 0; i < cdArray.length; i++) { // CD thankyou array
            if (cdArray[i].equals(compareName)) {
               CS125Staff = true;
                points = 500000; //Half a million points
                thankYouMessage.setText("CS125 CDs develop the course! Without you guys, I don't know what this course would've been! Thank you! " +
                        "AWARDED: 500,000pts.");
            }
        }
        // TODO: 2019-12-09 Just double check that the thankyous look clean (added CD array)
        if (CS125Staff == false) {
            ///System.out.println("WRONG PASSCODE. CHEATS DENIED");
           thankYouMessage.setText("WRONG PASSCODE: CHEATS DENIED.");
        }
    }
}