package top.linjia.wizarposapp.beans;

public class OrderCacel {

    /**
     * result :
     * state : 1
     * desc : 订单取消成功！
     */

    private String result;
    private int state;
    private String desc;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
