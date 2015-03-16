package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class MuMemberSellProPercentCollection extends AbstractObjectCollection 
{
    public MuMemberSellProPercentCollection()
    {
        super(MuMemberSellProPercentInfo.class);
    }
    public boolean add(MuMemberSellProPercentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(MuMemberSellProPercentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(MuMemberSellProPercentInfo item)
    {
        return removeObject(item);
    }
    public MuMemberSellProPercentInfo get(int index)
    {
        return(MuMemberSellProPercentInfo)getObject(index);
    }
    public MuMemberSellProPercentInfo get(Object key)
    {
        return(MuMemberSellProPercentInfo)getObject(key);
    }
    public void set(int index, MuMemberSellProPercentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(MuMemberSellProPercentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(MuMemberSellProPercentInfo item)
    {
        return super.indexOf(item);
    }
}