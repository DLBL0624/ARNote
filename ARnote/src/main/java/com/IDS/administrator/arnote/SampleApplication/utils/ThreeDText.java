
package com.IDS.administrator.arnote.SampleApplication.utils;

import android.util.Log;

import com.IDS.administrator.arnote.Message;
import com.IDS.administrator.arnote.Model;
import com.IDS.administrator.arnote.OBJReader;
import com.IDS.administrator.arnote.VuforiaSamples.app.UserDefinedTargets.UserDefinedTargets;

import java.io.IOException;
import java.nio.Buffer;

public class ThreeDText extends MeshObject
{

    private Buffer[] mVertBuff = new Buffer[52];
    private Buffer[] mTexCoordBuff = new Buffer[52];
    private Buffer[] mNormBuff = new Buffer[52];
    private Buffer[] mIndBuff = new Buffer[52];
    public Model[] model = new Model[52];
    private int[] indicesNumber = new int[52];
    private int [] verticesNumber = new int[52];
    public Message mess;

    public ThreeDText(UserDefinedTargets context)
    {

        for (int i =0; i<26; i++) {

            String str = (char)(97+i)+ "1.obj";
            try {
                System.out.println(i);
                Log.d("threeDobject", "ThreeDText: " + i);
                model[i] = new OBJReader().parserBinStlInAssets(context, str);//read alphabet

            } catch (IOException e) {
                e.printStackTrace();
            }
            setVerts(model[i].getVerts(),i);
            //for(int r = 0;r<model[i].getVerts().length;r++)Log.d("threeDobject", "Verts: " + r + " " + model[i].getVerts()[r]);
            //Log.d("threeDobject", "Verts:" + model[i].getVerts());
            setTexCoords(model[i].getTextureCoor(),i);
            //for(int r = 0;r<model[i].getTextureCoor().length;r++)Log.d("threeDobject", "TextureCoor: " + r + " " + model[i].getTextureCoor()[r]);
            setNorms(model[i].getVnorms(),i);
            //for(int r = 0;r<model[i].getVnorms().length;r++)Log.d("threeDobject", "Norms: " + r + " " + model[i].getVnorms()[r]);
            setIndices(model[i].getIndices(),i);
            //for(int r = 0;r<model[i].getIndices().length;r++)Log.d("threeDobject", "Indices:"+ r + " " + model[i].getIndices()[r]);
        }
        for (int i =0; i<26; i++) {

            String str = (char)(65+i)+ ".obj";
            try {
                System.out.println(i+26);
                Log.d("threeDobject", "ThreeDText: " + (i+26));
                model[i+26] = new OBJReader().parserBinStlInAssets(context, str);//read alphabet

            } catch (IOException e) {
                e.printStackTrace();
            }
            setVerts(model[i+26].getVerts(),i+26);
            //for(int r = 0;r<model[i].getVerts().length;r++)Log.d("threeDobject", "Verts: " + r + " " + model[i].getVerts()[r]);
            //Log.d("threeDobject", "Verts:" + model[i].getVerts());
            setTexCoords(model[i+26].getTextureCoor(),i+26);
            //for(int r = 0;r<model[i].getTextureCoor().length;r++)Log.d("threeDobject", "TextureCoor: " + r + " " + model[i].getTextureCoor()[r]);
            setNorms(model[i+26].getVnorms(),i+26);
            //for(int r = 0;r<model[i].getVnorms().length;r++)Log.d("threeDobject", "Norms: " + r + " " + model[i].getVnorms()[r]);
            setIndices(model[i+26].getIndices(),i+26);
            //for(int r = 0;r<model[i].getIndices().length;r++)Log.d("threeDobject", "Indices:"+ r + " " + model[i].getIndices()[r]);
        }


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


    public int getNumObjectIndex(int index)
    {
        return indicesNumber[index];
    }


    @Override
    public int getNumObjectVertex(int index)
    {
        return verticesNumber[index];
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
