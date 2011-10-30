package yoshikuni.jujo.binEdit;

import android.app.Activity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Button;

import android.view.View;

import android.util.Log;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import yoshikuni.jujo.binEdit.Edit;

public class BinEdit extends Activity
{
	private TextView textview;
	private Edit edit;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		textview = (TextView)findViewById(R.id.textview);

		edit = new Edit();

		setButtonAction();
	}

	private void setButtonAction()
	{
		WindowManager wm = getWindowManager();
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		int widthPixels = metrics.widthPixels;

		Button[] btns = new Button[16];
		Button bsbtn;

		btns[0] = (Button)findViewById(R.id.btn0);
		btns[1] = (Button)findViewById(R.id.btn1);
		btns[2] = (Button)findViewById(R.id.btn2);
		btns[3] = (Button)findViewById(R.id.btn3);
		btns[4] = (Button)findViewById(R.id.btn4);
		btns[5] = (Button)findViewById(R.id.btn5);
		btns[6] = (Button)findViewById(R.id.btn6);
		btns[7] = (Button)findViewById(R.id.btn7);
		btns[8] = (Button)findViewById(R.id.btn8);
		btns[9] = (Button)findViewById(R.id.btn9);
		btns[10] = (Button)findViewById(R.id.btna);
		btns[11] = (Button)findViewById(R.id.btnb);
		btns[12] = (Button)findViewById(R.id.btnc);
		btns[13] = (Button)findViewById(R.id.btnd);
		btns[14] = (Button)findViewById(R.id.btne);
		btns[15] = (Button)findViewById(R.id.btnf);
		for (int i = 0; i < 16; i++) {
			btns[i].setWidth(widthPixels / 9);
			btns[i].setHeight(widthPixels / 9);
			btns[i].setOnClickListener(
				new ButtonClickListener(edit, textview, i));
		}
		bsbtn = (Button)findViewById(R.id.backspace);
		bsbtn.setWidth(widthPixels / 9);
		bsbtn.setHeight(widthPixels / 9);
		bsbtn.setOnClickListener(
			new ButtonClickListener(edit, textview, -1));
	}
}

class ButtonClickListener implements View.OnClickListener
{
	TextView textview;
	int key;
	Edit edit;

	ButtonClickListener(Edit ed, TextView tv, int i)
	{
		textview = tv;
		key = i;
		edit = ed;
	}

	@Override
	public void onClick(View view)
	{
		edit.push(key);
		textview.setText(edit.get());
	}
}
