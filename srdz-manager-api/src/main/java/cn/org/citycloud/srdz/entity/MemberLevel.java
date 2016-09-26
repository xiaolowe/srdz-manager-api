package cn.org.citycloud.srdz.entity;

import cn.org.citycloud.srdz.annotation.FieldName;

import javax.persistence.*;
import java.io.Serializable;


/**
 * The persistent class for the member_level database table.
 */
@Entity
@Table(name = "member_level")
@NamedQuery(name = "MemberLevel.findAll", query = "SELECT m FROM MemberLevel m")
public class MemberLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_level_id")
    private int memberLevelId;

    @FieldName(name = "成长值")
    private int growth;

    @FieldName(name = "会员等级")
    @Column(name = "member_level")
    private String memberLevel;

    public MemberLevel() {
    }

    public int getMemberLevelId() {
        return this.memberLevelId;
    }

    public void setMemberLevelId(int memberLevelId) {
        this.memberLevelId = memberLevelId;
    }

    public int getGrowth() {
        return this.growth;
    }

    public void setGrowth(int growth) {
        this.growth = growth;
    }

    public String getMemberLevel() {
        return this.memberLevel;
    }

    public void setMemberLevel(String memberLevel) {
        this.memberLevel = memberLevel;
    }
}