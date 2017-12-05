package util;

import com.alibaba.fastjson.JSON;

public class Resp {
    private String status;
    private String message;
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.status = "success";
        this.data = data;
    }

    public void setData(Object data, String msg) {
        this.status = "success";
        this.data = data;
        this.message = msg;
    }

    public void setFailed(Exception e){
        this.setStatus("Failed");
        this.setMessage(e.getCause().toString());
    }


    public void setFailed(String msg){
        this.setStatus("Failed");
        this.setMessage(msg);
    }

    public String toJSON(){
        return JSON.toJSONString(this);
    }

}
