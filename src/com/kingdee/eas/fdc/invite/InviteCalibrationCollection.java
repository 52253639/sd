package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteCalibrationCollection extends AbstractObjectCollection 
{
    public InviteCalibrationCollection()
    {
        super(InviteCalibrationInfo.class);
    }
    public boolean add(InviteCalibrationInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteCalibrationCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteCalibrationInfo item)
    {
        return removeObject(item);
    }
    public InviteCalibrationInfo get(int index)
    {
        return(InviteCalibrationInfo)getObject(index);
    }
    public InviteCalibrationInfo get(Object key)
    {
        return(InviteCalibrationInfo)getObject(key);
    }
    public void set(int index, InviteCalibrationInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteCalibrationInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteCalibrationInfo item)
    {
        return super.indexOf(item);
    }
}