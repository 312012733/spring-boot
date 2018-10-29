package com.my.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.my.vo.ErrorHanlder;

@Controller
public class UpdateAndDownloadController
{
    @RequestMapping(path = "/upload")
    public ResponseEntity<Object> upload(MultipartHttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        try
        {
            Iterator<String> fileNames = req.getFileNames();
            
            String uploadDir = req.getServletContext().getRealPath("upload");
            
            while (fileNames.hasNext())
            {
                String name = fileNames.next();
                
                List<MultipartFile> files = req.getFiles(name);
                
                for (MultipartFile multipartFile : files)
                {
                    multipartFile
                            .transferTo(new File(uploadDir + File.separator + multipartFile.getOriginalFilename()));
                }
            }
            
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/download")
    public ResponseEntity<Object> download(String fileName, HttpServletRequest req, HttpServletResponse resp)
            throws IOException
    {
        try
        {
            InputStream in = req.getServletContext().getResourceAsStream("upload" + File.separator + fileName);
            
            if (null == in)
            {
                throw new SecurityException("file is not found. " + fileName);
            }
            
            byte[] file = FileCopyUtils.copyToByteArray(in);
            
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
            
            return new ResponseEntity<Object>(file, headers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            
            return new ResponseEntity<Object>(new ErrorHanlder(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
