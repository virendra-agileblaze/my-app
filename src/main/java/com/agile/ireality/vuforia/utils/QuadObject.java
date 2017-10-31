/*===============================================================================
Copyright (c) 2012-2014 Qualcomm Connected Experiences, Inc. All Rights Reserved.

Vuforia is a trademark of PTC Inc., registered in the United States and other 
countries.
===============================================================================*/

package com.agile.ireality.vuforia.utils;

import java.nio.Buffer;


public class QuadObject extends MeshObject {
    // Data for drawing the 3D plane as overlay
    private static final double cubeVertices[] = {
            -1.00f, -1.00f, 0.00f,
            1.00f, -1.00f, 0.00f,
            1.00f, 1.00f, 0.00f,
            -1.00f, 1.00f, 0.00f
    };


    private static final double cubeTexcoords[] = {
            0, 0, 1, 0, 1, 1, 0, 1
    };


    private static final double cubeNormals[] = {
            0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1
    };

    private static final short cubeIndices[] = {
            0, 1, 2, 0, 2, 3
    };

    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;

    public QuadObject() {
        mVertBuff = fillBuffer(cubeVertices);
        mTexCoordBuff = fillBuffer(cubeTexcoords);
        mNormBuff = fillBuffer(cubeNormals);
        mIndBuff = fillBuffer(cubeIndices);
    }

    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType) {
        Buffer result = null;
        switch (bufferType) {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
            default:
                break;
        }
        return result;
    }

    @Override
    public int getNumObjectVertex() {
        return cubeVertices.length / 3;
    }

    @Override
    public int getNumObjectIndex() {
        return cubeIndices.length;
    }
}