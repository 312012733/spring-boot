package com.service.impl;

import com.bean.StudentIdCard;
import com.dao.IStudentIdCardDao;
import com.service.IStuIdCardService;

public class StuIdCardServiceImpl implements IStuIdCardService
{
    private IStudentIdCardDao idCardDao;
    
    public StudentIdCard findIdCardByStuId(String stuId)
    {
        return idCardDao.findIdCardByStuId(stuId);
    }
    
    public void save(StudentIdCard studentIdCard)
    {
        
        idCardDao.save(studentIdCard);
    }
    
    @Override
    public void update(StudentIdCard studentIdCard)
    {
        idCardDao.update(studentIdCard);
    }
    
    @Override
    public void delete(String idCardId)
    {
        idCardDao.delete(idCardId);
    }
    
    @Override
    public void batDelete(String[] ids)
    {
        idCardDao.batDelete(ids);
    }
    
}
