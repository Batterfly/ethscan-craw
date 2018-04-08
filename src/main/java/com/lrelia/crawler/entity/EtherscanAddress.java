package com.lrelia.crawler.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author <a href="mailto:jiangqin@vpgame.cn">Jiangqin</a>
 * @description
 * @date 2018/4/5
 */
@Entity
@Table(name = "etherscan_address", schema = "etherscan", catalog = "")
public class EtherscanAddress {
    private long id;
    private String address;
    private String alias;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 512)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "alias", nullable = true, length = 512)
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

}
