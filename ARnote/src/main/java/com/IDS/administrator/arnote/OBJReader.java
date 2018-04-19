package com.IDS.administrator.arnote;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OBJReader {
    private OBJLoadListener objLoadListener;

    public Model parserBinStlInSDCard(String path) throws IOException {
        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);
        return parserBinObj(fis);
    }

    public Model parserBinStlInAssets(Context context, String fileName) throws IOException {
        Log.d("parserBinStlInAssets", "parserBinStlInAssets: In!");
        InputStream is = context.getAssets().open(fileName);

        return parserBinObj(is);
    }

    //更改的地方 读取的格式不对
    // TODO: 2018/3/6 load completed
    public Model parserBinObj(InputStream in) throws IOException {

        Model model = new Model();
        String temps = null;

        float Mult = 100;

        ArrayList<Float> alv=new ArrayList<Float>();
        ArrayList<Float> alvText = new ArrayList<Float>();
        ArrayList<Float> alvNormal = new ArrayList<Float>();
        ArrayList<Float> alvResult = new ArrayList<Float>();
        ArrayList<Short> alvIndices = new ArrayList<Short>();

        if (objLoadListener != null)
            objLoadListener.onstart();

        try {
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);


            //skip the filename (80bits)
            //in.skip(80);//file name
        /*
            Obj
        */
            while ((temps = br.readLine()) != null) {
                String[] tempsa = temps.split("[ ]+");
                if(tempsa[0].trim().equals("v"))
                {
                    alv.add(Float.parseFloat(tempsa[1])*Mult);
                    alv.add(Float.parseFloat(tempsa[2])*Mult);
                    alv.add(Float.parseFloat(tempsa[3])*Mult);
                }
                else if(tempsa[0].trim().equals("vt"))//if vertex texture
                {
                    alvText.add(Float.parseFloat(tempsa[1])*Mult);
                    alvText.add(Float.parseFloat(tempsa[2])*Mult);
                    alvText.add(Float.parseFloat(tempsa[3])*Mult);
                }
                else if(tempsa[0].trim().equals("vn"))//if vertex normal
                {
                    alvNormal.add(Float.parseFloat(tempsa[1]));
                    alvNormal.add(Float.parseFloat(tempsa[2]));
                    alvNormal.add(Float.parseFloat(tempsa[3]));
                }
                else if(tempsa[0].trim().equals("f"))
                {//此行为三角形面
                    int index=Integer.parseInt(tempsa[1].split("/")[0])-1;
                    alvResult.add(alv.get(3*index));
                    alvResult.add(alv.get(3*index+1));
                    alvResult.add(alv.get(3*index+2));

                    int k = Integer.parseInt(tempsa[1].split("/")[0]) - 1;
                    alvIndices.add((short)k);//vertex indices;

                    index=Integer.parseInt(tempsa[2].split("/")[0])-1;
                    alvResult.add(alv.get(3*index));
                    alvResult.add(alv.get(3*index+1));
                    alvResult.add(alv.get(3*index+2));

                    k = Integer.parseInt(tempsa[2].split("/")[0]) - 1;
                    alvIndices.add((short)k);//vertex indices;

                    index=Integer.parseInt(tempsa[3].split("/")[0])-1;
                    alvResult.add(alv.get(3*index));
                    alvResult.add(alv.get(3*index+1));
                    alvResult.add(alv.get(3*index+2));

                    k = Integer.parseInt(tempsa[3].split("/")[0]) - 1;
                    alvIndices.add((short)k);//vertex indices;


                }
            }
            //生成顶点数组
            int resultSize = alvResult.size();
            int vertexSize = alv.size();
            int textureSize = alvText.size();

            int normalSize = alvNormal.size();
            int indicesSize = alvIndices.size();
            float[] vResult=new float[resultSize];
            float[] vVertex= new float[vertexSize];

            float[] vText= new float[textureSize];
            float[] vNorm=new float[normalSize];
            short[] vIndices=new short[indicesSize];



            for(int i=0;i<resultSize;i++)
            {
                vResult[i]=alvResult.get(i);
            }
            for(int i=0;i<vertexSize;i++)
            {
                vVertex[i]=alv.get(i);
            }
            for(int i=0;i<textureSize;i++)
            {
                vText[i]=alvText.get(i);
            }
            for(int i=0;i<normalSize;i++)
            {
                vNorm[i]=alvNormal.get(i);
            }
            for(int i=0;i<indicesSize;i++)
            {
                vIndices[i]=alvIndices.get(i);
            }


            model.setVerts(vVertex);
            model.setTexture(vText);
            model.setVnorms(vNorm);
            //model.setRemarks(remarks);
            model.setIndices(vIndices);

            br.close();
            isr.close();

        }
        catch(Exception e)
        {
            Log.d("load error", "load error");
            e.printStackTrace();
        }

        in.close();





        //
        /*
        byte[] bytes1 = new byte[80];
        in.read(bytes1);// number of triangles
        String str1 = new String(bytes1);
        Log.d("parserBinObj", "parserBinObj66666: " + str1);

        byte[] bytes = new byte[4];
        in.read(bytes);// number of triangles
        String str = new String(bytes);
        Log.v("ByteInput",str);
        int facetCount = Util.byte4ToInt(bytes, 0);
        model.setFacetCount(facetCount);
        if (facetCount == 0) {
            in.close();
            return model;
        }
        byte[] facetBytes = new byte[50 * facetCount];// the attribute and position of each triangle
        in.read(facetBytes);
        Log.d("parserBinStlInAssets", "facetCount:" + facetCount);
        in.close();
        parseModel(model, facetBytes);
        Log.d("parserBinStlInAssets", "Comein:");
        if (objLoadListener != null)
            objLoadListener.onFinished();
            */
        return model;
    }


    /**
     * 解析模型数据，包括顶点数据、法向量数据、所占空间范围等
     */
//    private void parseModel(Model model, byte[] facetBytes) {
//        int facetCount = model.getFacetCount();
//        /**
//         *  每个三角面片占用固定的50个字节,50字节当中：
//         *  三角片的法向量：（1个向量相当于一个点）*（3维/点）*（4字节浮点数/维）=12字节
//         *  三角片的三个点坐标：（3个点）*（3维/点）*（4字节浮点数/维）=36字节
//         *  最后2个字节用来描述三角面片的属性信息
//         * **/
//        // 保存所有顶点坐标信息,一个三角形3个顶点，一个顶点3个坐标轴
//        float[] verts = new float[facetCount * 3 * 3];
//        // 保存所有三角面对应的法向量位置，
//        // 一个三角面对应一个法向量，一个法向量有3个点
//        // 而绘制模型时，是针对需要每个顶点对应的法向量，因此存储长度需要*3
//        // 又同一个三角面的三个顶点的法向量是相同的，
//        // 因此后面写入法向量数据的时候，只需连续写入3个相同的法向量即可
//        float[] vnorms = new float[facetCount * 3 * 3];
//        //保存所有三角面的属性信息
//        short[] remarks = new short[facetCount];
//
//        int stlOffset = 0;
//
//        try {
//            for (int i = 0; i < facetCount; i++) {
//                if (objLoadListener != null) {
//                    objLoadListener.onLoading(i, facetCount);
//                }
//                for (int j = 0; j < 4; j++) {// one normal + 3 vertes
//                    float x = Util.byte4ToFloat(facetBytes, stlOffset);
//                    float y = Util.byte4ToFloat(facetBytes, stlOffset + 4);
//                    float z = Util.byte4ToFloat(facetBytes, stlOffset + 8);
//                    stlOffset += 12;//skip the
//
//                    if (j == 0) {//法向量
//
//                        vnorms[i * 9] = x;
//                        vnorms[i * 9 + 1] = y;
//                        vnorms[i * 9 + 2] = z;
//                        vnorms[i * 9 + 3] = x;
//                        vnorms[i * 9 + 4] = y;
//                        vnorms[i * 9 + 5] = z;
//                        vnorms[i * 9 + 6] = x;
//                        vnorms[i * 9 + 7] = y;
//                        vnorms[i * 9 + 8] = z;
//                    } else {//三个顶点
//
//                        verts[i * 9 + (j - 1) * 3] = x; //
//                        verts[i * 9 + (j - 1) * 3 + 1] = y; //
//                        verts[i * 9 + (j - 1) * 3 + 2] = z; //
//
//                        //记录模型中三个坐标轴方向的最大最小值
//                        if (i == 0 && j == 1) {
//                            model.minX = model.maxX = x;
//                            model.minY = model.maxY = y;
//                            model.minZ = model.maxZ = z;
//                        } else {
//                            model.minX = Math.min(model.minX, x);
//                            model.minY = Math.min(model.minY, y);
//                            model.minZ = Math.min(model.minZ, z);
//                            model.maxX = Math.max(model.maxX, x);
//                            model.maxY = Math.max(model.maxY, y);
//                            model.maxZ = Math.max(model.maxZ, z);
//                        }
//
//
//
//
//                    }
//                }
//                short r = Util.byte2ToShort(facetBytes, stlOffset);
//                Log.d("parseModel", "parseModel: " + r);
//                stlOffset = stlOffset + 2;
//                remarks[i] = r;
//            }
//        } catch (Exception e) {
//            if (objLoadListener != null) {
//                objLoadListener.onFailure(e);
//            } else {
//                e.printStackTrace();
//            }
//        }
//        //将读取的数据设置到Model对象中
//        short[] indices = IndicesSet(model,verts);
//        model.setVerts(verts);
//        model.setVnorms(vnorms);
//        model.setRemarks(remarks);
//        model.setIndices(indices);
//    }



//    public short[] IndicesSet(Model model,float[] verts)
//    {
//        int facetCount = model.getFacetCount();
//        short[] indices = new short[facetCount* 3];// indices
//        float[][] indices_check = new float[facetCount*3][3];
//        short ind = 1;
//        boolean check = false;
//
//        indices_check[0][0] = verts[0];
//        indices_check[0][1] = verts[1];
//        indices_check[0][2] = verts[2];
//
//        for(int i =1; i< (verts.length/3);i++)//each point
//        {
//            for(short t = 0; t<i;t++) {//check the repeat indices
//                if (indices_check[t][0] == verts[i*3] && indices_check[t][1] == verts[i*3+1] && indices_check[t][2] == verts[i*3+2]) {
//                    indices[i] = indices[t];
//                    Log.d("parseModel:", "parseModel: indices[" + i + "]" + indices[i]);
//                    check = true;
//                    break;
//                }
//            }
//
//            if(!check){
//                indices[i] = ind;
//                Log.d("parseModel:" , "parseModel: indices[" + i + "]" + indices[i]);
//                indices_check[i][0]=verts[i*3];
//                indices_check[i][1]=verts[i*3+1];
//                indices_check[i][2]=verts[i*3+2];
//                ind++;
//
//            }
//            check = false;
//        }
//        return indices;
//    }

    public static interface OBJLoadListener {
        void onstart();

        void onLoading(int cur, int total);

        void onFinished();

        void onFailure(Exception e);
    }
    }



