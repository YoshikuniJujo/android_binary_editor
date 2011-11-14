package yoshikuni.jujo.binaryEditor;

import java.io.*;

import android.app.Activity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Button;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;

import android.widget.Toast;

import yoshikuni.jujo.binaryEditor.Layout;
import yoshikuni.jujo.binaryEditor.Edit;

public class BinaryEditor extends Activity
{
	static private TextView textview;
	static private Edit edit;
	private static SharedPreferences sp;
	private static Layout layout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		edit		= new Edit();
		layout		= new Layout(this);
		textview	= layout.getField();
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

	static Handler handler		= new Handler();
	static Runnable runnable	= new Runnable()
	{
		@Override
		public void run()
		{
			if (sp.contains("filePath")) {
				try {
					edit.setPath(sp.getString("filePath", ""));
					textview.setText(edit.get());
				} catch (FileNotFoundException e) {
					textview.setText("new file");
				} catch (IOException e) {
					textview.setText("read error!");
				}
			}
		}
	};

	private void setButtonAction()
	{
		Button[] btns = new Button[16];
		Button bsbtn, left, right, up, down, copy, paste;

		btns = layout.getButtons();

		for (int i = 0; i < 16; i++) {
			btns[i].setOnClickListener(
				new ButtonClickListener(edit, textview, i));
		}
		bsbtn = layout.getFunctionButton(Layout.ButtonName.BackSpace);
		bsbtn.setOnClickListener(
			new ButtonClickListener(edit, textview, -1));
		left	= layout.getFunctionButton(Layout.ButtonName.Left);
		right	= layout.getFunctionButton(Layout.ButtonName.Right);
		down	= layout.getFunctionButton(Layout.ButtonName.Down);
		up	= layout.getFunctionButton(Layout.ButtonName.Up);
		copy	= layout.getFunctionButton(Layout.ButtonName.Copy);
		paste	= layout.getFunctionButton(Layout.ButtonName.Paste);
		left.setOnClickListener(
			new ButtonClickListener(edit, textview, -2));
		right.setOnClickListener(
			new ButtonClickListener(edit, textview, -3));
		up.setOnClickListener(
			new ButtonClickListener(edit, textview, -4));
		down.setOnClickListener(
			new ButtonClickListener(edit, textview, -5));
		copy.setOnClickListener(
			new ButtonClickListener(edit, textview, -6));
		paste.setOnClickListener(
			new ButtonClickListener(edit, textview, -7));
	}

	private void setAlertDialog()
	{
		final Activity act = this;
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
						Toast.makeText(act,
							"can't write",
							Toast.LENGTH_LONG).show();
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
