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

class Layout
{
	public enum ButtonName {
		Up, Down, Left, Right, Paste, Copy, BackSpace
	}

	Activity activity;
	int widthPixels;
	int heightPixels;

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
			(Button)activity.findViewById(R.id.down),
			(Button)activity.findViewById(R.id.left),
			(Button)activity.findViewById(R.id.right),
			(Button)activity.findViewById(R.id.paste),
			(Button)activity.findViewById(R.id.copy),
			(Button)activity.findViewById(R.id.backspace),
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
