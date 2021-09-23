package cn.kuwo.networker.crypt;

import android.text.TextUtils;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;


/**
 * @author 李建衡：jianheng.li@kuwo.cn
 * 
 */
public class IOUtils {
	
public static final int MAX_BUFFER_BYTES = 2048;
    private static final String TAG = "IOUtils";

    public static int readShort(InputStream in) throws IOException {
		byte[] buffer = null;

		try {
			buffer = new byte[2];
		} catch (OutOfMemoryError oom) {
			return -1;
		}

		if (in.markSupported())
			in.mark(2);
		int count = in.read(buffer);
		// 没有读取到数据，或者数据不充分
		if (count <= 0) {
			buffer = null;

			return -1;
		} else if (count == 1) {
			in.reset();
			buffer = null;
			return -1;
		} else {
			int result = (((buffer[0] << 8) & 0xff00) | (buffer[1] & 0xff));
			buffer = null;
			return result;
		}
	}
	
	public static long readInt(InputStream in) throws IOException {
		byte[] buffer = null;
		try {
			buffer = new byte[4];
		} catch (OutOfMemoryError oom) {
			return -1L;
		}

		if (in.markSupported()) {
			in.mark(4);
		}
		int count = in.read(buffer, 0, 4);
		// 没有读取到数据，或者数据不充分
		if (count <= 0) {
			buffer = null;
			return -1L;
		} else if (count < 4) {
			in.reset();
			buffer = null;
			return -1L;
		} else {
			long result = (long) (((buffer[0] << 24) & 0xff000000) |
					((buffer[1] << 16) & 0xff0000) |
					((buffer[2] << 8) & 0xff00) |
					(buffer[3] & 0xff));

			buffer = null;
			return result;
		}
	}
	
	public static byte[] readBytes(InputStream in, int len) throws IOException {
		if( len <= 0 ) {
			return null;
		}
		
		int pos = 0, recvBytes = 0;
		byte[] buffer = null;
		
		try {
			buffer = new byte[len];			
		} catch (OutOfMemoryError oom) {
			return null;
		}
		
		try {
			while(pos < len && (recvBytes = in.read(buffer, pos, len - pos)) > 0) {
				pos += recvBytes;
			}	
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			return null;
		}
		
		return buffer;
	}

	// 如果内存申请失败，返回null
	public static CharSequence readString(InputStream in, int len) throws IOException {
		int leftBytes = len, recvBytes = 0;
		int bufLen = Math.min(leftBytes, MAX_BUFFER_BYTES);
		
		byte[] buffer = null;
		
		try {
			buffer = new byte[bufLen];			
		} catch (OutOfMemoryError oom) {
			return null;
		}
		
		ByteArrayOutputStream outputStream = null;
		
		try {
			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
			
			while(leftBytes > 0 && (recvBytes = in.read(buffer, 0, bufLen)) != -1 ) {
				outputStream.write(buffer, 0, recvBytes);
				leftBytes -= recvBytes;			
			}	
		} catch (Exception e) {
			e.printStackTrace();
			if(outputStream != null){
				outputStream.close();
			}
			return null;
		}
		
		
		buffer = null;
		String result = outputStream.toString();
		outputStream.close();
		return result;
		
	}
	
	// 如果空间申请失败，返回null
	public static CharSequence readString(InputStream in, int len, String characterSet) throws IOException {
		int leftBytes = len, recvBytes = 0;
		int bufLen = Math.min(leftBytes, MAX_BUFFER_BYTES);
		byte[] buffer = null;
		
		try {
			buffer = new byte[bufLen];			
		} catch (OutOfMemoryError oom) {
			return null;
		}
		
		ByteArrayOutputStream outputStream = null;
		
		try {
			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
			
			while(leftBytes > 0 && (recvBytes = in.read(buffer, 0, bufLen)) != -1 ) {
				outputStream.write(buffer, 0, recvBytes);
				leftBytes -= recvBytes;			
			}	
		} catch (Exception e) {
			e.printStackTrace();
			if(outputStream!=null){
				outputStream.close();
			}
			return null;
		}		
		
		buffer = null;
		String result = outputStream.toString(characterSet);
		outputStream.close();
		return result;
	}
	
	public static void writeShort(OutputStream out, int s) throws IOException {
		byte[] buffer = new byte[] {(byte)((s >> 8)& 0xff), (byte)(s&0xff)};
		
		out.write(buffer);
		out.flush();
		buffer = null;
	}
	
	public static void writeInt(OutputStream out, int s) throws IOException {
		byte[] buffer = new byte[] {
				(byte)((s >> 24)& 0xff), 
				(byte)((s >> 16)& 0xff),
				(byte)((s >> 8)& 0xff), 
				(byte)(s&0xff)};
		
		out.write(buffer);
		out.flush();			
		buffer = null;		
	}
	
	public static void writeString(OutputStream out, String s) throws IOException {
		out.write(s.getBytes());
		out.flush();
	}
	
	public static void writeString(OutputStream out, String s, String characterSet) throws IOException {
		out.write(s.getBytes(characterSet));
		out.flush();
	}
	
	public static void writeString(OutputStream out, String s, int fixedLen)throws IOException {
		byte[] bytes = s.getBytes("utf-8");
		if(fixedLen <= bytes.length){
			out.write(bytes, 0, fixedLen);
		} else {
			out.write(bytes);
			
			byte[] fillBytes = null;
			
			try {
				fillBytes = new byte[fixedLen - bytes.length];
				Arrays.fill(fillBytes, (byte)0);
				out.write(fillBytes);
			} catch (OutOfMemoryError oom) {
				oom.printStackTrace();
			}
		}
		
		out.flush();
	}
	
	public static void writeString(OutputStream out, String s, String characterSet, int fixedLen)throws IOException {
		byte[] bytes = s.getBytes(characterSet);
		if(fixedLen <= bytes.length){
			out.write(bytes, 0, fixedLen);
		} else {
			out.write(bytes);
			
			byte[] fillBytes = null;
			
			try {
				fillBytes = new byte[fixedLen - bytes.length];
				Arrays.fill(fillBytes, (byte)0);
				out.write(fillBytes);
			} catch (OutOfMemoryError oom) {
				oom.printStackTrace();
			}
		}
		
		out.flush();
	}
	
	public static ReadableByteChannel getChannel(InputStream inputStream) {
	    return (inputStream != null) ? Channels.newChannel(inputStream) : null;
	}

	   
    public static WritableByteChannel getChannel(OutputStream outputStream) {
        return (outputStream != null) ? Channels.newChannel(outputStream)
                : null;
    }
    
    public static long exhaust(InputStream input) throws IOException {
		long result = 0L;

		if (input != null) {
			byte[] buf = null;

			try {
				buf = new byte[MAX_BUFFER_BYTES / 2];
			} catch (OutOfMemoryError oom) {
				return result;
			}

			try {
				int read = input.read(buf);
				result = (read == -1) ? -1 : 0;

				while (read != -1) {
					result += read;
					read = input.read(buf);
				}
			} catch (IOException e) {
				throw e;
			} finally {
				buf = null;
			}
		}
        return result;
    }

    public static String readLeft(InputStream in) throws IOException {
		String result = null;
		if (in != null) {
			int recvBytes = 0;
			boolean cancel = false;
			byte[] buffer = null;

			try {
				buffer = new byte[MAX_BUFFER_BYTES / 2];
			} catch (OutOfMemoryError oom) {
				return result;
			}

			ByteArrayOutputStream outputStream = null;

			try {
				outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
				while ((recvBytes = in.read(buffer, 0, MAX_BUFFER_BYTES / 2)) > 0) {
					outputStream.write(buffer, 0, recvBytes);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						cancel = true;
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (outputStream != null) {
					outputStream.close();
				}
				in.close();
				return null;
			}
			buffer = null;
			if (!cancel) {
				result = outputStream.toString();
			}
			outputStream.close();
			in.close();
		}
		return result;
	}

	public static String readLeft(InputStream in, String characterSet) throws IOException {
    	String result = null;
    	if (in != null) {
        	int  recvBytes = 0;
        	boolean cancel = false;
    		byte[] buffer= null;
    		
    		try {
    			buffer = new byte[MAX_BUFFER_BYTES/2];
    		} catch (OutOfMemoryError oom) {
    			return result;
    		}
    		
    		ByteArrayOutputStream outputStream = null;
    		try {
    			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
    			while((recvBytes = in.read(buffer, 0, MAX_BUFFER_BYTES/2)) > 0) {
    				outputStream.write(buffer, 0, recvBytes);
    				try {
    					Thread.sleep(10);
    				} catch (InterruptedException e) {
    					cancel = true;
    					break;
    				}
    			}	
    		} catch (Exception e) {
    			e.printStackTrace();
    			if(outputStream != null){
    				outputStream.close();
    			}
        		in.close();
    			return null;
    		}
			
    		buffer = null;	
    		if (!cancel) {
    			result = outputStream.toString(characterSet);
    		}
    		outputStream.close();
    		in.close();
        }
        return result;
    }

	/**
	 * 注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 * 注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 *  注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 */
	public static byte[] readLeftBytes(InputStream in) throws IOException {
		if (in == null) {
			return null;
		}

		byte[] buffer = null;
		try {
			buffer = new byte[MAX_BUFFER_BYTES];
		} catch (OutOfMemoryError oom) {
			return null;
		}
		//ByteArrayOutputStream是内存读写流，不指向硬盘，不需要关闭
		ByteArrayOutputStream outputStream = null;
		byte[] result = null;
		int recvBytes = 0;
		boolean cancel = false;
		try {
			outputStream = new ByteArrayOutputStream(MAX_BUFFER_BYTES);
			while ((recvBytes = in.read(buffer, 0, MAX_BUFFER_BYTES)) > 0) {
				outputStream.write(buffer, 0, recvBytes);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					cancel = true;
					break;
				}
			}
			if (!cancel) {
				result = outputStream.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
    
    public static byte[] createZeroBytes(int length) {
    	if(length <= 0)
    		throw new IllegalArgumentException("length must be gt 0");
    	
    	byte[] bytes = null;
		
		try {
			bytes = new byte[length];			
		} catch (OutOfMemoryError oom) {
			return null;
		}
		
    	Arrays.fill(bytes,(byte)0);
    	return bytes;
    }    

    /**
	 * 从指定字节数组中查找某子字节数组的第一次出现的位置
	 * @param datas 指定数组
	 * @param start 起始查询位置
	 * @param t 待查询数组
	 * @return 如果没找到，返回-1，否则返回索引
	 */
	public static int indexOf(byte[] datas, int start, byte[] t) {
		
		if (datas == null || t == null) {
			throw new NullPointerException("source or target array is null!");
		}
		
		int index = -1;
		int len = datas.length;
		int tlen = t.length;
		
		if (start >= len || len-start < tlen ) {
			return -1;
		}

		while ( start <= len - tlen) {
			int i = 0;
			for ( ; i < tlen; i++) {
				if( datas[start+i] != t[i] ){					
					break;
				}					
			}
			
			if (i == tlen) {
				index = start;
				break;
			}
			
			start ++;
		}
		
		return index;
	}
	
	/**
	 * 从一个字节数组中解析一个整数
	 * @param buf 字节数组
	 * @param bigEndian 是否大字节序解析 
	 * @return 相应的整数
	 */
	public static int parseInteger(byte[] buf, boolean bigEndian) {
		return (int)parseNumber(buf, 4, bigEndian);
	}
	/**
	 * 从一个字节数组中解析一个短整数
	 * @param buf 字节数组
	 * @param bigEndian 是否大字节序解析 
	 * @return 相应的短整数
	 */
	public static int parseShort(byte[] buf, boolean bigEndian) {
		return (int)parseNumber(buf, 2, bigEndian);
	}
	
	/**
	 * 从一个字节数组中解析一个长整数
	 * @param buf 字节数组
	 * @param len 整数组成的字节数
	 * @param bigEndian 是否大字节序解析 
	 * @return 相应的长整数
	 */
	public static long parseNumber(byte[] buf, int len, boolean bigEndian) {
		if (buf == null || buf.length == 0) {
			throw new IllegalArgumentException("byte array is null or empty!");
		}
		int mlen = Math.min(len, buf.length);
		long r = 0;
		if (bigEndian)
			for (int i = 0; i < mlen; i++) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		else
			for (int i = mlen-1; i >= 0; i--) {
				r <<= 8;
				r |= (buf[i] & 0x000000ff);
			}
		return r;
	}

	/**
	 * 注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 * 注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 *  注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 */
	public static String convertStreamToString(InputStream is, String charset){

		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, charset));
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		} finally {
			IOUtils.closeIoStream(reader);
		}
		return sb.toString();
	}

	/**
	 * 注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 * 注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 *  注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 */
	public static String toString(InputStream is)throws IOException {
		byte[] buffer = null;
		try {
			buffer = new byte[1024];	
		} catch (OutOfMemoryError oom) {
			return null;
		}
		ByteArrayOutputStream baos = null;
		int totalRead = 0;
		int read = 0;
		try {
			baos = new ByteArrayOutputStream();
			while ((read = is.read(buffer, totalRead, 1024)) > 0) {
				baos.write(buffer, totalRead, read);
			}
			return baos.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeIoStream(baos);
		}
	}

	/**
	 * 注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 * 注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 *  注意！！！
	 * 传入的流要谨慎，要在源头上关闭
	 */
	/*public static byte[] convertStreamToBytes(InputStream is) throws IOException {
		ByteArrayOutputStream baos = null;
		byte[] buffer = null;
		try {
			buffer = new byte[1024];
		} catch (OutOfMemoryError oom) {
			return null;
		}
		int totalRead = 0;
		int read = 0;
		try {
			baos = new ByteArrayOutputStream();
			while ((read = is.read(buffer, totalRead, 1024)) > 0) {
				baos.write(buffer, totalRead, read);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			IOUtils.closeIoStream(baos);
		}
	}*/
	
	public static String readLine(InputStream in) {
		StringBuilder stringBuilder = new StringBuilder();
		int i = -1;
		try {
			while ((i = in.read()) != -1) {
				char c = (char) i;
				if (c == '\n') {
					stringBuilder.append("\r\n");
					break;
				}
				if (c != '\r') {
					stringBuilder.append(c);
				}
			}
		} catch (Exception localException) {
			if (stringBuilder.length() == 0) {
				if (i == -1) {
					return null;
				}
				return "";
			}
		}
		return stringBuilder.toString();
	}

	public static String stringFromFile(final File file) {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			byte[] buffer = new byte[fin.available()];
			fin.read(buffer);
			return new String(buffer);
		} catch (Exception e) {
		} finally {
			IOUtils.closeIoStream(fin);
		}
		return "";
	}
	public static String stringFromFile(final String path) {
		return stringFromFile(new File(path));
	}
	
	public static byte[] bytesFromFile(final File file) {
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			byte[] buffer = new byte[fin.available()];
			fin.read(buffer);
			return buffer;
		} catch (Exception e) {
		} finally {
			IOUtils.closeIoStream(fin);
		}
		return null;
	}
	public static byte[] bytesFromFile(final String path) {
		return bytesFromFile(new File(path));
	}
	
	public static boolean saveBytesToFile(final byte[] data, final String path) {
		if (data == null || data.length==0 || TextUtils.isEmpty(path)) {
			return false;
		}
		try {
			FileOutputStream fout = new FileOutputStream(new File(path));
			try {
				fout.write(data);
			} finally {
				fout.close();
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

    public static void closeIoStream(Closeable closeable) {
		if (closeable != null && closeable instanceof Closeable) {
			try {
				closeable.close();
			} catch (IOException e) {
				// silence
			}
		}
    }

	public static double readDouble(InputStream in) throws IOException {

		byte[] buffer = new byte[8];
		if (in.markSupported()) {
			in.mark(8);
		}
		int count = in.read(buffer, 0, 8);
		// 没有读取到数据，或者数据不充分
		if (count <= 0) {
			buffer = null;
			return (Double) null;
		} else if (count < 8) {
			in.reset();
			buffer = null;
			return (Double) null;
		} else {
			int i = 0;
			long accum = 0;
			for (int shiftBy = 0; shiftBy < 64; shiftBy += 8) {
				accum |= ((long) (buffer[i] & 0xff)) << shiftBy;
				i++;
			}
			buffer = null;
			return Double.longBitsToDouble(accum);
		}
	}
}
