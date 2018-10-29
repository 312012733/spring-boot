package com.test.conditional;

public class WindowsListService implements ListService
{
    
    @Override
    public String showListCmd()
    {
        return "DIR";
    }
}
