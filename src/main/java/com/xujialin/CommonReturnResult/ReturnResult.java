package com.xujialin.CommonReturnResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XuJiaLin
 * @date 2021/9/12 15:11
 */

@Data
@NoArgsConstructor
public class ReturnResult {
    private String Code;
    private Boolean State;
    private Object Data;

    public ReturnResult(String Code, boolean State){
        this.Code=Code;
        this.State=State;
    }

    public ReturnResult(Boolean state)
    {
        this.State = state;
    }

    public ReturnResult(String Code, boolean state, Object Data)
    {
        this.State=state;
        this.Code=Code;
        this.Data=Data;
    }


}
