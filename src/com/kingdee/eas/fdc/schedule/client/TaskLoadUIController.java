/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import org.apache.log4j.Logger;
import com.kingdee.bos.appframework.uip.ControllerBase;
import com.kingdee.bos.appframework.uip.Navigator;
import com.kingdee.bos.ui.face.CoreUIObject;

/**
 * output class name
 */
public class TaskLoadUIController extends AbstractTaskLoadUIController {
    private static final Logger logger = CoreUIObject.getLogger(TaskLoadUIController.class);
    
    /**
     * output class constructor
     */
    public TaskLoadUIController(Navigator navigator) {
        super(navigator);
    }

}