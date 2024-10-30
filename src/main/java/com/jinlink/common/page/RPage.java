package com.jinlink.common.page;

import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 自定义分页返回对象
 */
@Data
@AllArgsConstructor
public class RPage<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 4865115289132710247L;

    @Schema(description = "当前页码")
    private long current;

    @Schema(description = "每页显示数量")
    private long size;

    @Schema(description = "数据源")
    private transient List<T> records;

    @Schema(description = "总页数")
    private long pages;

    @Schema(description = "总数")
    private long total;

    /**
     * 组装分页返回对象 RPage.build()
     *
     * @param page Mybatis Flex 分页对象
     */
    public static <T> RPage<T> build(Page<T> page) {
        return new RPage<>(page.getPageNumber(), page.getPageSize(), page.getRecords(), page.getTotalPage(), page.getTotalRow());
    }
}
