package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyDisPlaySetCollection extends AbstractObjectCollection 
{
    public TenancyDisPlaySetCollection()
    {
        super(TenancyDisPlaySetInfo.class);
    }
    public boolean add(TenancyDisPlaySetInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyDisPlaySetCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyDisPlaySetInfo item)
    {
        return removeObject(item);
    }
    public TenancyDisPlaySetInfo get(int index)
    {
        return(TenancyDisPlaySetInfo)getObject(index);
    }
    public TenancyDisPlaySetInfo get(Object key)
    {
        return(TenancyDisPlaySetInfo)getObject(key);
    }
    public void set(int index, TenancyDisPlaySetInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyDisPlaySetInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyDisPlaySetInfo item)
    {
        return super.indexOf(item);
    }
}