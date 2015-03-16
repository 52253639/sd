package com.kingdee.eas.fdc.finance;

import java.util.Enumeration;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.framework.agent.AgentManager;
import com.kingdee.bos.framework.agent.AgentState;
import com.kingdee.bos.framework.agent.IObjectValueAgent;
import com.kingdee.bos.framework.agent.AgentContainerFactory;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.util.BOSObjectType;

public class PaymentSplitEntryAgent extends PaymentSplitEntryInfo implements IObjectValueAgent{
    public static final BOSObjectType bosType = new BOSObjectType("27BEF6EF");
    private final AgentManager manager;

    private PaymentSplitEntryAgent ()
    {
        manager = new AgentManager();
    }
    
    public static PaymentSplitEntryAgent copyOvToAgent(IObjectValue vo){
        PaymentSplitEntryAgent agent = new PaymentSplitEntryAgent();
        return (PaymentSplitEntryAgent)agent.manager.copyOvToAgent(agent,vo);
    }

    public static PaymentSplitEntryAgent copyOvAsNewAgent(IObjectValue vo){
        PaymentSplitEntryAgent agent = new PaymentSplitEntryAgent();
        return (PaymentSplitEntryAgent)agent.manager.copyOvToAgentAsNew(agent,vo);
    }

    public static PaymentSplitEntryAgent find(IObjectPK id) throws BOSException
    {
		PaymentSplitEntryAgent agent = new PaymentSplitEntryAgent();
        return (PaymentSplitEntryAgent)agent.manager.find(agent, id);
    }
    
    public static PaymentSplitEntryAgent create() throws BOSException
    {
        return (PaymentSplitEntryAgent)AgentManager.create(new PaymentSplitEntryAgent());
    }

    public void agentRemove() throws BOSException
    {
        AgentManager.remove(this);
    }

    public IObjectPK agentSave() throws BOSException
    {
        return AgentManager.save(this);
    }

    public AgentState getAgentState()
    {
        return manager.getAgentState();
    }

    public void setAgentState(AgentState state)
    {
        manager.setAgentState(state);
    }

    public void recursiveSetAgentState(AgentState state) {
    	recursiveSetAgentState(state, new java.util.HashSet());
    }

    public void recursiveSetAgentState(AgentState state, java.util.Set handledSet) {
    	if (handledSet == null) {
    		throw new IllegalArgumentException("The second argument should not be null.");
    	}
    	setAgentState(state);
    	handledSet.add(new Integer(System.identityHashCode(this)));
    	
    	Enumeration enumer = this.keys();
    	Object obj = null;
    	AbstractObjectCollection collection = null;
    	while (enumer.hasMoreElements()) {
    		obj = this.get((String)enumer.nextElement());
    		if (obj instanceof IObjectValueAgent) {
    			if (! handledSet.contains(new Integer(System.identityHashCode(obj)))) {
    				((IObjectValueAgent)obj).recursiveSetAgentState(state, handledSet);
    			}
    		} else if (obj instanceof AbstractObjectCollection) {
    			collection = (AbstractObjectCollection)obj;
    			IObjectValue vo = null;
    			for(int i = 0, j = collection.size(); i < j; i++) {
    				vo = collection.getObject(i);
    				if (vo instanceof IObjectValueAgent) {
    					if (! handledSet.contains(new Integer(System.identityHashCode(vo)))) {    					
    						((IObjectValueAgent)vo).recursiveSetAgentState(state, handledSet);
    					}
    				}
    			}
    		}
    	}
    }


    public IObjectValue getTarget()
    {
        return this;
    }
    
    public IObjectValue getInfoInstance() 
    {
        return manager.getSourceInfo();
    }

}
