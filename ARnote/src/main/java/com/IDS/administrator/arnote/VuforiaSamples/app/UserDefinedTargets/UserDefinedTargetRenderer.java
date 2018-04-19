/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.IDS.administrator.arnote.VuforiaSamples.app.UserDefinedTargets;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import com.IDS.administrator.arnote.SampleApplication.SampleAppRenderer;
import com.IDS.administrator.arnote.SampleApplication.SampleAppRendererControl;
import com.IDS.administrator.arnote.SampleApplication.SampleApplicationSession;
import com.IDS.administrator.arnote.SampleApplication.utils.CubeObject;
import com.IDS.administrator.arnote.SampleApplication.utils.CubeShaders;
import com.IDS.administrator.arnote.SampleApplication.utils.SampleUtils;
import com.IDS.administrator.arnote.SampleApplication.utils.Teapot;
import com.IDS.administrator.arnote.SampleApplication.utils.Texture;
import com.IDS.administrator.arnote.SampleApplication.utils.ThreeDText;
import com.vuforia.Device;
import com.vuforia.Matrix44F;
import com.vuforia.Renderer;
import com.vuforia.State;
import com.vuforia.Tool;
import com.vuforia.TrackableResult;
import com.vuforia.Vuforia;

import java.util.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


// The renderer class for the ImageTargetsBuilder sample. 
public class UserDefinedTargetRenderer implements GLSurfaceView.Renderer, SampleAppRendererControl
{
    private static final String LOGTAG = "UDTRenderer";

    private SampleApplicationSession vuforiaAppSession;
    private SampleAppRenderer mSampleAppRenderer;

    private boolean mIsActive = false;
    
    private Vector<Texture> mTextures;
    private int shaderProgramID;
    private int vertexHandle;
    private int textureCoordHandle;
    private int mvpMatrixHandle;
    private int texSampler2DHandle;
    
    // Constants:
    static final float kObjectScale = 3.f;//
    
    private Teapot mTeapot;
    private CubeObject mCube;
    private ThreeDText mThreeDText;
    // Reference to main activity
    private UserDefinedTargets mActivity;

    private float[] mMatrixCurrent=     //原始矩阵
            {1,0,0,0,
                    0,1,0,0,
                    0,0,1,0,
                    0,0,0,1};
    
    public UserDefinedTargetRenderer(UserDefinedTargets activity,
        SampleApplicationSession session)
    {
        mActivity = activity;
        vuforiaAppSession = session;
        Log.d("Initial???", "UserDefinedTargetRenderer: ");
        // SampleAppRenderer used to encapsulate the use of RenderingPrimitives setting
        // the device mode AR/VR and stereo mode
        mSampleAppRenderer = new SampleAppRenderer(this, mActivity, Device.MODE.MODE_AR, false, 10f, 5000f);
    }
    
    
    // Called when the surface is created or recreated.
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config)
    {
        Log.d(LOGTAG, "GLRenderer.onSurfaceCreated");
        
        // Call Vuforia function to (re)initialize rendering after first use
        // or after OpenGL ES context was lost (e.g. after onPause/onResume):
        vuforiaAppSession.onSurfaceCreated();

        mSampleAppRenderer.onSurfaceCreated();
    }
    
    
    // Called when the surface changed size.
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height)
    {
        Log.d(LOGTAG, "GLRenderer.onSurfaceChanged");
        
        // Call function to update rendering when render surface
        // parameters have changed:
        mActivity.updateRendering();
        
        // Call Vuforia function to handle render surface size changes:
        vuforiaAppSession.onSurfaceChanged(width, height);

        // RenderingPrimitives to be updated when some rendering change is done
        mSampleAppRenderer.onConfigurationChanged(mIsActive);

        // Call function to initialize rendering:
        initRendering();
    }


    public void setActive(boolean active)
    {
        mIsActive = active;

        if(mIsActive)
            mSampleAppRenderer.configureVideoBackground();
    }


    // Called to draw the current frame.
    @Override
    public void onDrawFrame(GL10 gl)
    {
        if (!mIsActive)
            return;

        // Call our function to render content from SampleAppRenderer class
        mSampleAppRenderer.render();
    }


    // The render function called from SampleAppRendering by using RenderingPrimitives views.
    // The state is owned by SampleAppRenderer which is controlling it's lifecycle.
    // State should not be cached outside this method.
    public void renderFrame(State state, float[] projectionMatrix)//draw the objects
    {
        // Renders video background replacing Renderer.DrawVideoBackground()
        mSampleAppRenderer.renderVideoBackground();
        
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glEnable(GLES20.GL_CULL_FACE);

        // Render the RefFree UI elements depending on the current state
        mActivity.refFreeFrame.render();
        
        // Did we find any trackables this frame?
        for (int tIdx = 0; tIdx < state.getNumTrackableResults(); tIdx++)
        {
            // Get the trackable:
            TrackableResult trackableResult = state.getTrackableResult(tIdx);
            Matrix44F modelViewMatrix_Vuforia = Tool
                .convertPose2GLMatrix(trackableResult.getPose());
            float[] modelViewMatrix = modelViewMatrix_Vuforia.getData();
            
            float[] modelViewProjection = new float[16];
            Matrix.translateM(modelViewMatrix, 0, 0.0f, 0.0f, kObjectScale);
            Matrix.scaleM(modelViewMatrix, 0, kObjectScale, kObjectScale,
                kObjectScale);//adjust the size

            Matrix.rotateM(modelViewMatrix, 0, 90, 0, 0, 1);
            Matrix.multiplyMM(modelViewProjection, 0, projectionMatrix, 0, modelViewMatrix, 0);
            
            GLES20.glUseProgram(shaderProgramID);


//            // @TODO 3dtext
//
//            GLES20.glVertexAttribPointer(vertexHandle, 3, GLES20.GL_FLOAT,
//                    false, 0, mThreeDText.getVertices());
//
//            GLES20.glEnableVertexAttribArray(vertexHandle);
//
//            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false,
//                    modelViewProjection, 0);
//
//            GLES20.glUniform1i(texSampler2DHandle, 0);

//            //glRotatef(90,0,0,1);
//            GLES20.glDrawElements(GLES20.GL_TRIANGLES,
//                    mThreeDText.getNumObjectIndex(), GLES20.GL_UNSIGNED_SHORT,
//                    mThreeDText.getIndices());
//
//            GLES20.glDisableVertexAttribArray(vertexHandle);

//

//            //TEAPOT @ToDO teapot edit
//            GLES20.glVertexAttribPointer(vertexHandle, 3, GLES20.GL_FLOAT,
//                false, 0, mTeapot.getVertices());
//
//            GLES20.glVertexAttribPointer(textureCoordHandle, 2,
//                    GLES20.GL_FLOAT, false, 0, mTeapot.getTexCoords());
//
//             //@todo texture switch
//            GLES20.glEnableVertexAttribArray(vertexHandle);
//            GLES20.glEnableVertexAttribArray(textureCoordHandle);
//
//            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,
//                mTextures.get(0).mTextureID[0]);
//            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false,
//                modelViewProjection, 0);
//            GLES20.glUniform1i(texSampler2DHandle, 0);
//
//
//            GLES20.glDrawElements(GLES20.GL_TRIANGLES,
//                mTeapot.getNumObjectIndex(), GLES20.GL_UNSIGNED_SHORT,
//                mTeapot.getIndices());
//
//            GLES20.glDisableVertexAttribArray(vertexHandle);
//            GLES20.glDisableVertexAttribArray(textureCoordHandle);
//
//            SampleUtils.checkGLError("UserDefinedTargets renderFrame");



            //TEAPOT @ToDO THREEdTEXT
            GLES20.glVertexAttribPointer(vertexHandle, 3, GLES20.GL_FLOAT,
                    false, 0, mThreeDText.getVertices());

            GLES20.glVertexAttribPointer(textureCoordHandle, 2,
                    GLES20.GL_FLOAT, false, 0, mThreeDText.getTexCoords());

            //@todo texture switch
            GLES20.glEnableVertexAttribArray(vertexHandle);
            GLES20.glEnableVertexAttribArray(textureCoordHandle);

            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,
                    mTextures.get(0).mTextureID[0]);
            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false,
                    modelViewProjection, 0);
            GLES20.glUniform1i(texSampler2DHandle, 0);



            //Matrix.scaleM(mMatrixCurrent,0,3.0f,2.0f,3.0f);
            GLES20.glDrawElements(GLES20.GL_TRIANGLES,
                    mThreeDText.getNumObjectIndex(), GLES20.GL_UNSIGNED_SHORT,
                    mThreeDText.getIndices());

            GLES20.glDisableVertexAttribArray(vertexHandle);
            GLES20.glDisableVertexAttribArray(textureCoordHandle);

            SampleUtils.checkGLError("UserDefinedTargets renderFrame");

//            //TEAPOT @ToDO THREEdTEXT
//            GLES20.glVertexAttribPointer(vertexHandle, 3, GLES20.GL_FLOAT,
//                    false, 0, mCube.getVertices());
//
//            GLES20.glVertexAttribPointer(textureCoordHandle, 2,
//                    GLES20.GL_FLOAT, false, 0, mCube.getTexCoords());
//
//            //@todo texture switch
//            GLES20.glEnableVertexAttribArray(vertexHandle);
//            GLES20.glEnableVertexAttribArray(textureCoordHandle);
//
//            GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
//            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,
//                    mTextures.get(0).mTextureID[0]);
//            GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false,
//                    modelViewProjection, 0);
//            GLES20.glUniform1i(texSampler2DHandle, 0);
//
//
//
//            Matrix.scaleM(mMatrixCurrent,0,3.0f,2.0f,3.0f);
//            GLES20.glDrawElements(GLES20.GL_TRIANGLES,
//                    mCube.getNumObjectIndex(), GLES20.GL_UNSIGNED_SHORT,
//                    mCube.getIndices());
//
//            GLES20.glDisableVertexAttribArray(vertexHandle);
//            GLES20.glDisableVertexAttribArray(textureCoordHandle);
//
//            SampleUtils.checkGLError("UserDefinedTargets renderFrame");
        }
        
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        
        Renderer.getInstance().end();
    }
    
    
    private void initRendering()
    {
        Log.d(LOGTAG, "initRendering");
        
        mTeapot = new Teapot();// the shape shown
        mThreeDText = new ThreeDText(mActivity);
        mCube = new CubeObject();
        // Define clear color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, Vuforia.requiresAlpha() ? 0.0f
            : 1.0f);


        // Now generate the OpenGL texture objects and add settings
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
        textureCoordHandle = GLES20.glGetAttribLocation(shaderProgramID,
            "vertexTexCoord");
        mvpMatrixHandle = GLES20.glGetUniformLocation(shaderProgramID,
            "modelViewProjectionMatrix");
        texSampler2DHandle = GLES20.glGetUniformLocation(shaderProgramID,
            "texSampler2D");
    }
    
    
    public void setTextures(Vector<Texture> textures)
    {

        mTextures = textures;
   }
    
}