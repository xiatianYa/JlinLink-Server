package com.jinlink.common.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 自定义分页查询对象
 */
@Getter
@Setter
public class PageQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = -9112559334485771185L;

    @Min(value = 1, message = "当前页码不能小于1")
    @Schema(description = "当前页码", defaultValue = "1")
    private Integer current = 1;

    @Max(value = 500, message = "每页显示数量不能超过500")
    @Schema(description = "每页显示数量", defaultValue = "20")
    private Integer size = 20;

    /**
     * 构建分页对象
     *
     * @return {@link Page} 分页对象
     * @author summer
     */
    @JsonIgnore
    public <T> Page<T> buildPage() {
        return new Page<>(this.current, this.size);
    }

}
