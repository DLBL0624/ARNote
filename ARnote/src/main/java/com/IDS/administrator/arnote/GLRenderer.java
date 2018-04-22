package com.IDS.administrator.arnote;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GLRenderer implements GLSurfaceView.Renderer {

    private Model[] model = new Model[26];
    private Point mCenterPoint;
    private Point eye = new Point(0, 0, -3);
    private Point up = new Point(0, 1, 0);
    private Point center = new Point(0, 0, 0);
    private float mScalef = 1f;
    private float mDegree = 0;
    private int[] deCodeString = new int[100];

    public Message mes = new Message(0,0,"helloworld",0);


    public GLRenderer(Context context) {

        for (int i =0; i<26; i++) {
            String str = (char)(97+i)+ "1.stl";
            try {
                model[i] = new OBJReader().parserBinStlInAssets(context, str);//read alphabet

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void rotate(float degree) {
        mDegree = degree;
    }


    public void loadString(Message mes) {
        String str = mes.getMessage();
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 'a':
                    deCodeString[i] = 0;
                    break;
                case 'b':
                    deCodeString[i] = 1;
                    break;
                case 'c':
                    deCodeString[i] = 2;
                    break;
                case 'd':
                    deCodeString[i] = 3;
                    break;
                case 'e':
                    deCodeString[i] = 4;
                    break;
                case 'f':
                    deCodeString[i] = 5;
                    break;
                case 'g':
                    deCodeString[i] = 6;
                    break;
                case 'h':
                    deCodeString[i] = 7;
                    break;
                case 'i':
                    deCodeString[i] = 8;
                    break;
                case 'j':
                    deCodeString[i] = 9;
                    break;
                case 'k':
                    deCodeString[i] = 10;
                    break;
                case 'l':
                    deCodeString[i] = 11;
                    break;
                case 'm':
                    deCodeString[i] = 12;
                    break;
                case 'n':
                    deCodeString[i] = 13;
                    break;
                case 'o':
                    deCodeString[i] = 14;
                    break;
                case 'p':
                    deCodeString[i] = 15;
                    break;
                case 'q':
                    deCodeString[i] = 16;
                    break;
                case 'r':
                    deCodeString[i] = 17;
                    break;
                case 's':
                    deCodeString[i] = 18;
                    break;
                case 't':
                    deCodeString[i] = 19;
                    break;
                case 'u':
                    deCodeString[i] = 20;
                    break;
                case 'v':
                    deCodeString[i] = 21;
                    break;
                case 'w':
                    deCodeString[i] = 22;
                    break;
                case 'x':
                    deCodeString[i] = 23;
                    break;
                case 'y':
                    deCodeString[i] = 24;
                    break;
                case 'z':
                    deCodeString[i] = 25;
                    break;
            }
        }
        deCodeString[str.length()] = '\0';
    }

    @Override
    public void onDrawFrame(GL10 gl) {

        loadString(mes);


        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);


        gl.glLoadIdentity();// 重置当前的模型观察矩阵


        gl.glColor4f(0.0f,1.0f,1.0f,1.0f);

        //眼睛对着原点看
        /*GLU.gluLookAt(gl, eye.x, eye.y, eye.z, center.x,
                center.y, center.z, up.x, up.y, up.z);
        */
        //Inverse the model
        gl.glRotatef(180, 0, -1, 0); //x,y,z
        //将模型放缩到View刚好装下
        gl.glScalef(mScalef, mScalef, mScalef);
        //把模型移动到原点
        gl.glTranslatef(-mCenterPoint.x, -mCenterPoint.y,
                -mCenterPoint.z);


        //===================begin==============================//

        //允许给每个顶点设置法向量
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        // 允许设置顶点
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        // 允许设置颜色


        for(int i = 0;deCodeString[i] != '\0'; i++) {
            gl.glTranslatef(-(model[deCodeString[i]].getR() - 1) / 2, 0, 0);

        }
        for(int i = 0;deCodeString[i] != '\0'; i++) {
            //设置法向量数据源
            gl.glNormalPointer(GL10.GL_FLOAT, 0, model[deCodeString[i]].getVnormBuffer());
            // 设置三角形顶点数据源
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, model[deCodeString[i]].getVertBuffer());

            // 绘制三角形
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, model[deCodeString[i]].getFacetCount() * 3);

            gl.glTranslatef(model[deCodeString[i]] .getR()-1,0,0);

        }
        // Cancel the vertex
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        // Cancel the normal vector
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);

        //=====================end============================//

    }


    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        // 设置OpenGL场景的大小,(0,0)表示窗口内部视口的左下角，(width, height)指定了视口的大小
        gl.glViewport(0, 0, width, height);

        gl.glMatrixMode(GL10.GL_PROJECTION); // 设置投影矩阵
        gl.glLoadIdentity(); // 设置矩阵为单位矩阵，相当于重置矩阵
        GLU.gluPerspective(gl, 45.0f, ((float) width) / height, 1f, 100f);// 设置透视范围

        //以下两句声明，以后所有的变换都是针对模型(即我们绘制的图形)
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();


    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glClearDepthf(1.0f); //
        gl.glDepthFunc(GL10.GL_LEQUAL); //
        gl.glShadeModel(GL10.GL_SMOOTH);// shade -> GL_SMOOTH


        float r;
        for(int i = 0; i<26; i++) {
            r = model[i].getR();
            //calculate the scale
            mScalef = 0.05f / r;
            mCenterPoint = model[i].getCentrePoint();
        }

    }
}