package yoshikuni.jujo.binEdit;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.TextView;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.Window;

import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import android.view.Gravity;

class Layout
{
	static final int WC = LinearLayout.LayoutParams.WRAP_CONTENT;
	static final int FP = LinearLayout.LayoutParams.FILL_PARENT;

	public enum ButtonName {
		Up, Down, Left, Right, Paste, Copy, BackSpace
	}

	Activity activity;
	int widthPixels;
	int heightPixels;
	Button down, paste, copy, backspace, left, right;

	Layout(Activity a)
	{
		WindowManager wm = a.getWindowManager();
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		a.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = a.getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		activity = a;
		widthPixels = metrics.widthPixels;
		heightPixels = metrics.heightPixels;

		activity.setContentView(mainView());

		LinearLayout lrLayout =
			(LinearLayout)activity.findViewById(R.id.lrlayout);
		LinearLayout.LayoutParams layoutparamsLR =
			new LinearLayout.LayoutParams(widthPixels / 9,
							heightPixels / 13);
		layoutparamsLR.setMargins(0, 0, 0, 0);

		left	= new Button(activity);
		right	= new Button(activity);
		left.setLayoutParams(layoutparamsLR);
		right.setLayoutParams(layoutparamsLR);
		lrLayout.addView(left);
		lrLayout.addView(right);

		LinearLayout deLayout =
			(LinearLayout)activity.findViewById(R.id.delayout);
		LinearLayout downLayout = new LinearLayout(activity);
		deLayout.addView(downLayout);

		LinearLayout editLayout = new LinearLayout(activity);
		editLayout.setGravity(Gravity.RIGHT);
		deLayout.addView(editLayout,
			new LinearLayout.LayoutParams(FP, WC));

		LinearLayout.LayoutParams layoutparamsDown =
			new LinearLayout.LayoutParams(widthPixels / 9,
							heightPixels / 13);
		LinearLayout.LayoutParams layoutparams =
			new LinearLayout.LayoutParams(widthPixels / 9,
							heightPixels / 13);
		layoutparamsDown.setMargins(widthPixels / 18, 0, 0, 0);
		layoutparams.setMargins(0, 0, 0, heightPixels / 40);
		down		= new Button(activity);
		paste		= new Button(activity);
		copy		= new Button(activity);
		backspace	= new Button(activity);
		down.setLayoutParams(layoutparamsDown);
		paste.setLayoutParams(layoutparams);
		copy.setLayoutParams(layoutparams);
		backspace.setLayoutParams(layoutparams);
		downLayout.addView(down);
		editLayout.addView(paste);
		editLayout.addView(copy);
		editLayout.addView(backspace);
	}

	public int mainView()
	{
		return R.layout.main;
	}

	public TextView getField()
	{
		return (TextView)activity.findViewById(R.id.textview);
	}

	public Button[] getButtons()
	{
		Button[] btns = {
			(Button)activity.findViewById(R.id.btn0),
			(Button)activity.findViewById(R.id.btn1),
			(Button)activity.findViewById(R.id.btn2),
			(Button)activity.findViewById(R.id.btn3),
			(Button)activity.findViewById(R.id.btn4),
			(Button)activity.findViewById(R.id.btn5),
			(Button)activity.findViewById(R.id.btn6),
			(Button)activity.findViewById(R.id.btn7),
			(Button)activity.findViewById(R.id.btn8),
			(Button)activity.findViewById(R.id.btn9),
			(Button)activity.findViewById(R.id.btna),
			(Button)activity.findViewById(R.id.btnb),
			(Button)activity.findViewById(R.id.btnc),
			(Button)activity.findViewById(R.id.btnd),
			(Button)activity.findViewById(R.id.btne),
			(Button)activity.findViewById(R.id.btnf),
		};

		for (int i = 0; i < btns.length; i++)
			btns[i].setWidth(widthPixels / 9);

		return btns;
	}

	public Button getFunctionButton(ButtonName n)
	{

		Button[] btns = {
			(Button)activity.findViewById(R.id.up),
			down, left, right,
/*
			(Button)activity.findViewById(R.id.down),
			(Button)activity.findViewById(R.id.left),
			(Button)activity.findViewById(R.id.right),
*/
			paste, copy, backspace,
/*
			(Button)activity.findViewById(R.id.paste),
			(Button)activity.findViewById(R.id.copy),
			(Button)activity.findViewById(R.id.backspace),
*/
		};

		for (int i = 0; i < btns.length; i++)
			btns[i].setWidth(widthPixels / 9);

		switch (n) {
		case Up:
			return btns[0];
		case Down:
			return btns[1];
		case Left:
			return btns[2];
		case Right:
			return btns[3];
		case Paste:
			return btns[4];
		case Copy:
			return btns[5];
		case BackSpace:
			return btns[6];
		default:
			return null;
		}
	}
}
