package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShareOrgUnitCollection extends AbstractObjectCollection 
{
    public ShareOrgUnitCollection()
    {
        super(ShareOrgUnitInfo.class);
    }
    public boolean add(ShareOrgUnitInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShareOrgUnitCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShareOrgUnitInfo item)
    {
        return removeObject(item);
    }
    public ShareOrgUnitInfo get(int index)
    {
        return(ShareOrgUnitInfo)getObject(index);
    }
    public ShareOrgUnitInfo get(Object key)
    {
        return(ShareOrgUnitInfo)getObject(key);
    }
    public void set(int index, ShareOrgUnitInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShareOrgUnitInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShareOrgUnitInfo item)
    {
        return super.indexOf(item);
    }
}