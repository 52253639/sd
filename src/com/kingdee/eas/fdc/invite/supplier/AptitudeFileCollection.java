package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AptitudeFileCollection extends AbstractObjectCollection 
{
    public AptitudeFileCollection()
    {
        super(AptitudeFileInfo.class);
    }
    public boolean add(AptitudeFileInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AptitudeFileCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AptitudeFileInfo item)
    {
        return removeObject(item);
    }
    public AptitudeFileInfo get(int index)
    {
        return(AptitudeFileInfo)getObject(index);
    }
    public AptitudeFileInfo get(Object key)
    {
        return(AptitudeFileInfo)getObject(key);
    }
    public void set(int index, AptitudeFileInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AptitudeFileInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AptitudeFileInfo item)
    {
        return super.indexOf(item);
    }
}