package com.bhh.booksystem.model.domain;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TableName bookflow
 */
@Data
@ApiModel(value="图书流通信息", description="图书流通信息实体")
@AllArgsConstructor
@NoArgsConstructor
public class  Bookflow implements Serializable {
    @ApiModelProperty(value = "流转ID")
    private Integer flowid;

    @ApiModelProperty(value = "图书ID")
    private Integer bookid;

    @ApiModelProperty(value = "原始单位")
    private Integer ownerunit;

    @ApiModelProperty(value = "流转目标单位")
    private Integer flowtounit;

    @ApiModelProperty(value = "流转时间")
    private Date flowtime;

    @ApiModelProperty(value = "归还时间")
    private Date returntime;

    @ApiModelProperty(value = "流转原因")
    private String reason;

    @ApiModelProperty(value = "申请人")
    private String applicant;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "流转状态")
    private Integer flowstatus;

    private static final long serialVersionUID = 1L;
}