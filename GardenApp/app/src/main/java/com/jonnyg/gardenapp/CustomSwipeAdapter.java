package com.jonnyg.gardenapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by reiko_000 on 24/04/2016.
 */
public class CustomSwipeAdapter extends PagerAdapter {

    DataBaseHelper myDB;


    //Drawable d = new BitmapDrawable(getImage(image));


    public static Bitmap getImages(byte[] image){

        return BitmapFactory.decodeByteArray(image,0,image.length);
    }




    //private Bitmap[] image_resources = {getImages(image)}
    private int[] image_resources = {R.drawable.community_btn_txtpn,R.drawable.logo_design,R.drawable.photo_camerabtn_txt,
    R.drawable.plant_for_gallery,R.drawable.save_for_sharing_txt,R.drawable.seed_savebtn_txt,R.drawable.seed_sharebtn_txt,
    R.drawable.seeds_for_sharing_txt};



    private Context ctx;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx){
        this.ctx = ctx;

    }
    @Override
    public int getCount() {
        //return image_resources.length;
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view ==(RelativeLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
        /*myDB = new DataBaseHelper(this.ctx);
        Cursor myImg = myDB.getImage();
        if(myImg.getCount()==0){
            showMessage("error", "no data");
        }
        byte[] images  = myImg.getBlob(myImg.getColumnIndex("image"));*/

        ImageView imageView = (ImageView)item_view.findViewById(R.id.image_view_gallery);
        TextView textView = (TextView)item_view.findViewById(R.id.image_count);
        TextView photoSlide = (TextView)item_view.findViewById(R.id.txtVPhotoSlide);
        /*while(myImg.moveToNext()){
            Bitmap[] myBmp = {getImages(images)};
            imageView.setImageBitmap(myBmp[position]);
        }*/
        imageView.setImageResource(image_resources[position]);
        position = position+1;
        textView.setText("Image : " + position);
        photoSlide.setText("Photo Slide");
        container.addView(item_view);
       // System.out.print("JonJoe"+myImg);
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       //work faster
        container.removeView((RelativeLayout)object);
    }

    public void showMessage(String title,String message) {
        final AlertDialog.Builder myBuilder = new AlertDialog.Builder(this.ctx);
        final AlertDialog myAlert = myBuilder.create();
        myAlert.setCancelable(true);
        myAlert.setTitle(title);
        myAlert.setMessage(message);
        myAlert.setButton(DialogInterface.BUTTON_POSITIVE,"Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myAlert.dismiss();
            }
        });
        myAlert.show();
    }
}
