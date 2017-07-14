package com.muyunluan.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.muyunluan.bakingapp.R;

public class StepDetailsActivity extends AppCompatActivity {

    private static final String TAG = StepDetailsActivity.class.getSimpleName();

//    private ArrayList<BakingRecipe.BakingStep> mSteps = new ArrayList<>();
//    private BakingRecipe.BakingStep mStep;
//    private String mVideoUrlStr;
//    private String mDescriptionStr;
//    private int mStepSize;
//    private static int mIndex = 0;
//
//    private SimpleExoPlayer mExoPlayer;
//    private SimpleExoPlayerView mPlayerView;
//    private static MediaSessionCompat mMediaSession;
//    private PlaybackStateCompat.Builder mStateBuilder;
//
//    private TextView mDescriptionTv;
//    private Button mPrevBt;
//    private Button mNextBt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

//        if (null != getIntent()) {
//            mSteps = getIntent().getExtras().getParcelableArrayList("steps");
//            mIndex = getIntent().getExtras().getInt("position");
//            mStep = mSteps.get(mIndex);
//            mVideoUrlStr = mStep.getmVideoUrl();
//            mDescriptionStr = mStep.getmDescription();
//            mStepSize = mSteps.size();
//            initView();
//        } else {
//            Log.e(TAG, "onCreate: empty Bundle for Step object");
//        }

        if (null != getIntent()) {
            Log.i(TAG, "onCreate: has bundle");
            Bundle args = new Bundle();
            if (getIntent().hasExtra("position")) {
                Log.i(TAG, "onCreate: has desired info");
                args.putInt("position", getIntent().getExtras().getInt("position"));
                args.putParcelableArrayList("steps", getIntent().getExtras().getParcelableArrayList("steps"));
            } else {
                Log.e(TAG, "onCreate: no desired info found");
            }
        } else {
            Log.e(TAG, "onCreate: empty Bundle for Step object");
        }
        getSupportFragmentManager().beginTransaction().add(R.id .frame_step_details, new StepDetailsFragment()).commit();

    }


    /*
    private void initView() {
        mPlayerView = (SimpleExoPlayerView) findViewById(R.id.pv_step);

        initializeMediaSession();

        if (!TextUtils.isEmpty(mVideoUrlStr)) {
            initializePlayer(Uri.parse(mVideoUrlStr));
        } else {
            Log.e(TAG, "initView: empty Video URL");
            // Load the question mark as the background image for empty video url
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.question_mark));
        }

        mDescriptionTv = (TextView) findViewById(R.id.tv_description);
        mDescriptionTv.setText(mDescriptionStr);

        mPrevBt = (Button) findViewById(R.id.bt_prev);
        mPrevBt.setOnClickListener(this);
        mNextBt = (Button) findViewById(R.id.bt_next);
        mNextBt.setOnClickListener(this);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            mPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            mDescriptionTv.setVisibility(View.GONE);
            mPrevBt.setVisibility(View.GONE);
            mNextBt.setVisibility(View.GONE);
            hideSystemUI();
        }

    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(this, TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }


    private void initializePlayer(Uri mediaUri) {
        if (null == mExoPlayer) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(this, "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this, userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer() {
        if (null != mExoPlayer) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        } else {
            Log.e(TAG, "releasePlayer: empty ExoPlayer");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaSession.setActive(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mExoPlayer) {
            mExoPlayer.setPlayWhenReady(false);
        }
        mMediaSession.setActive(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.bt_prev) {
            if (mIndex > 0) {
                mIndex--;
                releasePlayer();
                mDescriptionTv.setText(mSteps.get(mIndex).getmDescription());
                initializePlayer(Uri.parse(mSteps.get(mIndex).getmVideoUrl()));
            } else {
                Toast.makeText(this, getString(R.string.no_prev_step), Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.bt_next) {
            if (mIndex < mStepSize - 1) {
                mIndex++;
                releasePlayer();
                mDescriptionTv.setText(mSteps.get(mIndex).getmDescription());
                initializePlayer(Uri.parse(mSteps.get(mIndex).getmVideoUrl()));
            } else {
                Toast.makeText(this, getString(R.string.no_next_step), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {
        public MediaReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }

    */
}
