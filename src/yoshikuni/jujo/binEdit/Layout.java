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
	Button up, down, paste, copy, backspace, left, right;
	Button[] keys = new Button[16];

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

		LinearLayout buttonField =
			(LinearLayout)activity.findViewById(R.id.buttonField);
		buttonField.setGravity(Gravity.BOTTOM);

		LinearLayout.LayoutParams layoutparamsUpDown =
			new LinearLayout.LayoutParams(widthPixels / 9,
							heightPixels / 13);
		layoutparamsUpDown.setMargins(widthPixels / 18, 0, 0, 0);

		up = new Button(activity);
		up.setLayoutParams(layoutparamsUpDown);
		buttonField.addView(up);

//		LinearLayout lrLayout =
//			(LinearLayout)activity.findViewById(R.id.lrlayout);
		LinearLayout lrLayout = new LinearLayout(activity);
		buttonField.addView(lrLayout);
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

//		LinearLayout deLayout =
//			(LinearLayout)activity.findViewById(R.id.delayout);
		LinearLayout deLayout = new LinearLayout(activity);
		buttonField.addView(deLayout, new LinearLayout.LayoutParams(FP, WC));

		LinearLayout downLayout = new LinearLayout(activity);
		deLayout.addView(downLayout);

		LinearLayout editLayout = new LinearLayout(activity);
		editLayout.setGravity(Gravity.RIGHT);
		deLayout.addView(editLayout,
			new LinearLayout.LayoutParams(FP, WC));
		LinearLayout.LayoutParams layoutparams =
			new LinearLayout.LayoutParams(widthPixels / 9,
							heightPixels / 13);
		layoutparams.setMargins(0, 0, 0, heightPixels / 40);
		down		= new Button(activity);
		paste		= new Button(activity);
		copy		= new Button(activity);
		backspace	= new Button(activity);
		down.setLayoutParams(layoutparamsUpDown);
		paste.setLayoutParams(layoutparams);
		copy.setLayoutParams(layoutparams);
		backspace.setLayoutParams(layoutparams);
		downLayout.addView(down);
		editLayout.addView(paste);
		editLayout.addView(copy);
		editLayout.addView(backspace);

		LinearLayout.LayoutParams layoutparamsKey =
			new LinearLayout.LayoutParams(widthPixels / 9,
							heightPixels / 13);
		layoutparamsKey.setMargins(0, 0, 0, 0);

		LinearLayout btn07 = new LinearLayout(activity);
		buttonField.addView(btn07, new LinearLayout.LayoutParams(FP, WC));

		LinearLayout btn03 = new LinearLayout(activity);
		btn07.addView(btn03);
		int i = 0;
		for (; i < 4; i++) {
			keys[i] = new Button(activity);
			keys[i].setLayoutParams(layoutparamsKey);
			btn03.addView(keys[i]);
		}

		LinearLayout btn47 = new LinearLayout(activity);
		btn47.setGravity(Gravity.RIGHT);
		btn07.addView(btn47, new LinearLayout.LayoutParams(FP, WC));

		for (; i < 8; i++) {
			keys[i] = new Button(activity);
			keys[i].setLayoutParams(layoutparamsKey);
			btn47.addView(keys[i]);
		}

		LinearLayout btn8f = new LinearLayout(activity);
		buttonField.addView(btn8f, new LinearLayout.LayoutParams(FP, WC));

		LinearLayout btn8b = new LinearLayout(activity);
		btn8f.addView(btn8b);
		for (; i < 12; i++) {
			keys[i] = new Button(activity);
			keys[i].setLayoutParams(layoutparamsKey);
			btn8b.addView(keys[i]);
		}

		LinearLayout btncf = new LinearLayout(activity);
		btncf.setGravity(Gravity.RIGHT);
		for (; i < 16; i++) {
			keys[i] = new Button(activity);
			keys[i].setLayoutParams(layoutparamsKey);
			btncf.addView(keys[i]);
		}
		btn8f.addView(btncf, new LinearLayout.LayoutParams(FP, WC));
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
			keys[0], keys[1], keys[2], keys[3],
			keys[4], keys[5], keys[6], keys[7],
			keys[8], keys[9], keys[10], keys[11],
			keys[12], keys[13], keys[14], keys[15],
		};

		for (int i = 0; i < btns.length; i++)
			btns[i].setWidth(widthPixels / 9);

		return btns;
	}

	public Button getFunctionButton(ButtonName n)
	{

		Button[] btns = {
//			(Button)activity.findViewById(R.id.up),
			up, down, left, right,
			paste, copy, backspace,
		};

/*
		for (int i = 0; i < btns.length; i++)
			btns[i].setWidth(widthPixels / 9);
*/

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
