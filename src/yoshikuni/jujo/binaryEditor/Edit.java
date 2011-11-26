package yoshikuni.jujo.binaryEditor;

import java.io.*;
import java.util.*;

class Edit
{

	LinkedList<Byte> contents = new LinkedList<Byte>();
	int cursor = 0;
	int high = -1;
	String path;

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

	public String get()
	{
		String ret = "";
		int i;
		for (i = 0; i < contents.size(); i++) {
			if (i == cursor) {
				if (high > -1) ret += String.format("%x", high);
				ret += "_";
			}
			ret += String.format("%02x ", contents.get(i));
			if (i % 16 == 15) ret += "\n";
		}
		if (i == cursor) {
			if (high > -1) ret += String.format("%x", high);
			ret += "_";
		}
		return ret;
	}

	public void push(int n)
	{
		if (n > -1) {
			if (high > -1) {
				contents.add(cursor, (byte)(high << 4 | n));
				high = -1;
				cursor++;
			} else {
				high = n;
			}
		} else if (n == -1) {
			if (cursor > 0) {
				cursor--;
				contents.remove(cursor);
			}
		} else if (n == -2) {
			if (cursor > 0) cursor--;
		} else if (n == -3) {
			if (cursor < contents.size()) cursor++;
		} else if (n == -4) {
			if (cursor > 15) cursor -= 16;
		} else if (n == -5) {
			if (cursor < contents.size() - 15) cursor += 16;
		}
	}

	public void setPath(String p) throws IOException
	{
		path = p;
		BufferedInputStream bis = new BufferedInputStream(
			new FileInputStream(p));
		int b;
		while ((b = bis.read()) > -1) {
			contents.add((byte)b);
		}
		bis.close();
	}

	public void save() throws IOException
	{
		BufferedOutputStream bos = new BufferedOutputStream(
			new FileOutputStream(path));
		for (int i = 0; i < contents.size(); i++) {
			bos.write(contents.get(i));
		}
		bos.close();
	}

}
