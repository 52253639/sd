package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpertQualifyCollection extends AbstractObjectCollection 
{
    public ExpertQualifyCollection()
    {
        super(ExpertQualifyInfo.class);
    }
    public boolean add(ExpertQualifyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpertQualifyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpertQualifyInfo item)
    {
        return removeObject(item);
    }
    public ExpertQualifyInfo get(int index)
    {
        return(ExpertQualifyInfo)getObject(index);
    }
    public ExpertQualifyInfo get(Object key)
    {
        return(ExpertQualifyInfo)getObject(key);
    }
    public void set(int index, ExpertQualifyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpertQualifyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpertQualifyInfo item)
    {
        return super.indexOf(item);
    }
}