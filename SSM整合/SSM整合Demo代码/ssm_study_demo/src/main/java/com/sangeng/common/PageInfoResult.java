package com.sangeng.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoResult<T> {
    /**
     * 页码
     */
    private int pageNum;

    /**
     * 一页包含多少条数据
     */
    private int pageSize;

    /**
     * 总共一共有多少条数据
     */
    private int total;

    /**
     * 查询到的数据
     * 由于可能查询不同类型的数据
     * 因此这里使用泛型
     */
    private List<T> Data;
}
