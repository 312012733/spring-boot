package com.my.vo;

public class ErrorHanlder
{
    
    private String errorMsg;
    
    public ErrorHanlder()
    {
    }
    
    public ErrorHanlder(String errorMsg)
    {
        this.errorMsg = errorMsg;
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
        return "ErroHanlder [errorMsg=" + errorMsg + "]";
    }
    
}
