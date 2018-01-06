package com.lilei.fitness.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.lilei.fitness.R;
public class LoadingDialog extends Dialog{

	private String msg;
	private TextView txt;
	private RoundProgressBar progressBar;
	private ImageView normal_loading_bar;
	private AnimationDrawable mAnimation;
	
	public LoadingDialog(Context context) {
		super(context, R.style.MyDialogStyle_center);
		this.msg = context.getResources().getString(R.string.loading_summary);
	}
	
	public LoadingDialog(Context context, String msg) {
		super(context, R.style.MyDialogStyle_center);
		this.msg = msg;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.loading_dialog_layout);
		initViews();
	}
	
	private void initViews() {
		this.txt=(TextView)findViewById(R.id.loading_content_text);
		this.progressBar=(RoundProgressBar)findViewById(R.id.roundProgressBar);
		this.normal_loading_bar=(ImageView)findViewById(R.id.normal_loading);
		this.txt.setText(msg);
		
		this.normal_loading_bar.setBackgroundResource(R.drawable.loading_animation);
		// 通过ImageView对象拿到背景显示的AnimationDrawable
		mAnimation = (AnimationDrawable) normal_loading_bar.getBackground();
		// 为了防止在onCreate方法中只显示第一帧的解决方案之一
		this.normal_loading_bar.post(new Runnable() {
			@Override
			public void run() {
				mAnimation.start();
			}
		});
		
		this.normal_loading_bar.setVisibility(View.VISIBLE);
		this.progressBar.setVisibility(View.GONE);
	}
	
	public synchronized void setProgress(int precent){
		
		if (precent > 100)
			precent = 100;
		
		this.normal_loading_bar.setVisibility(View.GONE);
		this.progressBar.setVisibility(View.VISIBLE);
		
		this.txt.setText(String.format("%1$s (%2$d%%)",msg,precent));
		this.progressBar.setProgress(precent);
	}
	
	public synchronized void setMSG(String msg){
		
		
		this.normal_loading_bar.setVisibility(View.VISIBLE);
		this.progressBar.setVisibility(View.GONE);
		
		this.msg=msg;
		this.txt.setText(msg);
		
	}
	

}
