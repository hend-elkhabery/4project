package com.example.hend.bakingapp.UI.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hend.bakingapp.Models.StepsModel;
import com.example.hend.bakingapp.R;
import com.example.hend.bakingapp.Utils.ConstantsUtil;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class step_details_Fragment extends Fragment {
    private StepsModel step;
    Button btnpre, btnnext;
    TextView tv_step_title, tv_step_description;
    PlayerView playerView;
    SimpleExoPlayer player;
    ImageView ivplaceholder;

    Context context;
    public static final String STEP_URI = "step_uri";
    public static final String STEP_VIDEO_POSITION = "step_video_position";
    public static final String STEP_PLAY_WHEN_READY = "step_play_when_ready";
    public static final String STEP_PLAY_WINDOW_INDEX = "step_play_window_index";
    public static final String STEP_SINGLE = "step_single";
    private final String TAG = step_details_Activity.class.getSimpleName();

    StepsModel mStep;
    Uri mVideoUri;
    boolean mShouldPlayWhenReady = true;
    long mPlayerPosition;
    int mWindowIndex;
    private String mVideoThumbnail;
    Bitmap mVideoThumbnailImage;

    public step_details_Fragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_details_, container, false);

        btnpre = view.findViewById(R.id.btn_previous_step);
        btnnext = view.findViewById(R.id.btn_next_step);
        playerView = view.findViewById(R.id.fl_player_container);
        tv_step_title = view.findViewById(R.id.tv_step_title);
        tv_step_description = view.findViewById(R.id.tv_step_description);
        ivplaceholder = view.findViewById(R.id.iv_video_placeholder);
        context = this.getContext();
        Bundle bundle = this.getArguments();


        if (savedInstanceState != null) {

            mStep = savedInstanceState.getParcelable(STEP_SINGLE);
            mShouldPlayWhenReady = savedInstanceState.getBoolean(STEP_PLAY_WHEN_READY);
            mPlayerPosition = savedInstanceState.getLong(STEP_VIDEO_POSITION);
            mWindowIndex = savedInstanceState.getInt(STEP_PLAY_WINDOW_INDEX);
            mVideoUri = Uri.parse(savedInstanceState.getString(STEP_URI));

        } else {

            if (getArguments() != null) {
                // Get arguments
                mStep = bundle.getParcelable(ConstantsUtil.ARG_STEP_POSITION);


                ivplaceholder.setVisibility(View.GONE);
                playerView.setVisibility(View.VISIBLE);

                // If has no video
                if (mStep.getVideoURL().isEmpty()) {
                    // Check thumbnail
                    if (mStep.getThumbnailURL().isEmpty()) {
                        // If no video or thumbnail, use placeholder image
                        playerView.setUseArtwork(true);
                        ivplaceholder.setVisibility(View.VISIBLE);
                        playerView.setUseController(false);
                    } else {
                        ivplaceholder.setVisibility(View.GONE);
                        playerView.setVisibility(View.VISIBLE);
                        mVideoThumbnail = mStep.getThumbnailURL();
                        mVideoThumbnailImage = ThumbnailUtils.createVideoThumbnail(mVideoThumbnail, MediaStore.Video.Thumbnails.MICRO_KIND);
                        playerView.setUseArtwork(true);
                        playerView.setDefaultArtwork(mVideoThumbnailImage);
                    }
                } else {
                    mVideoUri = Uri.parse(mStep.getVideoURL());
                }
            }


        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void initializePlayer(Uri videoUri) {

        tv_step_description.setText(mStep.getDescription());
        tv_step_title.setText(mStep.getShortDescription());

        if (player == null) {

            player = ExoPlayerFactory.newSimpleInstance(getActivity(),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            playerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getActivity(), getString(R.string.app_name));
            MediaSource mediaSource = new ExtractorMediaSource(videoUri,
                    new DefaultDataSourceFactory(getActivity(), userAgent),
                    new DefaultExtractorsFactory(),
                    null,
                    null);

            if (mPlayerPosition != C.TIME_UNSET) {
                player.seekTo(mPlayerPosition);
            }
            // Prepare the player with the source.
            player.prepare(mediaSource);
            player.setPlayWhenReady(mShouldPlayWhenReady);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateStartPosition();
        outState.putString(STEP_URI, mStep.getVideoURL());
        outState.putParcelable(STEP_SINGLE, mStep);
        outState.putLong(STEP_VIDEO_POSITION, mPlayerPosition);
        outState.putBoolean(STEP_PLAY_WHEN_READY, mShouldPlayWhenReady);
    }

    private void updateStartPosition() {
        if (player != null) {
            mShouldPlayWhenReady = player.getPlayWhenReady();
            mWindowIndex = player.getCurrentWindowIndex();
            mPlayerPosition = player.getCurrentPosition();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(mVideoUri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer(mVideoUri);
        }
        if (player != null) {
            player.setPlayWhenReady(mShouldPlayWhenReady);
            player.seekTo(mPlayerPosition);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player != null) {
            updateStartPosition();
            if (Util.SDK_INT <= 23) {
                releasePlayer();
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            updateStartPosition();
            if (Util.SDK_INT > 23) {
                releasePlayer();
            }
        }
    }

    private void releasePlayer() {
        if (player != null) {
            updateStartPosition();
            player.stop();
            player.release();
            player = null;
        }
    }


}
