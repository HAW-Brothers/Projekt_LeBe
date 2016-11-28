package lebe.lebeprototyp02.gui.control;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;

import com.astuetz.PagerSlidingTabStrip;

import java.io.Serializable;
import java.util.List;

import lebe.lebeprototyp02.R;

/**
 * Created by Chris on 28.11.2016.
 */

public class GUIController implements Serializable{

    private Context context;
    private View view;
    private String gender;

    public GUIController(View view){
        this.view = view;
        this.context = view.getContext();
    }

    // Konstruktor für Fragments
    public GUIController(View view, String gender){
        this.view = view;
        this.context = view.getContext();
        this.gender = gender;
    }


    public void handleLoginInterface(){
        ScrollView scrollView1 = (ScrollView) view.findViewById(R.id.scrollView1);
        ImageView lebeLogo = (ImageView) view.findViewById(R.id.imageView2);
        Drawable logo;
        Button button_login = (Button) view.findViewById(R.id.login_button);
        Drawable buttin_login_drawable;

        if(this.gender.equals("male")) {
            scrollView1.setBackgroundColor(this.context.getResources().getColor(R.color.colorLebePrimaryBackground_Male));

            logo = this.context.getResources().getDrawable(R.drawable.logo_male);
            lebeLogo.setImageDrawable(logo);

            buttin_login_drawable = this.context.getResources().getDrawable(R.drawable.button_login_male);
            button_login.setBackground(buttin_login_drawable);

        } else if (this.gender.equals("female")) {
            scrollView1.setBackgroundColor(this.context.getResources().getColor(R.color.colorLebePrimaryBackground_Female));

            logo = this.context.getResources().getDrawable(R.drawable.logo_female);
            lebeLogo.setImageDrawable(logo);

            buttin_login_drawable = this.context.getResources().getDrawable(R.drawable.button_login_female);
            button_login.setBackground(buttin_login_drawable);

        } else {

        }


    }

    public void handleMainInterface(){
        LinearLayout navigation = (LinearLayout) view.findViewById(R.id.navigation);
        PagerSlidingTabStrip slider = (PagerSlidingTabStrip) view.findViewById(R.id.PageSliderTabs);
        ImageView header_view = (ImageView) view.findViewById(R.id.header);
        Drawable header;


        if(this.gender.equals("male")) {
            navigation.setBackgroundColor(this.context.getResources().getColor(R.color.colorLebePrimaryBackground_Male));
            slider.setIndicatorColor(this.context.getResources().getColor(R.color.color_pageslider_indicator_male));
            slider.setBackgroundColor(this.context.getResources().getColor(R.color.colorLebePrimaryBackground_Male));

            header = this.context.getResources().getDrawable(R.drawable.header_male);
            header_view.setImageDrawable(header);


        } else if (this.gender.equals("female")) {
            slider.setBackgroundColor(this.context.getResources().getColor(R.color.colorLebePrimaryBackground_Female));
            slider.setIndicatorColor(this.context.getResources().getColor(R.color.color_pageslider_indicator_female));
            navigation.setBackgroundColor(this.context.getResources().getColor(R.color.colorLebePrimaryBackground_Female));

            header = this.context.getResources().getDrawable(R.drawable.header_female);
            header_view.setImageDrawable(header);


        } else {

        }


    }


    public void handleFragmentSettings(){

        EditText ed1 = (EditText) view.findViewById(R.id.userNameEdit);
        EditText ed2 = (EditText) view.findViewById(R.id.anzeigeNameEdit);
        EditText ed3 = (EditText) view.findViewById(R.id.passwortEdit);
        EditText ed4 = (EditText) view.findViewById(R.id.birthdateEdit);
        EditText ed5 = (EditText) view.findViewById(R.id.regDateEdit);
        EditText ed6 = (EditText) view.findViewById(R.id.editEmail);
        Button button_login = (Button) view.findViewById(R.id.button2);



        if(this.gender.equals("male")) {
            ed1.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_male));
            ed2.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_male));
            ed3.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_male));
            ed4.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_male));
            ed5.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_male));
            ed6.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_male));
            button_login.setBackground(this.context.getResources().getDrawable(R.drawable.button_login_male));


        } else if (this.gender.equals("female")) {
            ed1.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_female));
            ed2.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_female));
            ed3.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_female));
            ed4.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_female));
            ed5.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_female));
            ed6.setBackground(this.context.getResources().getDrawable(R.drawable.edittext_user_view_female));
            button_login.setBackground(this.context.getResources().getDrawable(R.drawable.button_login_female));

        } else {

        }

    }

    public void handleTableRow(List<TableRow> list){

        if(gender.equals("male")) {

            for(int i = 0; i < list.size() ; i++){
                list.get(i).setBackground(this.context.getResources().getDrawable(R.drawable.table_row_bib_male));
            }


        } else if (gender.equals("female")) {

            for(int i = 0; i < list.size() ; i++){
                list.get(i).setBackground(this.context.getResources().getDrawable(R.drawable.table_row_bib_female));
            }

        } else {

        }

    }

    public void handelFragmentHome(){

    }

    public void handleFragmentStore(){

    }

    public void updateView(View view){
        this.view = view;
        this.context = view.getContext();
    }



}
