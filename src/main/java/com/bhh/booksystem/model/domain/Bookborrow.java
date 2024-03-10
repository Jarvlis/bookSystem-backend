package com.bhh.booksystem.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName bookborrow
 */
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="图书借阅信息", description="图书借阅信息实体")
@Data
public class Bookborrow implements Serializable {
    @ApiModelProperty(value = "借阅ID")
    private Integer borrowid;

    @ApiModelProperty(value = "图书ID")
    private Integer bookid;

    @ApiModelProperty(value = "用户ID")
    private Integer userid;

    @ApiModelProperty(value = "借阅时间")
    private Date borrowtime;

    @ApiModelProperty(value = "归还时间")
    private Date returntime;

    @ApiModelProperty(value = "借阅原因")
    private String reason;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "借阅状态")
    private Integer borrowstatus;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Bookborrow other = (Bookborrow) that;
        return (this.getBorrowid() == null ? other.getBorrowid() == null : this.getBorrowid().equals(other.getBorrowid()))
            && (this.getBookid() == null ? other.getBookid() == null : this.getBookid().equals(other.getBookid()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getBorrowtime() == null ? other.getBorrowtime() == null : this.getBorrowtime().equals(other.getBorrowtime()))
            && (this.getReturntime() == null ? other.getReturntime() == null : this.getReturntime().equals(other.getReturntime()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getContact() == null ? other.getContact() == null : this.getContact().equals(other.getContact()))
            && (this.getRemarks() == null ? other.getRemarks() == null : this.getRemarks().equals(other.getRemarks()))
            && (this.getBorrowstatus() == null ? other.getBorrowstatus() == null : this.getBorrowstatus().equals(other.getBorrowstatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBorrowid() == null) ? 0 : getBorrowid().hashCode());
        result = prime * result + ((getBookid() == null) ? 0 : getBookid().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getBorrowtime() == null) ? 0 : getBorrowtime().hashCode());
        result = prime * result + ((getReturntime() == null) ? 0 : getReturntime().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getContact() == null) ? 0 : getContact().hashCode());
        result = prime * result + ((getRemarks() == null) ? 0 : getRemarks().hashCode());
        result = prime * result + ((getBorrowstatus() == null) ? 0 : getBorrowstatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", borrowid=").append(borrowid);
        sb.append(", bookid=").append(bookid);
        sb.append(", userid=").append(userid);
        sb.append(", borrowtime=").append(borrowtime);
        sb.append(", returntime=").append(returntime);
        sb.append(", reason=").append(reason);
        sb.append(", contact=").append(contact);
        sb.append(", remarks=").append(remarks);
        sb.append(", borrowstatus=").append(borrowstatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}