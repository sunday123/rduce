package web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;



public class FileServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  public FileServlet() {
	    super();
    }
  
  @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	 doPost(request, response);
}
 
  protected void file01(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean isMp=ServletFileUpload.isMultipartContent(request);
		System.out.println(isMp);
		FileOutputStream fos=null;
		InputStream is=null;
		try {
			if(isMp){
				ServletFileUpload upload=new ServletFileUpload();
				FileItemIterator iter=upload.getItemIterator(request);
				while(iter.hasNext()){
					FileItemStream fis=iter.next();
					is=fis.openStream();
					if(fis.isFormField()){
						System.out.println(fis.getFieldName());
						System.out.println(Streams.asString(is));
					}else{
						System.out.println(fis.getName());
						String path = request.getSession().getServletContext().getRealPath("/upload");
						path = path+"/"+fis.getName();
						System.out.println(path);
						fos=new FileOutputStream(path);
						byte[] buf=new byte[1024];
						int len=0;
						while((len=is.read(buf))>0){
							fos.write(buf, 0, len);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			if(is!=null) is.close();
			if(fos!=null) fos.close();
		}
	}
	 
   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
	 file02(request, response);
}
   protected void file02(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 if(ServletFileUpload.isMultipartContent(request)){
			 request=new MultipartRequest(request);
		 }
		 System.out.println(request.getParameter("username"));
		 System.out.println(request.getParameter("photo"));
		 String[] interest=request.getParameterValues("interest");
		 for(String in:interest){
			 System.out.println(in);
		 }
	}
}
