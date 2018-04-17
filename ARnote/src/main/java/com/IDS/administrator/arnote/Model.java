package com.IDS.administrator.arnote;

import java.nio.FloatBuffer;

public class Model {
    private int facetCount;
    private float[] verts;
    private float[] vnorms;
    private short[] remarks;

    //array vector -> Buffer
    private FloatBuffer vertBuffer;

    private FloatBuffer vnormBuffer;

    float maxX;
    float minX;
    float maxY;
    float minY;
    float maxZ;
    float minZ;

    //get the center point
    public Point getCentrePoint() {
        float cx = (maxX - minX) / 2;
        float cy = (maxY - minY) / 2;
        float cz = (maxZ - minZ) / 2;
        return new Point(cx, cy, cz);
    }

    //get the radius of the cover ball
    public float getR() {
        float dx = (maxX - minX);
        float dy = (maxY - minY);
        float dz = (maxZ - minZ);
        float max = dx;
        if (dy > max)
            max = dy;
        if (dz > max)
            max = dz;
        return max;
    }

    public int getFacetCount() {
        return facetCount;
    }

    public void setFacetCount(int facetCount) {
        this.facetCount = facetCount;
    }

    public float[] getVerts() {
        return verts;
    }

    public void setVerts(float[] verts) {
        this.verts = verts;
        vertBuffer = Util.floatToBuffer(verts);
    }

    public FloatBuffer getVertBuffer() {

        return vertBuffer;
    }

    public FloatBuffer getVnormBuffer() {
        return vnormBuffer;
    }

    public float[] getVnorms() {
        return vnorms;
    }

    public void setVnorms(float[] vnorms) {
        this.vnorms = vnorms;
        vnormBuffer = Util.floatToBuffer(vnorms);
    }

    public short[] getRemarks() {
        return remarks;
    }

    public void setRemarks(short[] remarks) {
        this.remarks = remarks;
    }


}
