
package com.IDS.administrator.arnote.SampleApplication.utils;

import com.IDS.administrator.arnote.Message;
import com.IDS.administrator.arnote.Model;
import com.IDS.administrator.arnote.STLReader;
import com.IDS.administrator.arnote.VuforiaSamples.app.UserDefinedTargets.UserDefinedTargets;

import java.io.IOException;
import java.nio.Buffer;

public class ThreeDText extends MeshObject
{

    private Buffer mVertBuff;
    //private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;
    private Model[] model;
    private int indicesNumber = 0;
    private int verticesNumber = 0;
    public Message mess;
    private int[] deCodeString = new int[100];

    public ThreeDText(UserDefinedTargets context)
    {

        for (int i =0; i<1; i++) {
            String str = (char)(97+i)+ "1.stl";
            try {
                model[i] = new STLReader().parserBinStlInAssets(context, str);//read alphabet

            } catch (IOException e) {
                e.printStackTrace();
            }
            setVerts(model[i].getVerts(),i);
            setNorms(model[i].getVnorms(),i);
            setIndices(model[i].getRemarks(),i);
        }


    }

    /*
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
    */

    private void setVerts( float [] STRING_VERIS, int index )
    {

        mVertBuff = fillBuffer(STRING_VERIS);
        verticesNumber = STRING_VERIS.length / 3;
    }


//    private void setTexCoords()
//    {
//
//        mTexCoordBuff = fillBuffer(TEAPOT_TEX_COORDS);
//
//    }


    private void setNorms(float [] STRING_NORMS, int index)
    {
        mNormBuff = fillBuffer(STRING_NORMS);
    }


    private void setIndices(short [] STRING_INDICES, int index)
    {
        mIndBuff = fillBuffer(STRING_INDICES);
        indicesNumber = STRING_INDICES.length;
    }


    public int getNumObjectIndex()
    {
        return indicesNumber;
    }


    @Override
    public int getNumObjectVertex()
    {
        return verticesNumber;
    }


    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType)
    {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
//            case BUFFER_TYPE_TEXTURE_COORD:
//                result = mTexCoordBuff;
//                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
            default:
                break;

        }

        return result;
    }

}
