package cn.org.citycloud.srdz.bean;

/**
 * Object比较结果bean，用于日志记录.
 *
 * @author demon
 * @Date 2016/5/13 16:43
 */
public class BeanCompareResultBean {
    /** 没修改之前的值 */
    private Object oldValue;

    /** 修改后的值 */
    private Object newValue;

    public BeanCompareResultBean() {
    }

    public BeanCompareResultBean(Object oldValue, Object newValue) {
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
}
