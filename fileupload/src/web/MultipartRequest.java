package web;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FilenameUtils;


public class MultipartRequest extends HttpServletRequestWrapper{

	/**
	 * 
	 */

	Map<String, String[]> param=new HashMap<String,String[]>();
	public final static String path1 = "D:\\file\\WebContent\\";
	public MultipartRequest(HttpServletRequest request) {
		super(request);
		setParam(request);
		// TODO Auto-generated constructor stub
	}
	private void setParam(HttpServletRequest request) {
		// TODO Auto-generated method stub
      try {
    		boolean isMp=ServletFileUpload.isMultipartContent(request);
    		if(isMp){
				ServletFileUpload upload=new ServletFileUpload();
				FileItemIterator iter=upload.getItemIterator(request);
				InputStream is=null;
				while (iter.hasNext()) {
					FileItemStream fis =iter.next();
					is=fis.openStream();
					if(fis.isFormField()){
					   setFormParam(fis.getFieldName(),is);
					}else{
						if(is.available()>0){
							String fname=FilenameUtils.getName(fis.getName());
							System.out.println(fname);
							Streams.copy(is, new FileOutputStream(path1+"/upload/"+fname),true);
							param.put(fis.getFieldName(), new String[]{fname});
						}
					}
					
				} 
			} else {

				param= request.getParameterMap();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void setFormParam(String fieldName, InputStream is)throws IOException {
		// TODO Auto-generated method stub
		if(param.containsKey(fieldName)){
		String[] value=param.get(fieldName);
		value=Arrays.copyOf(value, value.length+1);
		value[value.length-1]=Streams.asString(is);
		param.put(fieldName, value);
	}else{
		param.put(fieldName, new String[]{Streams.asString(is)});
	}
}
	
	public Map<String, String[]>getParameterMap(){
		return param;
	}
	public String getParameter(String name){
		String[]value=param.get(name);
		if(value!=null){
			return value[0];
		}
		return null;
	}
	public String[] getParameterValues(String name){
		String[] value=param.get(name);
		if(value!=null){
			return value;
		}
		return null;
	}
}