package yoshikuni.jujo.binEdit;

import java.io.*;

import android.app.Activity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;

import android.view.View;

import android.util.Log;

import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import android.view.Window;

import yoshikuni.jujo.binEdit.Edit;

public class BinEdit extends Activity
{
	static private TextView textview;
	static private ScrollView scrollview;
	static private LinearLayout vbox;
	static private Edit edit;
	private static SharedPreferences sp;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = getWindow();
		window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		textview = (TextView)findViewById(R.id.textview);
		scrollview = (ScrollView)findViewById(R.id.scrollview);
		vbox = (LinearLayout)findViewById(R.id.vbox);

		edit = new Edit();

		setButtonAction();

		sp = PreferenceManager.getDefaultSharedPreferences(this);
		handler.post(runnable);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(Menu.NONE, 0, 0, "open");
		menu.add(Menu.NONE, 1, 1, "save");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()) {
		case 0:
			Intent intent = new Intent(this, EditPref.class);
			startActivity(intent);
			break;
		case 1:
			setAlertDialog();
			break;
		}
		return true;
	}

	static Handler handler = new Handler();
	static Runnable runnable = new Runnable()
	{
		@Override
		public void run()
		{
			if (sp.contains("filePath")) {
				try {
					edit.setPath(sp.getString("filePath", ""));
					textview.setText(edit.get());
				} catch (IOException e) {
					textview.setText("read error!");
				}
			}
		}
	};

	private void setButtonAction()
	{
		WindowManager wm = getWindowManager();
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);

		int widthPixels = metrics.widthPixels;
		int heightPixels = metrics.heightPixels;

		Button[] btns = new Button[16];
		Button bsbtn, left, right, up, down, copy, paste;

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
		left = (Button)findViewById(R.id.left);
		right = (Button)findViewById(R.id.right);
		down = (Button)findViewById(R.id.down);
		up = (Button)findViewById(R.id.up);
		left.setWidth(widthPixels / 9);
		left.setHeight(widthPixels / 9);
		right.setWidth(widthPixels / 9);
		right.setHeight(widthPixels / 9);
		down.setWidth(widthPixels / 9);
		down.setHeight(widthPixels / 9);
		up.setWidth(widthPixels / 9);
		up.setHeight(widthPixels / 9);
		left.setOnClickListener(
			new ButtonClickListener(edit, textview, -2));
		right.setOnClickListener(
			new ButtonClickListener(edit, textview, -3));
		up.setOnClickListener(
			new ButtonClickListener(edit, textview, -4));
		down.setOnClickListener(
			new ButtonClickListener(edit, textview, -5));
		copy = (Button)findViewById(R.id.copy);
		copy.setWidth(widthPixels / 9);
		copy.setHeight(widthPixels / 9);
		copy.setOnClickListener(
			new ButtonClickListener(edit, textview, -6));
		paste = (Button)findViewById(R.id.paste);
		paste.setWidth(widthPixels / 9);
		paste.setHeight(widthPixels / 9);
		paste.setOnClickListener(
			new ButtonClickListener(edit, textview, -7));
//		textview.setHeight(heightPixels / 3);
//		vbox.setHeight(heightPixels / 3);
//		vbox.height = heightPixels / 3;
	}

	private void setAlertDialog()
	{
		AlertDialog.Builder alertDialogBuilder
			= new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("save?");
		alertDialogBuilder.setPositiveButton("OK",
			new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog,
					int width)
				{
					try {
						edit.save();
					} catch (IOException e) {
						textview.setText("can't write");
					}
				}
			}
		);
		alertDialogBuilder.setNegativeButton("Cancel",
			new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog,
					int width)
				{
				}
			}
		);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
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
