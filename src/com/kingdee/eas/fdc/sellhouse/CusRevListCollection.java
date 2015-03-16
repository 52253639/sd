package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class CusRevListCollection extends AbstractObjectCollection 
{
    public CusRevListCollection()
    {
        super(CusRevListInfo.class);
    }
    public boolean add(CusRevListInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(CusRevListCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(CusRevListInfo item)
    {
        return removeObject(item);
    }
    public CusRevListInfo get(int index)
    {
        return(CusRevListInfo)getObject(index);
    }
    public CusRevListInfo get(Object key)
    {
        return(CusRevListInfo)getObject(key);
    }
    public void set(int index, CusRevListInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(CusRevListInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(CusRevListInfo item)
    {
        return super.indexOf(item);
    }
}