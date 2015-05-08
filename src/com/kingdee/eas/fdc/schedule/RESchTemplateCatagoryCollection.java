package com.kingdee.eas.fdc.schedule;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class RESchTemplateCatagoryCollection extends AbstractObjectCollection 
{
    public RESchTemplateCatagoryCollection()
    {
        super(RESchTemplateCatagoryInfo.class);
    }
    public boolean add(RESchTemplateCatagoryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(RESchTemplateCatagoryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(RESchTemplateCatagoryInfo item)
    {
        return removeObject(item);
    }
    public RESchTemplateCatagoryInfo get(int index)
    {
        return(RESchTemplateCatagoryInfo)getObject(index);
    }
    public RESchTemplateCatagoryInfo get(Object key)
    {
        return(RESchTemplateCatagoryInfo)getObject(key);
    }
    public void set(int index, RESchTemplateCatagoryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(RESchTemplateCatagoryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(RESchTemplateCatagoryInfo item)
    {
        return super.indexOf(item);
    }
}