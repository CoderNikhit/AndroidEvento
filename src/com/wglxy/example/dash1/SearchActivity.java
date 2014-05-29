package com.wglxy.example.dash1;

import android.os.Bundle;

public class SearchActivity extends DashboardActivity 
{


protected void onCreate(Bundle savedInstanceState) 
{
    super.onCreate(savedInstanceState);
    setContentView (R.layout.activity_search);
    setTitleFromActivityLabel (R.id.title_text);
}    
}
