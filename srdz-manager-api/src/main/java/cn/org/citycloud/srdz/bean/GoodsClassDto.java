package cn.org.citycloud.srdz.bean;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/27 10:12
 */
public class GoodsClassDto {
    private int classId;

    private String className;

    private long goodsCount;

    private String classImage;

    public GoodsClassDto() {
    }

    public GoodsClassDto(int classId, String className, long goodsCount, String classImage) {
        this.classId = classId;
        this.className = className;
        this.goodsCount = goodsCount;
        this.classImage = classImage;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(long goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getClassImage() {
        return classImage;
    }

    public void setClassImage(String classImage) {
        this.classImage = classImage;
    }
}
