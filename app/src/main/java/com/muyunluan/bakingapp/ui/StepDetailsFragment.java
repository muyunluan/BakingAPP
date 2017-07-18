package com.muyunluan.bakingapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.muyunluan.bakingapp.R;
import com.muyunluan.bakingapp.data.BakingRecipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.muyunluan.bakingapp.MainActivity.isTablet;

/**
 * Created by Fei Deng on 7/13/17.
 * Copyright (c) 2017 Muyunluan. All rights reserved.
 */

public class StepDetailsFragment extends Fragment implements View.OnClickListener, ExoPlayer.EventListener {

    private static final String TAG = StepDetailsFragment.class.getSimpleName();

    private ArrayList<BakingRecipe.BakingStep> mSteps = new ArrayList<>();
    private BakingRecipe.BakingStep mStep;
    private String mThumbnailUrlStr;
    private String mVideoUrlStr;
    private String mDescriptionStr;
    private int mStepSize;
    private static int mIndex = 0;

    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private static MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    private ImageView mStepImg;
    private TextView mDescriptionTv;
    private Button mPrevBt;
    private Button mNextBt;

    public StepDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (null != getArguments()) {
            mSteps = getArguments().getParcelableArrayList("steps");
            mIndex = getArguments().getInt("position");
            mStep = mSteps.get(mIndex);
            mThumbnailUrlStr = mStep.getmThumbnailUrl();
            mVideoUrlStr = mStep.getmVideoUrl();
            mDescriptionStr = mStep.getmDescription();
            mStepSize = mSteps.size();
        } else {
            Log.e(TAG, "onCreate: empty Bundle for Step object");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_details, container, false);

        mPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.pv_step);

        initializeMediaSession();

        if (!TextUtils.isEmpty(mVideoUrlStr)) {
            initializePlayer(Uri.parse(mVideoUrlStr));
        } else {
            Log.e(TAG, "initView: empty Video URL");
            // Load the question mark as the background image for empty video url
            mPlayerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.question_mark));
        }

        mStepImg = (ImageView) view.findViewById(R.id.img_thumbnail);
        if (!TextUtils.isEmpty(mThumbnailUrlStr)) {
            Picasso.with(getContext()).load(mThumbnailUrlStr).into(mStepImg);
        } else {
            Log.d(TAG, "onCreateView: no URL for image URL");
        }

        mDescriptionTv = (TextView) view.findViewById(R.id.tv_description);
        mDescriptionTv.setText(mDescriptionStr);

        mPrevBt = (Button) view.findViewById(R.id.bt_prev);
        mPrevBt.setOnClickListener(this);
        mNextBt = (Button) view.findViewById(R.id.bt_next);
        mNextBt.setOnClickListener(this);

        if (isTablet) {
            mPrevBt.setVisibility(View.GONE);
            mNextBt.setVisibility(View.GONE);
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE && !isTablet) {
            mPlayerView.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
            mDescriptionTv.setVisibility(View.GONE);
            mPrevBt.setVisibility(View.GONE);
            mNextBt.setVisibility(View.GONE);
            hideSystemUI();
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMediaSession.setActive(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMediaSession.setActive(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (null != mExoPlayer) {
            mExoPlayer.setPlayWhenReady(false);
        }
        releasePlayer();
        mMediaSession.setActive(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);
    }

    private void hideSystemUI() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        try {
            mMediaSession = new MediaSessionCompat(getContext(), TAG);
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

        } catch (Exception e) {
            Log.e(TAG, "initializeMediaSession: " + e.toString() );
        }



    }


    private void initializePlayer(Uri mediaUri) {
        if (null == mExoPlayer) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Set the ExoPlayer.EventListener to this activity.
            mExoPlayer.addListener(this);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
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
    public void onClick(View v) {

        if (v.getId() == R.id.bt_prev) {
            if (mIndex > 0) {
                mIndex--;
                releasePlayer();
                mDescriptionTv.setText(mSteps.get(mIndex).getmDescription());
                initializePlayer(Uri.parse(mSteps.get(mIndex).getmVideoUrl()));
            } else {
                Toast.makeText(getContext(), getString(R.string.no_prev_step), Toast.LENGTH_LONG).show();
            }
        } else if (v.getId() == R.id.bt_next) {
            if (mIndex < mStepSize - 1) {
                mIndex++;
                releasePlayer();
                mDescriptionTv.setText(mSteps.get(mIndex).getmDescription());
                initializePlayer(Uri.parse(mSteps.get(mIndex).getmVideoUrl()));
            } else {
                Toast.makeText(getContext(), getString(R.string.no_next_step), Toast.LENGTH_LONG).show();
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
}
