package com.jinlink.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.jinlink.modules.monitor.entity.vo.GameEntityVo;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpUtils {
    public static List<GameEntityVo> getSteamApiServer(List<String> pathList){
        try {
            RestTemplate restTemplate = new RestTemplate();
            StringBuilder paths= new StringBuilder();
            for (String path : pathList) {
                paths.append("paths=").append(path).append("&");
            }
            return analysisJsonForObject(restTemplate.getForObject(new URI("https://inadvertently.top/steamApi/?" + paths), String.class));
        }catch (Exception e){
            System.out.println("请求失败: " + e.getMessage());
            return null;
        }
    }

    /**
     * 将返回的json字符串转为java对象
     */
    public static List<GameEntityVo> analysisJsonForObject(String jsonString){
        if (ObjectUtil.isNull(jsonString)){
            return List.of();
        }
        List<GameEntityVo> gameEntityVos = new ArrayList<>();
        JSONArray serverJsonArray = JSON.parseArray(jsonString);
        serverJsonArray.forEach(obj->{
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray servers = jsonObject.getJSONObject("response").getJSONArray("servers");
            if (StringUtils.isNull(servers)){
                return;
            }
            //遍历servers
            servers.forEach(server->{
                GameEntityVo gameEntityVo = JSONObject.parseObject(server.toString(), GameEntityVo.class);
                gameEntityVos.add(gameEntityVo);
            });
        });
        return gameEntityVos;
    }
}
