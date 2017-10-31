package com.agile.ireality.vuforia.CloudRecognition;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.agile.ireality.R;

/**
 * Created by sarath on 11/2/16.
 */
@SuppressLint("ValidFragment")
public class TextDialog extends DialogFragment {

    private static final String LOGTAG = "TextDialog";

    private TextView textView;
    private ImageView imgCopy;

    private TextDialog() { /*empty*/ }

    public static TextDialog newInstance() {
        return new TextDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String msg =  getArguments().getString("message");

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_text, null);
        textView = (TextView)view.findViewById(R.id.txtMessage);
        imgCopy = (ImageView)view.findViewById(R.id.imgCopy);
        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!textView.getText().toString().isEmpty()) {
                    ClipboardManager clipboard = (ClipboardManager) getContext().
                            getSystemService(getContext().CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("Vuforia text", textView.getText());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(getContext(), R.id.text_copy, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), R.id.text_copy_fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
        textView.setText(msg);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        return builder.create();
    }

}