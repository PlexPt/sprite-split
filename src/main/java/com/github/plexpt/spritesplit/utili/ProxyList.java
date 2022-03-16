package com.github.plexpt.spritesplit.utili;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.dreamlu.mica.http.HttpRequest;

import java.net.InetSocketAddress;
import java.net.Proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * 取到代理：115.76.91.101:4001
 * 获取到代理：178.168.31.146:443
 *
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-01-18 13:17
 */
@Slf4j
public class ProxyList {

    public static Proxy current = null;

    public static Proxy get() {
        if (current == null) {
            current = getNew();
        }

        return current;
    }

    public static void invalid() {
        current = null;
    }

    private static Proxy getNew() {
        String url = "https://www.proxyscan.io/api/proxy?type=http";
        String s = HttpRequest.get(url)
//                .retry(10, 100)
                .execute()
                .asString();
        JSONArray j = JSON.parseArray(s);
        JSONObject jsonObject = j.getJSONObject(0);
        String ip = jsonObject.getString("Ip");
        Integer port = jsonObject.getInteger("Port");
        log.info("获取到代理：" + ip + ":" + port);

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
        return proxy;

    }

}
