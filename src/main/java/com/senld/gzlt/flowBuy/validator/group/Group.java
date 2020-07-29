package com.senld.gzlt.flowBuy.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * 
 * @author system
 */
@GroupSequence({ AddGroup.class, UpdateGroup.class})
public interface Group {

}
