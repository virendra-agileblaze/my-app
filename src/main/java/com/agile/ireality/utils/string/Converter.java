package com.agile.ireality.utils.string;

import com.qualcomm.vuforia.Matrix44F;

/**
 * Created by sarath on 11/2/16.
 */
public class Converter {

    public static String toString(Matrix44F matrix44F){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0;i<matrix44F.getData().length; i++){
            if(i==0)
                stringBuilder.append("[");
            stringBuilder.append(matrix44F.getData()[i]);
            if(i!=0 && (i+1)%4==0) {
                stringBuilder.append("], [");
            }else if(i+1 == matrix44F.getData().length) {
                stringBuilder.append("]");
            }else {
                stringBuilder.append(", ");
            }

        }
        return stringBuilder.toString();
    }
}
