package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class BusinessOverViewCollection extends AbstractObjectCollection 
{
    public BusinessOverViewCollection()
    {
        super(BusinessOverViewInfo.class);
    }
    public boolean add(BusinessOverViewInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(BusinessOverViewCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(BusinessOverViewInfo item)
    {
        return removeObject(item);
    }
    public BusinessOverViewInfo get(int index)
    {
        return(BusinessOverViewInfo)getObject(index);
    }
    public BusinessOverViewInfo get(Object key)
    {
        return(BusinessOverViewInfo)getObject(key);
    }
    public void set(int index, BusinessOverViewInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(BusinessOverViewInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(BusinessOverViewInfo item)
    {
        return super.indexOf(item);
    }
}