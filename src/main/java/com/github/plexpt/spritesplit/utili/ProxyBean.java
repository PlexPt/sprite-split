package com.github.plexpt.spritesplit.utili;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author pengtao
 * @email plexpt@gmail.com
 * @date 2022-03-16 15:01
 */
@NoArgsConstructor
@Data
public class ProxyBean {

    /**
     * country : US
     * port : 3128
     * host : 205.155.45.139
     * response_time : 1.06
     * anonymity : transparent
     * export_address : ["205.155.45.164"]
     * type : http
     * from : proxylist
     */

    private String country;
    private int port;
    private String host;
    private double response_time;
    private String anonymity;
    private String type;
    private String from;
    private List<String> export_address;
}
