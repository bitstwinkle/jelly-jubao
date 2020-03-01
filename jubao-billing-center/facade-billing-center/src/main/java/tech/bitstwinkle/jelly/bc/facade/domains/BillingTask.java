/*
 *
 *  * ** github: https://github.com/bitstwinkle ***
 *  * ** gitee: https://gitee.com/bitstwinkle ***
 *  * ** 比特闪耀-技术让世界更美丽 ***
 *  * Copyright © 2020-2030 Hangzhou BitsTwinkle Information Technology Co.,Ltd. All rights reserved.
 *
 */

package tech.bitstwinkle.jelly.bc.facade.domains;

import com.google.common.base.MoreObjects;
import java.io.Serializable;
import java.util.List;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class BillingTask implements Serializable {

  private static final long serialVersionUID = -5843411262632808231L;

  private String src;

  private List<TransferJob> transfers;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("src", src)
        .add("transfers", transfers)
        .toString();
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public List<TransferJob> getTransfers() {
    return transfers;
  }

  public void setTransfers(List<TransferJob> transfers) {
    this.transfers = transfers;
  }
}
