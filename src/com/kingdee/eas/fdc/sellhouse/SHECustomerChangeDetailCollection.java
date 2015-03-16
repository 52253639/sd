package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SHECustomerChangeDetailCollection extends AbstractObjectCollection 
{
    public SHECustomerChangeDetailCollection()
    {
        super(SHECustomerChangeDetailInfo.class);
    }
    public boolean add(SHECustomerChangeDetailInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SHECustomerChangeDetailCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SHECustomerChangeDetailInfo item)
    {
        return removeObject(item);
    }
    public SHECustomerChangeDetailInfo get(int index)
    {
        return(SHECustomerChangeDetailInfo)getObject(index);
    }
    public SHECustomerChangeDetailInfo get(Object key)
    {
        return(SHECustomerChangeDetailInfo)getObject(key);
    }
    public void set(int index, SHECustomerChangeDetailInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SHECustomerChangeDetailInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SHECustomerChangeDetailInfo item)
    {
        return super.indexOf(item);
    }
}