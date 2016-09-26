package cn.org.citycloud.srdz.bean;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * desc the file.
 *
 * @author demon
 * @Date 2016/4/25 17:18
 */
public class NoticeBean {

    @ApiModelProperty(value="公告内容",required=true)
    @NotBlank(message="公告内容不能为空")
    private String content;

    @ApiModelProperty(value="公告标题",required=true)
    @NotBlank(message="公告标题不能为空")
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
