/*===============================================================================
Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.agile.ireality.vuforia.CloudRecognition;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.DisplayMetrics;

import com.agile.ireality.log.AppLog;
import com.agile.ireality.vuforia.SampleApplication.SampleApplicationSession;
import com.agile.ireality.vuforia.utils.QuadObject;
import com.agile.ireality.vuforia.utils.CubeShaders;
import com.agile.ireality.vuforia.utils.SampleMath;
import com.agile.ireality.vuforia.utils.SampleUtils;
import com.agile.ireality.vuforia.utils.Texture;
import com.qualcomm.vuforia.ImageTarget;
import com.qualcomm.vuforia.Matrix44F;
import com.qualcomm.vuforia.Renderer;
import com.qualcomm.vuforia.State;
import com.qualcomm.vuforia.Tool;
import com.qualcomm.vuforia.TrackableResult;
import com.qualcomm.vuforia.VIDEO_BACKGROUND_REFLECTION;
import com.qualcomm.vuforia.Vec2F;
import com.qualcomm.vuforia.Vec3F;
import com.qualcomm.vuforia.Vuforia;

import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


// The renderer class for the CloudRecoActivity sample.
public class CloudRecoRenderer implements GLSurfaceView.Renderer
{
    private static final String TAG = "CloudRecoRenderer";
    SampleApplicationSession vuforiaAppSession;
    private QuadObject cubeObject = new QuadObject();
    // Constants:
    final static float kCubeScaleX = 120.0f * 1.00f / 2.0f;
    final static float kCubeScaleY = 120.0f * 1.00f / 2.0f;
    final static float kCubeScaleZ = 120.0f * 1.00f / 2.0f;


    private static final float OBJECT_SCALE_FLOAT = 3.0f;
    Vec3F targetPositiveDimensions;

    private int shaderProgramID;
    private int vertexHandle;
    private int normalHandle;
    private int textureCoordHandle;
    private int mvpMatrixHandle;
    private int texSampler2DHandle;

    private Vector<Texture> mTextures;


    private CloudRecoActivity mActivity;
    private Matrix44F modelViewMatrix_Vuforia;
    private MetaData mMetaData;

    public CloudRecoRenderer(SampleApplicationSession session, CloudRecoActivity activity)
    {
        vuforiaAppSession = session;
        mActivity = activity;
        targetPositiveDimensions = new Vec3F();
    }


    // Called when the surface is created or recreated.
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        // Call function to initialize rendering:
        initRendering();

        // Call Vuforia function to (re)initialize rendering after first use
        // or after OpenGL ES context was lost (e.g. after onPause/onResume):
        vuforiaAppSession.onSurfaceCreated();
    }


    // Called when the surface changed size.
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        // Call Vuforia function to handle render surface size changes:
        vuforiaAppSession.onSurfaceChanged(width, height);
    }


    // Called to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl)
    {
        // Call our function to render content
        renderFrame();
    }


    // Function for initializing the renderer.
    private void initRendering()
    {
        // Define clear color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, Vuforia.requiresAlpha() ? 0.0f
                : 1.0f);

        for (Texture t : mTextures)
        {
            GLES20.glGenTextures(1, t.mTextureID, 0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, t.mTextureID[0]);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D,
                    GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA,
                    t.mWidth, t.mHeight, 0, GLES20.GL_RGBA,
                    GLES20.GL_UNSIGNED_BYTE, t.mData);
        }

        shaderProgramID = SampleUtils.createProgramFromShaderSrc(
                CubeShaders.CUBE_MESH_VERTEX_SHADER,
                CubeShaders.CUBE_MESH_FRAGMENT_SHADER);

        vertexHandle = GLES20.glGetAttribLocation(shaderProgramID,
                "vertexPosition");
        normalHandle = GLES20.glGetAttribLocation(shaderProgramID,
                "vertexNormal");
        textureCoordHandle = GLES20.glGetAttribLocation(shaderProgramID,
                "vertexTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgramID,
                "modelViewProjectionMatrix");
        texSampler2DHandle = GLES20.glGetUniformLocation(shaderProgramID,
                "texSampler2D");
    }


    // The render function.
    private void renderFrame()
    {

        float temp[] = { 0.0f, 0.0f, 0.0f };
        targetPositiveDimensions.setData(temp);

        // Clear color and depth buffer
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Get the state from Vuforia and mark the beginning of a rendering
        // section
        State state = Renderer.getInstance().begin();

        // Explicitly render the Video Background
        Renderer.getInstance().drawVideoBackground();

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_BLEND);
        if (Renderer.getInstance().getVideoBackgroundConfig().getReflection() == VIDEO_BACKGROUND_REFLECTION.VIDEO_BACKGROUND_REFLECTION_ON)
            GLES20.glFrontFace(GLES20.GL_CW);  // Front camera
        else
            GLES20.glFrontFace(GLES20.GL_CCW);   // Back camera

        // Did we find any trackables this frame?
        if (state.getNumTrackableResults() > 0)
        {
            // Gets current trackable result
            TrackableResult trackableResult = state.getTrackableResult(0);

            if (trackableResult == null)
            {
                return;
            }

            mActivity.stopFinderIfStarted();

            // Renders the Augmentation View with the 3D Book data Panel
            renderAugmentation(trackableResult);

        }
        else
        {
            mActivity.startFinderIfStopped();
        }
        GLES20.glDisable(GLES20.GL_CULL_FACE);
        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        Renderer.getInstance().end();
    }


    private void renderAugmentation(TrackableResult trackableResult)
    {
        ImageTarget imageTarget = (ImageTarget) trackableResult
                .getTrackable();

        float temp[] = { 0.0f, 0.0f, 0.0f};
        targetPositiveDimensions = imageTarget.getSize();
        temp[0] = targetPositiveDimensions.getData()[0] / 2.0f;
        temp[1] = targetPositiveDimensions.getData()[1] / 2.0f;
        targetPositiveDimensions.setData(temp);

        float w = (temp[0]>temp[1]?temp[1]:temp[0]) * 0.9f;

        modelViewMatrix_Vuforia = Tool
                .convertPose2GLMatrix(trackableResult.getPose());
        float[] modelViewMatrix = modelViewMatrix_Vuforia.getData();

        float[] modelViewProjection = new float[16];
        Matrix.scaleM(modelViewMatrix, 0, w,
                w,
                0);
        Matrix.multiplyMM(modelViewProjection, 0, vuforiaAppSession
                .getProjectionMatrix().getData(), 0, modelViewMatrix, 0);

        GLES20.glUseProgram(shaderProgramID);

        // Draw the cube:

        // We must detect if background reflection is active and adjust the
        // culling direction.
        // If the reflection is active, this means the post matrix has been
        // reflected as well, therefore standard counter clockwise face
        // culling will result in "inside out" models.

        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
        GLES20.glVertexAttribPointer(vertexHandle, 3, GLES20.GL_FLOAT,
                false, 0, cubeObject.getVertices());
        GLES20.glVertexAttribPointer(normalHandle, 3, GLES20.GL_FLOAT,
                false, 0, cubeObject.getNormals());
        GLES20.glVertexAttribPointer(textureCoordHandle, 2,
                GLES20.GL_FLOAT, false, 0, cubeObject.getTexCoords());

        GLES20.glEnableVertexAttribArray(vertexHandle);
        GLES20.glEnableVertexAttribArray(normalHandle);
        GLES20.glEnableVertexAttribArray(textureCoordHandle);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,
                mTextures.get(mMetaData.getContentType().getNumericType()).mTextureID[0]);
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false,
                modelViewProjection, 0);
        GLES20.glUniform1i(texSampler2DHandle, 0);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                cubeObject.getNumObjectIndex(), GLES20.GL_UNSIGNED_SHORT,
                cubeObject.getIndices());


        SampleUtils.checkGLError("CloudRecoActivity renderFrame");
    }


    boolean isTapOnScreenInsideTarget(float x, float y)
    {
        // Here we calculate that the touch event is inside the target
        Vec3F intersection;
        // Vec3F lineStart = new Vec3F();
        // Vec3F lineEnd = new Vec3F();

        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        intersection = SampleMath.getPointToPlaneIntersection(SampleMath
                        .Matrix44FInverse(vuforiaAppSession.getProjectionMatrix()),
                modelViewMatrix_Vuforia, metrics.widthPixels, metrics.heightPixels,
                new Vec2F(x, y), new Vec3F(0, 0, 0), new Vec3F(0, 0, 1));

        // The target returns as pose the center of the trackable. The following
        // if-statement simply checks that the tap is within this range
        if ((intersection.getData()[0] >= -(targetPositiveDimensions
                .getData()[0]))
                && (intersection.getData()[0] <= (targetPositiveDimensions
                .getData()[0]))
                && (intersection.getData()[1] >= -(targetPositiveDimensions
                .getData()[1]))
                && (intersection.getData()[1] <= (targetPositiveDimensions
                .getData()[1])))
            return true;
        else
            return false;
    }

    public void setTextures(Vector<Texture> textures)
    {
        mTextures = textures;
    }

    public void onTouchEvent(String metadata, float x, float y) {
        AppLog.i(TAG, "X = "+x);
        AppLog.i(TAG, "Y = "+y);
    }

    public void setMetaDate(MetaData metaData) {
        AppLog.i(TAG, "setMetaDate called");
        this.mMetaData = metaData;
    }
}
