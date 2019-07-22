package com.iot.instagramcolone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ArrayAdapterClass extends ArrayAdapter {

    private final ArrayList<String> userName;
    private final ArrayList<String> userComment;
    private final ArrayList<Bitmap> userImage;
    private final Activity context;

        public ArrayAdapterClass(ArrayList<String> userNam,
                                 ArrayList<String> userCommen,
                                 ArrayList<Bitmap> userImage,
                                 Activity contex){

            super(contex,R.layout.custom_layout,userNam);// 1. isteği CONTEXT, 2. istediği hangi LAYOUT ile bağlamak istediğin

            this.userName    = userNam;
            this.userComment = userCommen;
            this.userImage   = userImage;
            this.context     = contex;

        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.custom_layout,null,true);

        TextView NameText = customView.findViewById(R.id.textViewCustomUserName);
        TextView CommText = customView.findViewById(R.id.textViewCustomUserComment);
        ImageView image   = customView.findViewById(R.id.imageView2);

        NameText.setText(userName.get(position));
        CommText.setText(userComment.get(position));
        image.setImageBitmap(userImage.get(position));

        return customView;
    }
}
