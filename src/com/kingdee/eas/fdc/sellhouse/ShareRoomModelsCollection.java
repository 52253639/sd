package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ShareRoomModelsCollection extends AbstractObjectCollection 
{
    public ShareRoomModelsCollection()
    {
        super(ShareRoomModelsInfo.class);
    }
    public boolean add(ShareRoomModelsInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ShareRoomModelsCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ShareRoomModelsInfo item)
    {
        return removeObject(item);
    }
    public ShareRoomModelsInfo get(int index)
    {
        return(ShareRoomModelsInfo)getObject(index);
    }
    public ShareRoomModelsInfo get(Object key)
    {
        return(ShareRoomModelsInfo)getObject(key);
    }
    public void set(int index, ShareRoomModelsInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ShareRoomModelsInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ShareRoomModelsInfo item)
    {
        return super.indexOf(item);
    }
}