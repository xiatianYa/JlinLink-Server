package com.jinlink.common.em;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class ExamineStatus {

    //攻略审核枚举
    public enum GameMapStrategyStatusEnum {
        Unaudited(0,"未审核"),
        PREVIEW(1,"审核中"),
        APPROVED(2,"审核通过"),
        REVIEWABLE(3,"审核未通过");

        private Integer value;
        private String name;

        GameMapStrategyStatusEnum(Integer value, String name) {
            this.value = value;
            this.name = name;
        }

        public static List<Type> getEnumList() {
            List<Type> enumTypes = new ArrayList<>();
            for (GameMapStrategyStatusEnum readStatusEnum : GameMapStrategyStatusEnum.values()) {
                Type enumType = new Type();
                enumType.setStatus(readStatusEnum.value);
                enumType.setStatusName(readStatusEnum.name);
                enumTypes.add(enumType);
            }
            return enumTypes;
        }

        public static String getByValue(Integer value) {
            for (GameMapStrategyStatusEnum readStatusEnum : GameMapStrategyStatusEnum.values()) {
                if (readStatusEnum.value.equals(value)) {
                    return readStatusEnum.name;
                }
            }
            return null;
        }

        public static Integer getByName(String name) {
            for (GameMapStrategyStatusEnum experimentEnum : GameMapStrategyStatusEnum.values()) {
                if (experimentEnum.name.equals(name)) {
                    return experimentEnum.value;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }
    }

}

@Data
class Type{
    @Schema(description = "值")
    private Integer status;
    @Schema(description = "名")
    private String statusName;
}
