package com.kingdee.eas.fdc.finance;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FDCProDepConPayContractEntryCollection extends AbstractObjectCollection 
{
    public FDCProDepConPayContractEntryCollection()
    {
        super(FDCProDepConPayContractEntryInfo.class);
    }
    public boolean add(FDCProDepConPayContractEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FDCProDepConPayContractEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FDCProDepConPayContractEntryInfo item)
    {
        return removeObject(item);
    }
    public FDCProDepConPayContractEntryInfo get(int index)
    {
        return(FDCProDepConPayContractEntryInfo)getObject(index);
    }
    public FDCProDepConPayContractEntryInfo get(Object key)
    {
        return(FDCProDepConPayContractEntryInfo)getObject(key);
    }
    public void set(int index, FDCProDepConPayContractEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FDCProDepConPayContractEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FDCProDepConPayContractEntryInfo item)
    {
        return super.indexOf(item);
    }
}