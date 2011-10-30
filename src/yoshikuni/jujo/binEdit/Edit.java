package yoshikuni.jujo.binEdit;

import java.io.*;

class Edit
{
	private String str = "";
	private int num = -1;

	public static void main(String[] args) throws IOException
	{
		InputStreamReader in = new InputStreamReader(System.in);
		System.out.println("for debug");

		Edit testEdit = new Edit();

		int c = 1;
		while(c > 0) {
			c = in.read();
			if ((char)c == '\n') break;
			int i;
			switch ((char)c) {
				case 'q':	i = 0; break;
				case 'w':	i = 1; break;
				case 'e':	i = 2; break;
				case 'r':	i = 3; break;
				case 'u':	i = 4; break;
				case 'i':	i = 5; break;
				case 'o':	i = 6; break;
				case 'p':	i = 7; break;
				case 'a':	i = 8; break;
				case 's':	i = 9; break;
				case 'd':	i = 10; break;
				case 'f':	i = 11; break;
				case 'j':	i = 12; break;
				case 'k':	i = 13; break;
				case 'l':	i = 14; break;
				case ';':	i = 15; break;
				default:	i = -1; break;
			}
			testEdit.push(i);
		}
		System.out.println(testEdit.get());
	}

	public String get() {
		if ( num < 0 ) {
			return str;
		} else {
			return str + "^" + num;
		}
	}

	public void push(int n) {
		if (n < 0) {
			if (num > -1) {
				num = -1;
			} else {
				if (!str.equals("")) {
					str = str.substring(0, str.length() - 1);
				}
			}
		} else if (num < 0) {
			num = n;
		} else {
			str += (char)(num << 4 | n);
			num = -1;
		}
	}
}
