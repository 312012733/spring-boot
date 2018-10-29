package com.my.vo;

public class ResponseEntity
{
    private Object content;
    
    private String errorMsg;
    
    public ResponseEntity()
    {
    }
    
    public Object getContent()
    {
        return content;
    }
    
    public void setContent(Object content)
    {
        this.content = content;
    }
    
    public String getErrorMsg()
    {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    @Override
    public String toString()
    {
        return "ResponseEntity [content=" + content + ", errorMsg=" + errorMsg + "]";
    }
    
}
