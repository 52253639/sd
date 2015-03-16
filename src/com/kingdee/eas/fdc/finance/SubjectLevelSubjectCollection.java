package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SubjectLevelSubjectCollection extends AbstractObjectCollection 
{
    public SubjectLevelSubjectCollection()
    {
        super(SubjectLevelSubjectInfo.class);
    }
    public boolean add(SubjectLevelSubjectInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SubjectLevelSubjectCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SubjectLevelSubjectInfo item)
    {
        return removeObject(item);
    }
    public SubjectLevelSubjectInfo get(int index)
    {
        return(SubjectLevelSubjectInfo)getObject(index);
    }
    public SubjectLevelSubjectInfo get(Object key)
    {
        return(SubjectLevelSubjectInfo)getObject(key);
    }
    public void set(int index, SubjectLevelSubjectInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SubjectLevelSubjectInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SubjectLevelSubjectInfo item)
    {
        return super.indexOf(item);
    }
}