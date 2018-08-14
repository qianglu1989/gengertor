package ${packageStr};

import com.alibaba.fastjson.JSON;
import com.ecej.nove.base.po.BasePo;
import com.ecej.cc.core.annotation.Id;
${importStr}

 /**
 * 
 * @Description: ${tableName}
 * @author ${author}
 * @date ${time}
 *
 */
public class ${className} extends BasePo {

    private static final long serialVersionUID = 1L;
    
${propertiesStr}
${methodStr}
	@Override
    public String toString()
    {
        return JSON.toJSONString(this);
    }
}