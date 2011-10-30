package yoshikuni.jujo.binEdit;

import java.io.*;

class Edit
{
	private String str = "";
	private int utfBuf = 0;
	private int num = -1;
	private int bytes = 0;

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
		String ret = str;
		if (utfBuf > 0) ret += "~" + utfBuf;
		if (num > -1) ret += "^" + num;
		return ret;
	}

	public void push(int n) {
		if (n < 0) {
			if (num > -1) {
				num = -1;
			} else if (utfBuf > 0) {
				utfBuf = 0; bytes = 0;
			} else {
				if (!str.equals("")) {
					str = str.substring(0, str.length() - 1);
				}
			}
		} else if (num < 0) {
			num = n;
		} else {
			add(num << 4 | n);
			num = -1;
		}
	}

	private void add(int c) {
		if (bytes > 0) {
			utfBuf = (utfBuf << 6) | (c & ~(1 << 7));
			bytes--;
			if (bytes < 1) {
				str += (char)utfBuf;
				utfBuf = 0;
			}
		} else {
			int n = 0;
			utfBuf = c;
			for (int i = 7; i > 1; i--) {
				if ((c & 1 << i) == 0) break;
				utfBuf &= ~(1 << i);
				n++;
			}
			System.out.println(n);
			bytes = n - 1;
			if (n == 0) {
				utfBuf = 0; str += (char)c;
			}
		}
	}
}
