package yoshikuni.jujo.binEdit;

import android.app.Activity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Button;

import android.view.View;

public class BinEdit extends Activity
{
	TextView textview;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
 	       setContentView(R.layout.main);

		textview = (TextView)findViewById(R.id.textview);
		textview.setText("hello\12");

		setButtonAction();
	}

	private void setButtonAction()
	{
		Button[] btns = new Button[16];
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
			btns[i].setOnClickListener(
				new ButtonClickListener(textview, i));
		}
	}
}

class ButtonClickListener implements View.OnClickListener
{
	TextView textview;
	int num;
	int key;

	ButtonClickListener(TextView tv, int i)
	{
		textview = tv;
		key = i;
	}

	@Override
	public void onClick(View view)
	{
		textview.setText(Integer.toString(key));
	}
}
