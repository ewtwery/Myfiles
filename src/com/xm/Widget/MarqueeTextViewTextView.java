package com.xm.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextViewTextView extends TextView  
{  
  
    public MarqueeTextViewTextView(Context context, AttributeSet attrs, int defStyle)  
    {  
        super(context, attrs, defStyle);  
        // TODO Auto-generated constructor stub  
    }  
  
    public MarqueeTextViewTextView(Context context, AttributeSet attrs)  
    {  
        super(context, attrs);  
    }  
  
    @Override  
    public boolean isFocused()  
    {  
        return true;  
    }  
  
}  