package com.asdabholkar.snapkart.activities.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asdabholkar.snapkart.R;
import com.asdabholkar.snapkart.activities.base.BaseTopDrawerActivity;
import com.asdabholkar.snapkart.application.SnapKartApp;
import com.asdabholkar.snapkart.utilities.ClarifaiUtil;
import com.flipboard.bottomsheet.BottomSheetLayout;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.input.image.ClarifaiImage;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import dagger.Lazy;

import static android.R.attr.bitmap;

public class HomeActivity extends BaseTopDrawerActivity {

    public static final int CAMERA_REQUEST = 100;

    @Inject Lazy<ClarifaiClient> clarifaiClient;

    @BindView(R.id.bottom_sheet) BottomSheetLayout bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SnapKartApp.from(this).getServicesComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setupToolbar();
        setupDrawer();
        setupViews();
    }

    @Override
    public void setupToolbar() {
        super.setupToolbar();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public void setupViews() {

    }

    @OnClick({R.id.fab, R.id.img1, R.id.img2, R.id.img3, R.id.img4})
    public void startImageCapture() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    @Override
    protected void onStart() {
        super.onStart();

        setDrawerSelectedItem(R.id.drawer_item_home);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch(requestCode) {
            case CAMERA_REQUEST:
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                byte[] imageBytes = outStream.toByteArray();
                onImagePicked(imageBytes);
                break;
        }
    }

    private void onImagePicked(@NonNull final byte[] imageBytes) {

        new AsyncTask<Void, Void, ClarifaiResponse<List<ClarifaiOutput<Concept>>>>() {
            @Override protected ClarifaiResponse<List<ClarifaiOutput<Concept>>> doInBackground(Void... params) {
                // The default Clarifai model that identifies concepts in images
                final ConceptModel generalModel = clarifaiClient.get().getDefaultModels().generalModel();

                // Use this model to predict, with the image that the user just selected as the input
                return generalModel.predict()
                        .withInputs(ClarifaiInput.forImage(ClarifaiImage.of(imageBytes)))
                        .executeSync();
            }

            @Override protected void onPostExecute(ClarifaiResponse<List<ClarifaiOutput<Concept>>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(HomeActivity.this, "Error while contacting API", Toast.LENGTH_LONG).show();
                    return;
                }
                final List<ClarifaiOutput<Concept>> predictions = response.get();
                if (predictions.isEmpty()) {
                    Toast.makeText(HomeActivity.this, "No results from API", Toast.LENGTH_LONG).show();
                    return;
                }
                launchBottomSheet(predictions.get(0).data().get(0).name());
            }
        }.execute();
    }

    private void launchBottomSheet(final String prediction) {
        View view = LayoutInflater.from(HomeActivity.this).inflate(R.layout.bottom_sheet, bottomSheet, false);
        TextView textViewClosestMatch = ButterKnife.findById(view, R.id.text_view_closest_match);
        ImageView amazon = ButterKnife.findById(view, R.id.amazon);
        ImageView flipkart = ButterKnife.findById(view, R.id.flipkart);
        amazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://flipkart.com/search?q=" + prediction;
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.setToolbarColor(ContextCompat.getColor(HomeActivity.this, R.color.dark)).build();
                customTabsIntent.launchUrl(HomeActivity.this, Uri.parse(url));
            }
        });
        flipkart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://flipkart.com/search?q=" + prediction;
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.setToolbarColor(ContextCompat.getColor(HomeActivity.this, R.color.dark)).build();
                customTabsIntent.launchUrl(HomeActivity.this, Uri.parse(url));
            }
        });
        textViewClosestMatch.setText(prediction);
        bottomSheet.showWithSheetView(view);
    }
}
