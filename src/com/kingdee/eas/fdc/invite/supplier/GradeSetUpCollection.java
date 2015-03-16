package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class GradeSetUpCollection extends AbstractObjectCollection 
{
    public GradeSetUpCollection()
    {
        super(GradeSetUpInfo.class);
    }
    public boolean add(GradeSetUpInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(GradeSetUpCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(GradeSetUpInfo item)
    {
        return removeObject(item);
    }
    public GradeSetUpInfo get(int index)
    {
        return(GradeSetUpInfo)getObject(index);
    }
    public GradeSetUpInfo get(Object key)
    {
        return(GradeSetUpInfo)getObject(key);
    }
    public void set(int index, GradeSetUpInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(GradeSetUpInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(GradeSetUpInfo item)
    {
        return super.indexOf(item);
    }
}