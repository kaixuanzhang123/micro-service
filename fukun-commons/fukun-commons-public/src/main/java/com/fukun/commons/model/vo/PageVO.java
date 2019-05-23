package com.fukun.commons.model.vo;

import com.fukun.commons.model.Model;
import com.fukun.commons.model.qo.PageQO;
import com.fukun.commons.util.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 分页VO对象
 *
 * @author tangyifei
 * @since 2019-5-23 11:53:48 AM
 */
@ApiModel("分页对象")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageVO<T> implements Model {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -4426958360243585882L;

    @ApiModelProperty(value = "当前页号")
    private Integer pageNum;

    @ApiModelProperty(value = "每页的数量")
    private Integer pageSize;

    @ApiModelProperty(value = "总记录数")
    private Long total;

    @ApiModelProperty(value = "总页数")
    private Integer pages;

    @ApiModelProperty(value = "结果集")
    private List<T> list;

    /**
     * 定义一个构造器，用于初始化当前页号，每页的大小
     *
     * @param pageQO 分页查询对象
     */
    public PageVO(PageQO pageQO) {
        this.setPageNum(pageQO.getPageNum());
        this.setPageSize(pageQO.getPageSize());
    }

    /**
     * 把PageHelper的分页信息赋值给当前的分页对象
     *
     * @param poList 对象列表
     */
    public PageVO(List<T> poList) {
        BeanUtils.copyProperties(new PageInfo<>(poList), this);
    }

    /**
     * 构造一个包含对象列表的分页对象
     *
     * @param poList 对象列表
     * @param <T>    泛型对象
     * @return 分页视图对象
     */
    public static <T> PageVO<T> build(List<T> poList) {
        return new PageVO<>(poList);
    }

    /**
     * 构建一个分页VO对象
     *
     * @param page 数据库查出来的分页数据列表
     */
    public static <T> PageVO<T> build(Page<T> page) {
        PageVO<T> pageVO = new PageVO<>();
        BeanUtils.copyProperties(page.toPageInfo(), pageVO);
        return pageVO;
    }

    /**
     * 构建一个分页VO对象
     *
     * @param page    数据库查出来的分页数据列表
     * @param voClazz 要转为的VO类
     *                试用场景为：从数据库取出的PO列表不做任何处理，转化为VO列表返回
     */
    public static <T, E> PageVO<T> build(Page<E> page, Class<T> voClazz) {

        PageVO<T> pageVO = new PageVO<>();
        BeanUtils.copyProperties(page, pageVO, "list");

        try {
            List<T> voList = Lists.newArrayList();
            List<E> poList = page.getResult();
            if (!CollectionUtils.isEmpty(poList)) {
                for(E e : poList) {
                    T t = voClazz.newInstance();
                    BeanUtils.copyProperties(e, t);
                    voList.add(t);
                }
            }
            pageVO.setList(voList);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        return pageVO;
    }

    /**
     * 构建一个分页VO对象
     *
     * @param poPage 数据库查出来的分页数据
     * @param voList vo数据列表
     *               试用场景为：将处理好的VO列表封装返回
     */
    public static <T, E> PageVO<T> build(Page<E> poPage, List<T> voList) {
        PageVO<T> page = new PageVO<>();
        BeanUtil.copyProperties(poPage, page, "list");
        page.setList(voList == null ? Lists.newArrayList() : voList);
        return page;
    }

    /**
     * 获取总页数
     *
     * @param total    总的记录数
     * @param pageSize 每页的大小
     * @return 总页数
     */
    public static int getPages(long total, int pageSize) {
        if (total == 0 || pageSize == 0) {
            return 0;
        }
        return (int) (total % pageSize == 0 ? (total / pageSize) : (total / pageSize + 1));
    }

    /**
     * 获取总页数
     *
     * @return 总页数
     */
    public int getPages() {
        return getPages(this.total, this.pageSize);
    }
}
