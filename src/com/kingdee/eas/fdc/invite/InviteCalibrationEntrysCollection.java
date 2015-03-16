package com.kingdee.eas.fdc.invite;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class InviteCalibrationEntrysCollection extends AbstractObjectCollection 
{
    public InviteCalibrationEntrysCollection()
    {
        super(InviteCalibrationEntrysInfo.class);
    }
    public boolean add(InviteCalibrationEntrysInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(InviteCalibrationEntrysCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(InviteCalibrationEntrysInfo item)
    {
        return removeObject(item);
    }
    public InviteCalibrationEntrysInfo get(int index)
    {
        return(InviteCalibrationEntrysInfo)getObject(index);
    }
    public InviteCalibrationEntrysInfo get(Object key)
    {
        return(InviteCalibrationEntrysInfo)getObject(key);
    }
    public void set(int index, InviteCalibrationEntrysInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(InviteCalibrationEntrysInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(InviteCalibrationEntrysInfo item)
    {
        return super.indexOf(item);
    }
}