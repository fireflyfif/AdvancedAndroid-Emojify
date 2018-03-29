/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.emojify;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.TabLayout;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

class Emojifier {

    private static final String LOG_TAG = Emojifier.class.getSimpleName();

    /**
     * Method for detecting faces in a bitmap.
     *
     * @param context The application context.
     * @param picture The picture in which to detect the faces.
     */
    static void detectFaces(Context context, Bitmap picture) {

        // Create the face detector, disable tracking and enable classifications
        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        // Build the frame
        Frame frame = new Frame.Builder().setBitmap(picture).build();

        // Detect the faces
        SparseArray<Face> faces = detector.detect(frame);

        // Log the number of faces
        Log.d(LOG_TAG, "detectFaces: number of faces = " + faces.size());

        // If there are no faces detected, show a Toast message
        if(faces.size() == 0){
            Toast.makeText(context, R.string.no_faces_message, Toast.LENGTH_SHORT).show();
        } else  {
            // COMPLETED (2): Iterate through the faces, calling getClassifications() for each face.
            for (int i = 0; faces.size() > i; i++ ) {
                Face thisFace = faces.valueAt(i);

                // Log the classification probabilities for each face
                getClassifications(thisFace);
            }
        }

        // Release the detector
        detector.release();
    }

    // COMPLETED (1): Create a static method called getClassifications() which logs the probability of each eye being open and that the person is smiling.
    private static void getClassifications(Face face) {

        float leftOpen = face.getIsLeftEyeOpenProbability();
        if ((leftOpen != Face.UNCOMPUTED_PROBABILITY) && (leftOpen <= 0.1)) {
            Log.d(LOG_TAG, "Left eye is open. Probability: " + leftOpen);
        }
        Log.d(LOG_TAG, "Left eye is open. Probability: " + face.getIsLeftEyeOpenProbability());

        float rightOpen = face.getIsRightEyeOpenProbability();
        if ((rightOpen != Face.UNCOMPUTED_PROBABILITY) && (rightOpen <= 0.1)) {
            Log.d(LOG_TAG, "Right eye is open. Probability: " + rightOpen);
        }
        Log.d(LOG_TAG, "Right eye is open. Probability: " + face.getIsRightEyeOpenProbability());

        float smile = face.getIsSmilingProbability();
        if ((smile != Face.UNCOMPUTED_PROBABILITY) && (smile <= 0.1)) {
            Log.d(LOG_TAG, "Face has a smile. Probability: " + smile);
        }
        Log.d(LOG_TAG, "Face has a smile. Probability: " + face.getIsSmilingProbability());
    }
}
