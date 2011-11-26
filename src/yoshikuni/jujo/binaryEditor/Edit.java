package yoshikuni.jujo.binaryEditor;

import java.io.*;
import java.util.*;

class Edit
{

	LinkedList<Byte> contents = new LinkedList<Byte>();
	int cursor = 0;
	int high = -1;
	String path;
	int blockSize = -1;
	int blockNumber = -1;

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

	public String get(int lineNum)
	{
		String ret = "";
		int i;
		if (getCursorY() - lineNum > 0) {
			i = (getCursorY() - lineNum) * 16;
		} else {
			i = 0;
		}
		for (; i < contents.size(); i++) {
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

	private int getCursorY()
	{
		return cursor / 16;
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
		contents = new LinkedList<Byte>();
		cursor = 0;
		BufferedInputStream bis = new BufferedInputStream(
			new FileInputStream(p));
		int b;
		while ((b = bis.read()) > -1) {
			contents.add((byte)b);
		}
		bis.close();
	}

	public void setPath(String p, int bs, int bn) throws IOException
	{
		if (bs == -1 && bn == -1) {
			setPath(p);
			return;
		}
		path = p;
		contents = new LinkedList<Byte>();
		cursor = 0;
		blockSize = bs;
		blockNumber = bn;
		RandomAccessFile bis = new RandomAccessFile(path, "r");
		bis.seek(bs * bn);
		int b;
		int i = 0;
		while ((b = bis.read()) > -1 && i < bs) {
			contents.add((byte)b);
			i++;
		}
		bis.close();
	}

	public void save() throws IOException
	{
		if (blockSize == -1 && blockNumber == -1) {
			saveWhole();
			return;
		}
		RandomAccessFile bos = new RandomAccessFile(path, "rw");
		RandomAccessFile bis = new RandomAccessFile(path, "r");
		int length = blockSize * blockNumber;
		bos.seek(length);
		bis.seek(blockSize * (blockNumber + 1));
		LinkedList<Byte> buffer = new LinkedList<Byte>();
		Byte b;
		for (int i = 0; i < contents.size(); i++) {
			buffer.add(contents.get(i));
		}
		int c;
		while ((c = bis.read()) > -1) {
			bos.write(buffer.poll());
			length++;
			buffer.offer((byte)c);
		}
		while ((b = buffer.poll()) != null) {
			bos.write(b);
			length++;
		}
		bos.setLength(length);
		bis.close();
		bos.close();
	}

	private void saveWhole() throws IOException
	{
		RandomAccessFile bos = new RandomAccessFile(path, "rw");
		for (int i = 0; i < contents.size(); i++) {
			bos.write(contents.get(i));
		}
		bos.setLength(contents.size());
		bos.close();
	}
}
