package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class SharePPMCollection extends AbstractObjectCollection 
{
    public SharePPMCollection()
    {
        super(SharePPMInfo.class);
    }
    public boolean add(SharePPMInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(SharePPMCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(SharePPMInfo item)
    {
        return removeObject(item);
    }
    public SharePPMInfo get(int index)
    {
        return(SharePPMInfo)getObject(index);
    }
    public SharePPMInfo get(Object key)
    {
        return(SharePPMInfo)getObject(key);
    }
    public void set(int index, SharePPMInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(SharePPMInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(SharePPMInfo item)
    {
        return super.indexOf(item);
    }
}