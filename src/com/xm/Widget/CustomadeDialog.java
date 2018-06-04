package com.xm.Widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.socketserialport.R;

public class CustomadeDialog extends Dialog{

	public CustomadeDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CustomadeDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
	}

	public CustomadeDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
	}
	
	
	  public static class Builder {
		  private Context context;  
	        private String title;  
	        private String message;  
	        private String arg; 
	        private String positiveButtonText;  
	        private String negativeButtonText;  
	        private View contentView;  
	        private DialogInterface.OnClickListener positiveButtonClickListener;  
	        private DialogInterface.OnClickListener negativeButtonClickListener;
			private boolean mCancelable;
			private boolean mAutoDismissenable = true;
	  
	        public Builder(Context context) {  
	            this.context = context;  
	        }  

	        public Builder setMessage(String message) {  
	            this.message = message;  
	            return this;  
	        }  
	  
	        /** 
	         * Set the Dialog message from resource 
	         *  
	         * @param title 
	         * @return 
	         */  
	        public Builder setMessage(int message) {  
	            this.message = (String) context.getText(message);  
	            return this;  
	        }  
	  
	        /** 
	         * Set the Dialog title from resource 
	         *  
	         * @param title 
	         * @return 
	         */  
	        public Builder setTitle(int title) {  
	            this.title = (String) context.getText(title);  
	            return this;  
	        }  
	  
	        /** 
	         * Set the Dialog title from String 
	         *  
	         * @param title 
	         * @return 
	         */  
	  
	        public Builder setTitle(String title) {  
	            this.title = title;  
	            return this;  
	        }  
	  
	        public Builder setContentView(View v) {  
	            this.contentView = v;  
	            return this;  
	        }  
	  
	        /** 
	         * Set the positive button resource and it's listener 
	         *  
	         * @param positiveButtonText 
	         * @return 
	         */  
	       
	        
	        public Builder setPositiveButton(int positiveButtonText,  
	                DialogInterface.OnClickListener listener) {  
	            this.positiveButtonText = (String) context  
	                    .getText(positiveButtonText);  
	            this.positiveButtonClickListener = listener;  
	            return this;  
	        }  
	  
	        public Builder setPositiveButton(String positiveButtonText,  
	                DialogInterface.OnClickListener listener) {  
	            this.positiveButtonText = positiveButtonText;  
	            this.positiveButtonClickListener = listener;  
	            return this;  
	        }  
	  
	        public Builder setNegativeButton(int negativeButtonText,  
	                DialogInterface.OnClickListener listener) {  
	            this.negativeButtonText = (String) context  
	                    .getText(negativeButtonText);  
	            this.negativeButtonClickListener = listener;  
	            return this;  
	        }  
	  
	        public Builder setNegativeButton(String negativeButtonText,  
	                DialogInterface.OnClickListener listener) {  
	            this.negativeButtonText = negativeButtonText;  
	            this.negativeButtonClickListener = listener;  
	            return this;  
	        }  
	        
	        public Builder setCancelable(boolean cancelable) {
	            mCancelable = cancelable;
	            return this;
	        }
	        
	        public Builder setAutoDismiss(boolean AutoDismissenable){
	        	mAutoDismissenable = AutoDismissenable;
	        	return this;
	        }
	  
	        public CustomadeDialog create() {  
	            LayoutInflater inflater = (LayoutInflater) context  
	                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);  
	            // instantiate the dialog with the custom Theme  
	            final CustomadeDialog dialog = new CustomadeDialog(context,R.style.Dialog);  
	            View layout = inflater.inflate(R.layout.customadeview_dialog, null);  
	            
//	            dialog.addContentView(layout, new LayoutParams(  
//	            		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)); 
	            dialog.addContentView(layout, new LayoutParams(  
	            		LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	            
	            dialog.setCancelable(mCancelable);
	            if (mCancelable) {
	                dialog.setCanceledOnTouchOutside(true);
	            }
	            // set the dialog title  
	            ((TextView) layout.findViewById(R.id.title)).setText(title);  
	            // set the confirm button  
	            if(positiveButtonText == null && negativeButtonText == null){
	            	((LinearLayout)layout.findViewById(R.id.option)).setVisibility(View.GONE);
	            }else{
	            	if (positiveButtonText != null) {  
		                ((Button) layout.findViewById(R.id.positiveButton))  
		                        .setText(positiveButtonText);  
		                if (positiveButtonClickListener != null) {  
		                    ((Button) layout.findViewById(R.id.positiveButton))  
		                            .setOnClickListener(new View.OnClickListener() {  
		                                public void onClick(View v) {  
		                                    positiveButtonClickListener.onClick(dialog,  
		                                            DialogInterface.BUTTON_POSITIVE);  
		                                    if(mAutoDismissenable)
		                                    dialog.dismiss();
		                                }  
		                            });  
		                }  
		            } else {  
		                // if no confirm button just set the visibility to GONE  
		                layout.findViewById(R.id.positiveButton).setVisibility(  
		                        View.GONE);  
		                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)layout.findViewById(R.id.negativeButton).getLayoutParams();
		                lp.setMargins(-20, 0, 0, 0);  
		                layout.findViewById(R.id.negativeButton).setLayoutParams(lp);  
		            }  
		            // set the cancel button  
		            if (negativeButtonText != null) {  
		                ((Button) layout.findViewById(R.id.negativeButton))  
		                        .setText(negativeButtonText);  
		                if (negativeButtonClickListener != null) {  
		                    ((Button) layout.findViewById(R.id.negativeButton))  
		                            .setOnClickListener(new View.OnClickListener() {  
		                                public void onClick(View v) {  
		                                    negativeButtonClickListener.onClick(dialog,  
		                                            DialogInterface.BUTTON_NEGATIVE); 
		                                    if(mAutoDismissenable)
		                                    dialog.dismiss();
		                                }  
		                            });  
		                }  
		            } else {  
		                // if no confirm button just set the visibility to GONE  
		                layout.findViewById(R.id.negativeButton).setVisibility(  
		                        View.GONE);  
		            }  
	            }
	            
	            // set the content message  
	            if (message != null) {  
	                ((TextView) layout.findViewById(R.id.message)).setText(message);  
	            } else if (contentView != null) {  
	                // if no message set  
	                // add the contentView to the dialog body  
	                ((LinearLayout) layout.findViewById(R.id.content))  
	                        .removeAllViews();  
	                if(contentView.getParent()!=null)
	                	((ViewGroup) contentView.getParent()).removeAllViews();
	                ((LinearLayout) layout.findViewById(R.id.content))  
	                        .addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));  
	            }  
	            
	            dialog.setContentView(layout);  
	            
	            return dialog;  
	        }


	  }

}
