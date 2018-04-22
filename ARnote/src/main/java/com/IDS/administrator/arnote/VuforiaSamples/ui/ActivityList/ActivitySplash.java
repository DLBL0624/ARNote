/*===============================================================================
Copyright (c) 2016 PTC Inc. All Rights Reserved.

Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other
countries.
===============================================================================*/

package com.IDS.administrator.arnote.VuforiaSamples.ui.ActivityList;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.IDS.administrator.arnote.Model;
import com.IDS.administrator.arnote.OBJReader;
import com.IDS.administrator.arnote.R;
import com.IDS.administrator.arnote.SampleApplication.utils.ThreeDText;

import java.io.IOException;
import java.util.ArrayList;


public class ActivitySplash extends Activity
{

    private static long SPLASH_MILLIS = 450;

    private String mClassToLaunchPackage = "com.IDS.administrator.arnote";
    private String mClassToLaunch = "com.IDS.administrator.arnote.VuforiaSamples.app.UserDefinedTargets.UserDefinedTargets";

    public ThreeDText tdt;
    public static ArrayList<float[]> mVertBuff = new ArrayList<float[]>();
    public static ArrayList<float[]> mTexCoordBuff = new ArrayList<float[]>();
    public static ArrayList<float[]> mNormBuff = new ArrayList<float[]>();
    public static ArrayList<short[]> mIndBuff = new ArrayList<short[]>();
    public static int[] indicesNumber = new int[53];
    public static int[] verticesNumber = new int[53];
    public Model[] model = new Model[53];

    String[] mutiPermissions = new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION};
    ArrayList<String> needRequest = new ArrayList<>();



    public final static int REQUEST_CODE_CAMERA = 624;
    public final static int REQUEST_CODE_MAP = 519;
    public final static int REQUEST_CODE_ASK_MUTI_PERMISSIONS = 928;
//

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LayoutInflater inflater = LayoutInflater.from(this);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(
                R.layout.splash_screen, null, false);
        addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));


        //traverse all the permissions
        for (String permission : mutiPermissions) {
            int check = checkSelfPermission(permission);
            if (check != PackageManager.PERMISSION_GRANTED) {
                needRequest.add(permission);
                Log.d("RuntimePermissionDemo", "needCheck: " + permission);
            }
        }

        if (needRequest.size() > 0) {
            requestPermissions(needRequest.toArray(new String[needRequest.size()]), REQUEST_CODE_ASK_MUTI_PERMISSIONS);

        } else {
            startARActivity();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {//根据请求码判断是哪一次申请的权限
            case REQUEST_CODE_CAMERA:
                if (grantResults.length > 0) {//grantResults 数组中存放的是授权结果
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//同意授权
                        //授权后做一些你想做的事情，即原来不需要动态授权时做的操作

                    }else {//用户拒绝授权
                        //可以简单提示用户
                        Toast.makeText(ActivitySplash.this, "Need Camera permission", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_MAP:
                if (grantResults.length > 0) {//grantResults 数组中存放的是授权结果
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {//同意授权
                        //授权后做一些你想做的事情，即原来不需要动态授权时做的操作

                    }else {//用户拒绝授权
                        //可以简单提示用户
                        Toast.makeText(ActivitySplash.this, "Need Location permission", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case REQUEST_CODE_ASK_MUTI_PERMISSIONS:
                if (grantResults.length > 0) {
                    // list of permission denied
                    ArrayList<String> deniedPermissions = new ArrayList<>();

                    for (int i=0; i<grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            deniedPermissions.add(permissions[i]);
                            Log.d("RuntimePermissionDemo", "Denied Permission: " + permissions[i]);
                        }
                    }
                    if (deniedPermissions.size() <= 0) {//已全部授权
                        //MapPane.mMap.setMyLocationEnabled(true);
                        startARActivity();
                    }else {//没有全部授权
                        Toast.makeText(ActivitySplash.this, "Missing part of permission", Toast.LENGTH_SHORT).show();


                    }
                }

                break;
            default: super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }





    private void startARActivity()
    {
        Intent i = new Intent();
        i.setClassName(mClassToLaunchPackage, mClassToLaunch);
        load3DData();
        startActivity(i);
    }

    private void load3DData()
    {
        loadData(this);
    }

    private void loadData(Context context){
        for (int i =0; i<26; i++) {

            String str = (char)(97+i)+ "1.obj";
            try {
                System.out.println(i);
                Log.d("threeDobject", "ThreeDText: " + i);
                model[i] = new OBJReader().parserBinStlInAssets(context, str);//read alphabet

            } catch (IOException e) {
                e.printStackTrace();
            }
            setV(model[i].getVerts());
            setT(model[i].getTextureCoor());
            setN(model[i].getVnorms());
            setI(model[i].getIndices());
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
            setV(model[i+26].getVerts());
            setT(model[i+26].getTextureCoor());
            setN(model[i+26].getVnorms());
            setI(model[i+26].getIndices());
        }
        try {
            String str = "TestObj.obj";
            model[52] = new OBJReader().parserBinStlInAssets(context, str);//read alphabet

        } catch (IOException e) {
            e.printStackTrace();
        }
        setV(model[52].getVerts());
        setT(model[52].getTextureCoor());
        setN(model[52].getVnorms());
        setI(model[52].getIndices());
    }

    public void setV(float [] v)
    {
        mVertBuff.add(v);
    }

    public void setT(float [] t)
    {
        mTexCoordBuff.add(t);
    }

    public void setN(float [] n)
    {
        mNormBuff.add(n);
    }

    public void setI(short [] i)
    {
        mIndBuff.add(i);
    }

}

