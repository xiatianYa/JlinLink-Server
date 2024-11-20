package com.jinlink.common.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {
    /**
     * 根据当前页码和每页数量获取分页后的列表
     *
     * @param list     原始列表
     * @param currentPage 当前页码（从1开始）
     * @param pageSize  每页显示的元素数量
     * @param <T>       列表元素类型
     * @return 分页后的列表
     */
    public static <T> List<T> getPage(List<T> list, int currentPage, int pageSize) {
        if (list == null || currentPage <= 0 || pageSize <= 0) {
            throw new IllegalArgumentException("Invalid input parameters");
        }

        int fromIndex = (currentPage - 1) * pageSize;
        if (fromIndex >= list.size()) {
            return new ArrayList<>(); // 返回空列表，表示没有更多数据
        }

        int toIndex = Math.min(fromIndex + pageSize, list.size());
        return list.subList(fromIndex, toIndex);
    }
}
