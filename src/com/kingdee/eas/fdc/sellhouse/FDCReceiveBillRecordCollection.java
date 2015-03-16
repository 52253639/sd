package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCReceiveBillRecordCollection extends AbstractObjectCollection 
{
    public FDCReceiveBillRecordCollection()
    {
        super(FDCReceiveBillRecordInfo.class);
    }
    public boolean add(FDCReceiveBillRecordInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCReceiveBillRecordCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCReceiveBillRecordInfo item)
    {
        return removeObject(item);
    }
    public FDCReceiveBillRecordInfo get(int index)
    {
        return(FDCReceiveBillRecordInfo)getObject(index);
    }
    public FDCReceiveBillRecordInfo get(Object key)
    {
        return(FDCReceiveBillRecordInfo)getObject(key);
    }
    public void set(int index, FDCReceiveBillRecordInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCReceiveBillRecordInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCReceiveBillRecordInfo item)
    {
        return super.indexOf(item);
    }
}