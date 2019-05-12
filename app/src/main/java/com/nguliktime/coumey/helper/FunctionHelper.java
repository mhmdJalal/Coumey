package com.nguliktime.coumey.helper;

import android.content.Context;
import android.graphics.Typeface;

import com.valdesekamdem.library.mdtoast.MDToast;

public class FunctionHelper {
    MDToast toast;
    Context context;

    public void showToastInfo(Context context, String pesan){
        toast  = MDToast.makeText(context, pesan, MDToast.LENGTH_SHORT, MDToast.TYPE_INFO);
        toast.show();
    }

    public void showToastWarning(Context context, String pesan){
        toast  = MDToast.makeText(context, pesan, MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING);
        toast.show();
    }

    public void showToastSuccess(Context context, String pesan){
        toast  = MDToast.makeText(context, pesan, MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS);
        toast.show();
    }

    public void showToastError(Context context, String pesan){
        toast  = MDToast.makeText(context, pesan, MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR);
        toast.show();
    }
}
