package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SubjectLevelCollection extends AbstractObjectCollection 
{
    public SubjectLevelCollection()
    {
        super(SubjectLevelInfo.class);
    }
    public boolean add(SubjectLevelInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SubjectLevelCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SubjectLevelInfo item)
    {
        return removeObject(item);
    }
    public SubjectLevelInfo get(int index)
    {
        return(SubjectLevelInfo)getObject(index);
    }
    public SubjectLevelInfo get(Object key)
    {
        return(SubjectLevelInfo)getObject(key);
    }
    public void set(int index, SubjectLevelInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SubjectLevelInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SubjectLevelInfo item)
    {
        return super.indexOf(item);
    }
}