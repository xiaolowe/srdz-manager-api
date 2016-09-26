package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/19 13:51
 */
public class ContentBean {
    @ApiModelProperty(value="内容",required=true)
    @NotBlank(message="内容不能为空")
    private String content;

    @ApiModelProperty(value="内容模块",required=true)
    private int contentModuleId;

    @ApiModelProperty(value="标题",required=true)
    @NotBlank(message="标题不能为空")
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getContentModuleId() {
        return contentModuleId;
    }

    public void setContentModuleId(int contentModuleId) {
        this.contentModuleId = contentModuleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
