package com.github.plexpt.spritesplit.utili;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import net.dreamlu.mica.http.HttpRequest;

import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.hutool.core.net.URLEncoder;
import cn.hutool.http.HtmlUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * 免费谷歌翻译 太频繁会被ban
 *
 * @author pt
 * @email plexpt@gmail.com
 * @date 2022-01-17 11:39
 */
@Slf4j
public class GoogleTranslateFree {
    public static final String api = "https://translate.googleapis" +
            ".com/translate_a/single?client=gtx&sl=en&tl=zh-cn&dt=t&q=";
    public static final String api2 = "https://translate.googleapis" +
            ".cn/translate_a/single?client=gtx&sl=en&tl=zh-cn&dt=t&q=";

    public static final RingBuffer<Proxy> queue = new RingBuffer<>();
    public static Proxy p = null;

    public static Proxy PROXY_DEFAULT = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0" +
            ".0.1", 1080));


    public static String translate(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String result = "";

        if (text.length() > 5000) {
            log.info(text.length() + "> 5000 文件太长， 分割中");
            List<String> list = SeqSpriter.makelinefeed(text, 5000);
            for (String s : list) {
                String trans = trans(s);
                result += trans + "\n";
            }
        } else {
            result = trans(text);
        }

        return result;
    }

    public static String trans(String text) {

        if (isContainChinese(text)) {
            return text;
        }
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String result = "";
        try {
            result = HttpRequest.get(api + URLEncoder.DEFAULT.encode(text, StandardCharsets.UTF_8))
                    .connectTimeout(Duration.ofSeconds(3))
                    .readTimeout(Duration.ofSeconds(3))
                    .writeTimeout(Duration.ofSeconds(3))
                    .proxy(getProxy())
//                    .proxy(ProxyList.get())
//                .proxy(Proxy.Type.HTTP, new InetSocketAddress("121.199.51.177", 1080))
                    .execute()
                    .asString();

            log.info(result);

            JSONArray objects = JSON.parseArray(result);
            JSONArray array = objects.getJSONArray(0);
            String string = "";
            for (int i = 0; i < array.size(); i++) {
                JSONArray jsonArray = array.getJSONArray(i);
                string += jsonArray.getString(0);

            }

            return string;
        } catch (Exception e) {
            ProxyList.invalid();
            p = null;
            if (StringUtils.containsIgnoreCase(result, "Our systems have detected unusual traffic" +
                    " from your computer network.")) {
                log.warn("IP被谷歌ban了");

            } else {
                log.warn(HtmlUtil.cleanHtmlTag(result));

            }

            log.warn(e.toString());
        }

        return "";
    }

    public static Proxy getProxy() {

        if (p == null) {
            p = ProxyList.get();
//            p =  queue.get();
        }

        return p;
    }

    public static void noProxy() {
        p = Proxy.NO_PROXY;
    }

    public static void DefaultProxy() {
        p = PROXY_DEFAULT;
    }

    public static void localProxy() {
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080));
        p = proxy;
    }

    /**
     * 字符串是否包含中文
     * 效率既高又能检测出中文汉字和中文标点；
     *
     * @param str 待校验字符串
     * @return true 包含中文字符 false 不包含中文字符
     */
    public static boolean isContainChinese(String str) {

        if (StringUtils.isBlank(str)) {
            return false;
        }
        Pattern p = Pattern.compile("[\u4E00-\u9FA5" +
                "|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @SneakyThrows
    public static void initProxy() {
        String url = "https://raw.githubusercontent.com/fate0/proxylist/master/proxy.list";
        String s = HttpRequest.get(url)
                .proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080))
                .retry(10, 100)
                .execute()
                .asString();
        String[] split = StringUtils.split(s, "\n");
        log.info("获取到代理个数：" + split.length);
        for (String json : split) {
            ProxyBean bean = JSON.parseObject(json, ProxyBean.class);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(bean.getHost(),
                    bean.getPort()));
            queue.add(proxy);
        }
    }
}
