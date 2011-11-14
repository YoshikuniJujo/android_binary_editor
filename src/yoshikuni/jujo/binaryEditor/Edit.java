package yoshikuni.jujo.binaryEditor;

import java.io.*;
import java.util.*;

class Edit
{
	private String str = "";
	private String buf = "";
	private int utfBuf = 0;
	private int num = -1;
	private int bytes = 0;
	private int cursor = 0;
	private String path = "";

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
				case '-':	i = -1; break;
				case '1':	i = -2; break;
				case '2':	i = -3; break;
				default:	i = -4; break;
			}
			testEdit.push(i);
		}
		System.out.println(testEdit.get());
	}

	public String get() {
		String ret = str.substring(0, cursor);
		if (utfBuf > 0) ret += "~" + utfBuf;
		if (num > -1) ret += "^" + num;
		ret += "_";
		ret += str.substring(cursor, str.length());
		return ret;
	}

	public void push(int n) {
		if (n == -1) {
			if (num > -1) {
				num = -1;
			} else if (utfBuf > 0) {
				utfBuf = 0; bytes = 0;
			} else {
				if (!str.equals("") && cursor > 0) {
					str = str.substring(0, cursor - 1)
					+ str.substring(cursor, str.length());
					cursor--;
				}
			}
		} else if (n == -2) {
			if (num == -1 && utfBuf == 0 && cursor > 0) {
				cursor--;
			}
		} else if (n == -3) {
			if (num == -1 && utfBuf == 0 && cursor < str.length()) {
				cursor++;
			}
		} else if (n == -4) {
			if (num == -1 && utfBuf == 0 && cursor > 0) {
				cursorUp();
			}
		} else if (n == -5) {
			if (num == -1 && utfBuf == 0 && cursor < str.length()) {
				cursorDown();
			}
		} else if (n == -6) {
			copy();
		} else if (n == -7) {
			paste();
		} else if (num < 0) {
			num = n;
		} else {
			add(num << 4 | n);
			num = -1;
		}
	}

	public void setPath(String p) throws IOException
	{
		if (path != p) {
			cursor = 0;
			utfBuf = 0;
			num = -1;
			bytes = 0;
			str = "";
			BufferedReader reader
				= new BufferedReader(new FileReader(p));
			String line;
			while ((line = reader.readLine()) != null) {
				str += line + "\n";
			}
			reader.close();
			path = p;
		}
	}

	public void save() throws IOException
	{
		PrintWriter writer =
			new PrintWriter(new BufferedWriter(new FileWriter(path)));
		writer.print(str);
		writer.close();
	}

	private void add(int c) {
		if (bytes > 0) {
			utfBuf = (utfBuf << 6) | (c & ~(1 << 7));
			bytes--;
			if (bytes < 1) {
				str = str.substring(0, cursor) + (char)utfBuf
					+ str.substring(cursor, str.length());
				utfBuf = 0;
				cursor++;
			}
		} else {
			int n = 0;
			utfBuf = c;
			for (int i = 7; i > 1; i--) {
				if ((c & 1 << i) == 0) break;
				utfBuf &= ~(1 << i);
				n++;
			}
			bytes = n - 1;
			if (n == 0) {
				utfBuf = 0; // str += (char)c;
				str = str.substring(0, cursor) + (char)c
					+ str.substring(cursor, str.length());
				cursor++;
			}
		}
	}

	private void cursorUp()
	{
		cursor = preIndex(str, '\n', cursor);
	}

	private void cursorDown()
	{
		cursor = postIndex2(str, '\n', cursor) + 1;
	}

	private void copy()
	{
		buf = str.substring(preIndex2(str, '\n', cursor) + 1,
			postIndex2(str, '\n', cursor) + 1);
	}

	private void paste()
	{
		int next = postIndex2(str, '\n', cursor) + 1;
		str = str.substring(0, next) + buf
			+ str.substring(next, str.length());
	}

	private static int preIndex2(String str, char c, int here)
	{
		Integer ret = -1;
		LinkedList<Integer> ids = indices(str, c);
		for (Iterator i = ids.iterator(); i.hasNext();) {
			Integer tmp = (Integer)i.next();
			if (tmp >= here) break;
			ret = tmp;
		}
		return ret;
	}

	private static int preIndex(String str, char c, int here)
	{
		Integer ret = 0;
		LinkedList<Integer> ids = indices(str, c);
		for (Iterator i = ids.iterator(); i.hasNext();) {
			Integer tmp = (Integer)i.next();
			if (tmp >= here) break;
			ret = tmp;
		}
		return ret;
	}

	private static int postIndex2(String str, char c, int here)
	{
		Integer ret = 0;
		LinkedList<Integer> ids = indices(str, c);
		for (Iterator i = ids.iterator(); i.hasNext();) {
			ret = (Integer)i.next();
			if (ret >= here) break;
		}
		return ret;
	}

	private static int postIndex(String str, char c, int here)
	{
		Integer ret = 0;
		LinkedList<Integer> ids = indices(str, c);
		for (Iterator i = ids.iterator(); i.hasNext();) {
			ret = (Integer)i.next();
			if (ret > here) break;
		}
		return ret;
	}

	private static LinkedList<Integer> indices(String str, char c)
	{
		LinkedList<Integer> ret = new LinkedList<Integer>();
		for (int i = 0; i < str.length(); i++)
			if (str.charAt(i) == c) ret.add(i);
		return ret;
	}
}
