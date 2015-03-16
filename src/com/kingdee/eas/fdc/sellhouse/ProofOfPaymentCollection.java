package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ProofOfPaymentCollection extends AbstractObjectCollection 
{
    public ProofOfPaymentCollection()
    {
        super(ProofOfPaymentInfo.class);
    }
    public boolean add(ProofOfPaymentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ProofOfPaymentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ProofOfPaymentInfo item)
    {
        return removeObject(item);
    }
    public ProofOfPaymentInfo get(int index)
    {
        return(ProofOfPaymentInfo)getObject(index);
    }
    public ProofOfPaymentInfo get(Object key)
    {
        return(ProofOfPaymentInfo)getObject(key);
    }
    public void set(int index, ProofOfPaymentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ProofOfPaymentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ProofOfPaymentInfo item)
    {
        return super.indexOf(item);
    }
}