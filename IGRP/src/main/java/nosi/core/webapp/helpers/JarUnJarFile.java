/**
 * @author: Emanuel Pereira
 * 
 * June 06, 2017
 *
 * Description: save files in jar format and read extract on far format
 */

package nosi.core.webapp.helpers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedInputStream;
import java.util.zip.CheckedOutputStream;
import javax.servlet.http.Part;

import nosi.core.webapp.import_export.FileImportAppOrPage;


public class JarUnJarFile {

	public static String encode = FileHelper.ENCODE_UTF8;
	
	public static byte[] convertFilesToJarBytes(Map<String,String>files,int level){
		byte[] result = null;
		if(files.size() > 0 && (level >= 0 && level <= 9))
		try{
			ByteArrayOutputStream fos = new ByteArrayOutputStream();
			CheckedOutputStream cos = new CheckedOutputStream(fos, new Adler32());
			JarOutputStream jos = new JarOutputStream(new BufferedOutputStream(cos));
			Set<Entry<String, String>> entry = files.entrySet();
			for(Entry<String,String> e:entry){
				JarEntry je = new JarEntry(e.getKey());
				jos.putNextEntry(je);
				InputStream fis = FileHelper.convertStringToInputStream(e.getValue());
				for(int r=fis.read();r!=-1;r=fis.read()){
					jos.write(r);
				}
				fis.close();
			}
			jos.close();
			result = fos.toByteArray();
			fos.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result;
	}
	
	//save data to jar format
	public static boolean saveJarFiles(String jarName,Map<String,String>files,int level){
		boolean result = false;
		jarName = jarName.endsWith(".jar")?jarName:(jarName+".jar");
		if(files.size() > 0 && (level >= 0 && level <= 9))
		try{
			FileOutputStream fos = new FileOutputStream(jarName);
			CheckedOutputStream cos = new CheckedOutputStream(fos, new Adler32());
			JarOutputStream jos = new JarOutputStream(new BufferedOutputStream(cos));
			Set<Entry<String, String>> entry = files.entrySet();
			for(Entry<String,String> e:entry){
				JarEntry je = new JarEntry(e.getKey());
				jos.putNextEntry(je);
				FileInputStream fis = new FileInputStream(e.getValue());
				for(int r=fis.read();r!=-1;r=fis.read()){
					jos.write(r);
				}
				fis.close();
			}
			jos.close();
			result = true;
		}catch(IOException e){
			result = false;
			e.printStackTrace();
		}
		return result;
	}
	
	public static Map<String,String> readJarFile(Part file){
		Map<String,String> files = new LinkedHashMap<>();
		try{
			CheckedInputStream cis = new CheckedInputStream(file.getInputStream(), new Adler32());
			JarInputStream jis = new JarInputStream(new BufferedInputStream(cis));
			JarEntry entry = null;
			while((entry=jis.getNextJarEntry())!=null){	
				   String         ls = System.getProperty("line.separator");
				   String         line = null;
				   DataInputStream in = new DataInputStream(jis); 
				   StringBuilder content = new StringBuilder();  
				   BufferedReader d = new BufferedReader(new InputStreamReader(in,encode));
				   while((line=d.readLine())!=null){
				   	content.append(line);
				   	content.append(ls);
				   }
				   files.put(entry.getName(), content.toString());
				   jis.closeEntry();
			}
			jis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return files;
	}

	//Extract files jar format
	public static List<FileImportAppOrPage> getJarFiles(Part file){
		List<FileImportAppOrPage> contents = null;
		try{
			contents = new ArrayList<>();
			CheckedInputStream cis = new CheckedInputStream(file.getInputStream(), new Adler32());
			JarInputStream jis = new JarInputStream(new BufferedInputStream(cis));
			JarEntry entry = null;
			while((entry=jis.getNextJarEntry())!=null){	
				   String         ls = System.getProperty("line.separator");
				   String         line = null;
				   DataInputStream in = new DataInputStream(jis); 
				   StringBuilder content = new StringBuilder();  
				   BufferedReader d = new BufferedReader(new InputStreamReader(in,encode));
				   while((line=d.readLine())!=null){
				   	content.append(line);
				   	content.append(ls);
				   }
				   int order = 4;
				   
				   if(entry.getName().startsWith("configApp")){
					   order = 0;
				   }
				   if(entry.getName().startsWith("configDBApp")){
					   order = 1;
				   }
				   if(entry.getName().startsWith("configHibernate")){
					   order = 2;
				   }
				   if(entry.getName().startsWith("configPage")){
					   order = 3;
				   }
				   FileImportAppOrPage f = new FileImportAppOrPage(entry.getName(), content.toString(), order);
				   
				contents.add(f);
				jis.closeEntry();
			}
			jis.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		Collections.sort(contents);
		return contents;
	}
	
	 public static void copyStream(OutputStream outputStream, InputStream inputStream) throws IOException {
		    byte[] bytes = new byte[4096];
		    int read = inputStream.read(bytes, 0, 4096);
		    while (read > 0) {
		      outputStream.write(bytes, 0, read);
		      read = inputStream.read(bytes, 0, 4096);
		    }
	}
	
	
}
