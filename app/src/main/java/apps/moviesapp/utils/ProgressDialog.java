package apps.moviesapp.utils;

import android.content.Context;
import android.graphics.Color;

import apps.moviesapp.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProgressDialog
{
    SweetAlertDialog pDialog;
    public void showDialog(Context cnx)
    {
        pDialog = new SweetAlertDialog(cnx, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#af616f"));
        pDialog.setTitleText(cnx.getResources().getString(R.string.loading));
        pDialog.show();
    }

    public void dismissDialog()
    {
        pDialog.dismissWithAnimation();
    }

}
