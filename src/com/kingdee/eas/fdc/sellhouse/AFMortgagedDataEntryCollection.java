package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class AFMortgagedDataEntryCollection extends AbstractObjectCollection 
{
    public AFMortgagedDataEntryCollection()
    {
        super(AFMortgagedDataEntryInfo.class);
    }
    public boolean add(AFMortgagedDataEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(AFMortgagedDataEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(AFMortgagedDataEntryInfo item)
    {
        return removeObject(item);
    }
    public AFMortgagedDataEntryInfo get(int index)
    {
        return(AFMortgagedDataEntryInfo)getObject(index);
    }
    public AFMortgagedDataEntryInfo get(Object key)
    {
        return(AFMortgagedDataEntryInfo)getObject(key);
    }
    public void set(int index, AFMortgagedDataEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(AFMortgagedDataEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(AFMortgagedDataEntryInfo item)
    {
        return super.indexOf(item);
    }
}