package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.util.Null;
import com.kingdee.util.PropertyContainer;
import java.util.Map;

abstract class MyPropertyContainer extends PropertyContainer
{

    MyPropertyContainer()
    {
    }

    public Map getValues()
    {
        return values;
    }

    public Object get(String key)
    {
        Object obj = values.get(key);
        if(obj instanceof Null)
            return null;
        else
            return obj;
    }

    public Object get(String key, Object defaultValue)
    {
        Object obj = values.get(key);
        if(obj == null)
            return defaultValue;
        if(obj instanceof Null)
            return null;
        else
            return obj;
    }

    public Object put(String key, Object value)
    {
        if(value == null)
        {
            values.put(key, Null.NULL);
            return null;
        } else
        {
            return values.put(key, value);
        }
    }

    public Object remove(String key)
    {
        return values.remove(key);
    }

    public boolean containsKey(String key)
    {
        return values.containsKey(key);
    }
}