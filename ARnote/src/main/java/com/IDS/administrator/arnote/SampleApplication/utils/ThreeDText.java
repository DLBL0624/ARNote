
package com.IDS.administrator.arnote.SampleApplication.utils;

import java.nio.Buffer;
import java.util.ArrayList;

public class ThreeDText extends MeshObject
{

    private Buffer[] mVertBuff = new Buffer[52];
    private Buffer[] mTexCoordBuff = new Buffer[52];
    private Buffer[] mNormBuff = new Buffer[52];
    private Buffer[] mIndBuff = new Buffer[52];
    private ArrayList<float[]> mVert = new ArrayList<float[]>();
    private ArrayList<float[]> mTexCoord = new ArrayList<float[]>();
    private ArrayList<float[]> mNorm = new ArrayList<float[]>();
    private ArrayList<short[]> mInd = new ArrayList<short[]>();

    private int[] indicesNumber = new int[52];
    private int[] verticesNumber = new int[52];

    public ThreeDText()
    {

    }

    public void loadExtraData()
    {
        for(int i =0;i<52;i++){
            setVerts(mVert.get(i),i);
            setTexCoords(mTexCoord.get(i),i);
            setNorms(mNorm.get(i),i);
            setIndices(mInd.get(i),i);
        }
    }



    public void setV(ArrayList<float[]> v)
    {
        this.mVert = v;
    }

    public void setT(ArrayList<float[]> t)
    {
        this.mTexCoord = t;
    }

    public void setN(ArrayList<float[]> n)
    {
        this.mNorm = n;
    }
    public void setI(ArrayList<short[]> i)
    {
        this.mInd = i;
    }

    public ArrayList<float[]> getV()
    {
        return mVert;
    }

    public ArrayList<float[]> getT()
    {
        return mTexCoord;
    }

    public ArrayList<float[]> getN()
    {
        return mNorm;
    }

    public ArrayList<short[]> getI()
    {
        return mInd;
    }

    private void setVerts( float [] STRING_VERIS, int index )
    {

        mVertBuff[index] = fillBuffer(STRING_VERIS);
        verticesNumber[index] = STRING_VERIS.length / 3;
    }


    private void setTexCoords( float [] STRING_TEX_COORDS, int index)
    {

        mTexCoordBuff[index] = fillBuffer(STRING_TEX_COORDS);

    }


    private void setNorms(float [] STRING_NORMS, int index)
    {
        mNormBuff[index] = fillBuffer(STRING_NORMS);
    }


    private void setIndices(short [] STRING_INDICES, int index)
    {
        mIndBuff[index] = fillBuffer(STRING_INDICES);
        indicesNumber[index] = STRING_INDICES.length;
    }

    @Override
    public int getNumObjectIndex(int index)
    {
        return indicesNumber[index];
    }


    @Override
    public int getNumObjectVertex(int index)
    {
        return verticesNumber[index];
    }

    public Buffer[] getmVertBuff(){ return mVertBuff;}
    public Buffer[] getmTexCoordBuff(){ return mTexCoordBuff;}
    public Buffer[] getmNormBuff(){ return mNormBuff;}
    public Buffer[] getmIndBuff(){ return mIndBuff;}

    public int[] getNumIndex()
    {
        return indicesNumber;
    }
    public int[] getNumVertex()
    {
        return verticesNumber;
    }

    public void setmVertBuff(Buffer[] mVertBuff){ this.mVertBuff = mVertBuff;}
    public void setmTexCoordBuff(Buffer[] mTexCoordBuff){ this.mTexCoordBuff = mTexCoordBuff;}
    public void setmNormBuff(Buffer[] mNormBuff){ this.mNormBuff =  mNormBuff;}
    public void setmIndBuff(Buffer[] mIndBuff){ this.mIndBuff =  mIndBuff;}

    public void setNumIndex(int[] indicesNumber)
    {
        this. indicesNumber = indicesNumber;
    }
    public void setNumVertex(int[] verticesNumber)
    {
        this. verticesNumber =  verticesNumber;
    }





    @Override
    public int getNumObjectIndex()
    {
        return indicesNumber[0];
    }


    @Override
    public int getNumObjectVertex()
    {
        return verticesNumber[0];
    }


    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType, int index)
    {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff[index];
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff[index];
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff[index];
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff[index];
            default:
                break;

        }

        return result;
    }

    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType)
    {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff[0];
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff[0];
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff[0];
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff[0];
            default:
                break;

        }

        return result;
    }
}
