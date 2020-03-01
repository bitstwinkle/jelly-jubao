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
import java.math.BigDecimal;

/**
 * @author suuyoo.wg on 2020/3/1
 */
public class TransferJob implements Serializable {

  private static final long serialVersionUID = 4750808908761456687L;

  private String dest;
  private BigDecimal amount;

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("dest", dest)
        .add("amount", amount)
        .toString();
  }

  public String getDest() {
    return dest;
  }

  public void setDest(String dest) {
    this.dest = dest;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }
}
