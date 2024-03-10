package com.bhh.booksystem.model.domain;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName bookinfo
 */
@Data
@ApiModel(value="图书信息", description="图书信息实体")
@NoArgsConstructor
@AllArgsConstructor
public class Bookinfo implements Serializable {
    /**
     * 图书ID
     */
    @ApiModelProperty(value = "图书ID")
    private Integer bookid;

    /**
     * 图书名称
     */
    @ApiModelProperty(value = "图书名称")
    private String bookname;

    /**
     * 出版时间
     */
    @ApiModelProperty(value = "出版时间")
    private Date publishtime;

    /**
     * 作者
     */
    @ApiModelProperty(value = "作者")
    private String author;

    /**
     * 出版社
     */
    @ApiModelProperty(value = "出版社")
    private String publisher;

    /**
     * 图书分类
     */
    @ApiModelProperty(value = "图书分类")
    private String category;

    /**
     * 图书页数
     */
    @ApiModelProperty(value = "图书页数")
    private Integer pages;

    /**
     * 图书价格
     */
    @ApiModelProperty(value = "图书价格")
    private Float price;

    /**
     * 图书封面路径
     */
    @ApiModelProperty(value = "图书封面路径")
    private String picturepath;

    /**
     * 图书状态
     */
    @ApiModelProperty(value = "图书状态")
    private int bookstatus;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID")
    private int deptid;

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
        Bookinfo other = (Bookinfo) that;
        return (this.getBookid() == null ? other.getBookid() == null : this.getBookid().equals(other.getBookid()))
                && (this.getBookname() == null ? other.getBookname() == null : this.getBookname().equals(other.getBookname()))
                && (this.getPublishtime() == null ? other.getPublishtime() == null : this.getPublishtime().equals(other.getPublishtime()))
                && (this.getAuthor() == null ? other.getAuthor() == null : this.getAuthor().equals(other.getAuthor()))
                && (this.getPublisher() == null ? other.getPublisher() == null : this.getPublisher().equals(other.getPublisher()))
                && (this.getCategory() == null ? other.getCategory() == null : this.getCategory().equals(other.getCategory()))
                && (this.getPages() == null ? other.getPages() == null : this.getPages().equals(other.getPages()))
                && (this.getPrice() == null ? other.getPrice() == null : this.getPrice().equals(other.getPrice()))
                && (this.getPicturepath() == null ? other.getPicturepath() == null : this.getPicturepath().equals(other.getPicturepath()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBookid() == null) ? 0 : getBookid().hashCode());
        result = prime * result + ((getBookname() == null) ? 0 : getBookname().hashCode());
        result = prime * result + ((getPublishtime() == null) ? 0 : getPublishtime().hashCode());
        result = prime * result + ((getAuthor() == null) ? 0 : getAuthor().hashCode());
        result = prime * result + ((getPublisher() == null) ? 0 : getPublisher().hashCode());
        result = prime * result + ((getCategory() == null) ? 0 : getCategory().hashCode());
        result = prime * result + ((getPages() == null) ? 0 : getPages().hashCode());
        result = prime * result + ((getPrice() == null) ? 0 : getPrice().hashCode());
        result = prime * result + ((getPicturepath() == null) ? 0 : getPicturepath().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bookid=").append(bookid);
        sb.append(", bookname=").append(bookname);
        sb.append(", publishtime=").append(publishtime);
        sb.append(", author=").append(author);
        sb.append(", publisher=").append(publisher);
        sb.append(", category=").append(category);
        sb.append(", pages=").append(pages);
        sb.append(", price=").append(price);
        sb.append(", picturepath=").append(picturepath);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}